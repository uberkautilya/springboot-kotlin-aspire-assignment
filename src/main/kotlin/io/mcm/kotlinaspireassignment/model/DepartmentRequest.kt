package io.mcm.kotlinaspireassignment.model

import io.mcm.kotlinaspireassignment.model.dto.FilterDepartment
import io.mcm.kotlinaspireassignment.model.entity.Department

class DepartmentRequest {
    var pageSize: Int = 0
    val departmentList: MutableList<Department> = mutableListOf()
    val pageNo: Int = 0
    val filterDepartment: FilterDepartment = FilterDepartment()
}