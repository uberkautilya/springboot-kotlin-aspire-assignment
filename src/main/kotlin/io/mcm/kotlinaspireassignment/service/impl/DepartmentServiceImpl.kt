package io.mcm.kotlinaspireassignment.service.impl

import io.mcm.kotlinaspireassignment.exceptionhandling.exception.DepartmentException
import io.mcm.kotlinaspireassignment.model.DepartmentRequest
import io.mcm.kotlinaspireassignment.model.DepartmentResponse
import io.mcm.kotlinaspireassignment.model.dto.DepartmentDto
import io.mcm.kotlinaspireassignment.model.entity.Department
import io.mcm.kotlinaspireassignment.repository.DepartmentRepository
import io.mcm.kotlinaspireassignment.service.DepartmentService
import io.mcm.kotlinaspireassignment.specification.DepartmentSpecification
import org.springframework.beans.factory.annotation.Value
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.sql.SQLIntegrityConstraintViolationException
import java.util.*

@Service
class DepartmentServiceImpl(val departmentRepository: DepartmentRepository) : DepartmentService {

    @Value("\${default.pageSize.departments:3}")
    var defaultPageSize: Int = 1

    /**
     * Fetch all Department entities from the DB
     */
    override fun findAll(): DepartmentResponse {
        return DepartmentResponse(departmentRepository.findAll())
    }

    /**
     * Fetch the department entity of Id provided as parameter
     */
    override fun findById(id: Int): DepartmentResponse {
        val departmentById = departmentRepository.findById(id)
            .orElseThrow { DepartmentException.DepartmentNotFoundException() }
        return DepartmentResponse(mutableListOf(departmentById))
    }

    /**
     * Save a list of Department entities in the DepartmentRequest
     */
    override fun save(departmentRequest: DepartmentRequest): DepartmentResponse {
        val departmentRequestList = DepartmentDto.getDepartmentEntityListFromDtoList(departmentRequest.departmentList)
        val departmentList = departmentRepository.saveAll(departmentRequestList)
        return DepartmentResponse(departmentList)
    }

    /**
     * Update list of Department entities in the DepartmentRequest
     */
    override fun update(departmentRequest: DepartmentRequest): DepartmentResponse {
        val departmentRequestList = DepartmentDto.getDepartmentEntityListFromDtoList(departmentRequest.departmentList)
        val departmentInDBList = mutableListOf<Department>()
        for (department in departmentRequestList) {
            if (null == department.id) {
                continue
            }
            val departmentInDB = departmentRepository.findById(department.id!!)
                .orElseThrow { throw DepartmentException.DepartmentNotFoundException() }
            departmentInDB.name = department.name
            departmentInDB.courseList = department.courseList
            departmentInDB.teacherList = department.teacherList
            departmentInDBList.add(departmentInDB)
        }
        val savedDepartmentList = departmentRepository.saveAll(departmentInDBList)
        return DepartmentResponse(savedDepartmentList)
    }

    /**
     * Deletes a list of Department entities, passed in the DepartmentRequest
     */
    override fun delete(departmentRequest: DepartmentRequest): DepartmentResponse {
        val departmentEntityList = DepartmentDto.getDepartmentEntityListFromDtoList(departmentRequest.departmentList)

        val departmentInDBList = mutableListOf<Department>()
        for (department in departmentEntityList) {
            if (null == department.id) {
                continue
            }
            val departmentInDB = departmentRepository.findById(department.id!!)
                .orElseThrow { throw DepartmentException.DepartmentNotFoundException() }
            departmentInDBList.add(departmentInDB)
        }
        try {
            departmentRepository.deleteAllInBatch(departmentEntityList)
        } catch (e: DataIntegrityViolationException) {
            if (e.message?.contains("references `departments`") == true) {
                throw DepartmentException.ForeignKeyViolationException("Department Id is referenced in another table")
            }
        } catch (e: SQLIntegrityConstraintViolationException) {
            if (e.message?.contains("references `departments`") == true) {
                throw DepartmentException.ForeignKeyViolationException("Department Id is referenced in another table")
            }
        }
//        departmentRepository.flush()
        return DepartmentResponse(departmentInDBList)
    }

    /**
     * Filter Department entities from the DB as per criteria available in departmentFilter of DepartmentRequest
     */
    override fun filter(departmentRequest: DepartmentRequest): DepartmentResponse {
        val page: Page<Department>
        val departmentFilter = departmentRequest.departmentFilter
        if (Objects.isNull(departmentFilter.pageNo)) {
            page = PageImpl(departmentRepository.findAll(DepartmentSpecification.build(departmentFilter)))
        } else {
            if (Objects.isNull(departmentFilter.pageSize) || departmentFilter.pageSize == 0) {
                departmentFilter.pageSize = defaultPageSize
            }
            val pageable = PageRequest.of(departmentFilter.pageNo!! - 1, departmentFilter.pageSize!!)
            page = departmentRepository.findAll(DepartmentSpecification.build(departmentFilter), pageable)
        }
        val departmentList = page.content
        val departmentResponse = DepartmentResponse(mutableListOf<Department>())
        if (departmentList.isNotEmpty()) {
            for (department in departmentList) {
                departmentResponse.departmentList.add(department)
            }
            departmentResponse.totalPages = page.totalPages
            departmentResponse.totalCount = page.totalElements
            departmentResponse.pageNo = page.number
        }
        return departmentResponse
    }
}