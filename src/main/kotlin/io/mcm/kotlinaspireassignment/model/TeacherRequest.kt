package io.mcm.kotlinaspireassignment.model

import io.mcm.kotlinaspireassignment.model.dto.TeacherFilter
import io.mcm.kotlinaspireassignment.model.entity.Teacher

class TeacherRequest {
    val teacherFilter: TeacherFilter = TeacherFilter()
    var pageSize: Int = 0
    val pageNo: Int = 0
    val teacherList: List<Teacher> = listOf<Teacher>()
}