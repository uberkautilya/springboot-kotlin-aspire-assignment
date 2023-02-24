package io.mcm.kotlinaspireassignment.model

import io.mcm.kotlinaspireassignment.model.entity.Department

class DepartmentRequest {
    val departmentList: MutableList<Department> = mutableListOf()
    val departmentFilter: DepartmentFilter = DepartmentFilter()
}