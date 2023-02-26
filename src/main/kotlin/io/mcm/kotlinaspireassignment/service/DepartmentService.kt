package io.mcm.kotlinaspireassignment.service

import io.mcm.kotlinaspireassignment.model.DepartmentRequest
import io.mcm.kotlinaspireassignment.model.DepartmentResponse

interface DepartmentService {
    fun findAll(): DepartmentResponse
    fun findById(id: Int): DepartmentResponse
    fun save(departmentRequest: DepartmentRequest): DepartmentResponse
    fun update(departmentRequest: DepartmentRequest): DepartmentResponse
    fun delete(departmentRequest: DepartmentRequest): DepartmentResponse
    fun filter(departmentRequest: DepartmentRequest): DepartmentResponse
}
