package io.mcm.kotlinaspireassignment.model.dto

import io.mcm.kotlinaspireassignment.model.entity.Teacher

class TeacherFilter: Teacher() {
    val orderBy: String = ""
    var pageSize: Int = 0
    val pageNo: Int = 0
    override fun toString(): String {
        return "TeacherFilter(orderBy='$orderBy', pageSize=$pageSize, pageNo=$pageNo) ${super.toString()}"
    }

}
