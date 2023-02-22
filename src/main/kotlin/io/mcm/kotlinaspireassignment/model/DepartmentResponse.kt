package io.mcm.kotlinaspireassignment.model

import io.mcm.kotlinaspireassignment.model.entity.Department

class DepartmentResponse(val departmentList: MutableList<Department>) {
    var pageNo: Int = 0
    var totalCount: Long = 0L
    var totalPages: Int = 0
    override fun toString(): String {
        return "DepartmentResponse(departmentList=$departmentList, pageNo=$pageNo, totalCount=$totalCount, totalPages=$totalPages)"
    }

}