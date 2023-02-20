package io.mcm.kotlinaspireassignment.model

import io.mcm.kotlinaspireassignment.model.entity.Teacher

class TeacherResponse(val teacherList: MutableList<Teacher>) {
    var pageNo: Int = 0
    var totalCount: Long = 0L
    var totalPages: Int = 0
}