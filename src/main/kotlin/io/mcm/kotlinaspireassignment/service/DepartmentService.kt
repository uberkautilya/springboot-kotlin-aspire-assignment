package io.mcm.kotlinaspireassignment.service

import io.mcm.kotlinaspireassignment.exceptionhandling.exception.DepartmentException
import io.mcm.kotlinaspireassignment.model.DepartmentRequest
import io.mcm.kotlinaspireassignment.model.DepartmentResponse
import io.mcm.kotlinaspireassignment.model.entity.Department
import io.mcm.kotlinaspireassignment.repository.DepartmentRepository
import io.mcm.kotlinaspireassignment.specification.DepartmentSpecification
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.util.*

@Service
class DepartmentService(val departmentRepository: DepartmentRepository) {

    @Value("\${default.pageSize.departments:3}")
    var defaultPageSize: Int = 1

    fun findAll(): MutableList<Department> {
        return departmentRepository.findAll()
    }

    fun findById(id: Int): Department {
        return departmentRepository.findById(id)
            .orElseThrow { DepartmentException.DepartmentNotFoundException() }
    }

    fun save(departmentRequest: DepartmentRequest): MutableList<Department> {
        return departmentRepository.saveAll(departmentRequest.departmentList)
    }

    fun update(departmentRequest: DepartmentRequest): DepartmentResponse {
        val departmentInDBList = mutableListOf<Department>()
        for (department in departmentRequest.departmentList) {
            val departmentInDB = departmentRepository.findById(department.id)
                .orElseThrow { throw DepartmentException.DepartmentNotFoundException() }
            departmentInDB.name = department.name
            departmentInDB.courseList = department.courseList
            departmentInDB.teacherList = department.teacherList
            departmentInDB.studentList = department.studentList
            departmentInDBList.add(departmentInDB)
        }
        val savedDepartmentList = departmentRepository.saveAll(departmentInDBList)
        return DepartmentResponse(savedDepartmentList)
    }

    fun delete(departmentRequest: DepartmentRequest): DepartmentResponse {
        val departmentInDBList = mutableListOf<Department>()
        for (department in departmentRequest.departmentList) {
            val departmentInDB = departmentRepository.findById(department.id)
                .orElseThrow { throw DepartmentException.DepartmentNotFoundException() }
            departmentInDBList.add(departmentInDB)
        }
        departmentRepository.deleteAll(departmentInDBList)
        return DepartmentResponse(departmentInDBList)
    }

    fun filter(departmentRequest: DepartmentRequest): DepartmentResponse {
        val page: Page<Department>
        val departmentFilter = departmentRequest.departmentFilter
        if (Objects.nonNull(departmentFilter.pageNo)) {
            page = PageImpl(departmentRepository.findAll(DepartmentSpecification.build(departmentFilter)))
        } else {
            if (Objects.isNull(departmentFilter.pageSize)) {
                departmentFilter.pageSize = defaultPageSize
            }
            val pageable = PageRequest.of(departmentFilter.pageNo, departmentFilter.pageSize)
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