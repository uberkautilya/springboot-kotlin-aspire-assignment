package io.mcm.kotlinaspireassignment.service

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
    var defaultPageSize: Int = 0

    fun findAll(): MutableList<Department> {
        return departmentRepository.findAll()
    }

    fun findById(id: Int): Optional<Department> {
        return departmentRepository.findById(id)
    }

    fun save(departmentRequest: DepartmentRequest): MutableList<Department> {
        return departmentRepository.saveAll(departmentRequest.departmentList)
    }

    fun delete(departmentRequest: DepartmentRequest) {
        return departmentRepository.deleteAll(departmentRequest.departmentList)
    }

    fun filter(departmentRequest: DepartmentRequest): DepartmentResponse {
        val page: Page<Department>
        if (Objects.nonNull(departmentRequest.pageNo)) {
            page = PageImpl(departmentRepository.findAll(DepartmentSpecification.build(departmentRequest)))
        } else {
            if (Objects.isNull(departmentRequest.pageSize)) {
                departmentRequest.pageSize = defaultPageSize
            }
            val pageable = PageRequest.of(departmentRequest.pageNo, departmentRequest.pageSize)
            page = departmentRepository.findAll(DepartmentSpecification.build(departmentRequest), pageable)
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