package io.mcm.kotlinaspireassignment.repository

import io.mcm.kotlinaspireassignment.model.entity.Department
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface DepartmentRepository: JpaRepository<Department, Int>, JpaSpecificationExecutor<Department> {
}