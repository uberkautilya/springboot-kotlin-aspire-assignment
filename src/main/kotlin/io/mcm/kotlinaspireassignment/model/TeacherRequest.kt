package io.mcm.kotlinaspireassignment.model

import io.mcm.kotlinaspireassignment.model.entity.Teacher

class TeacherRequest {
    val teacherFilter: TeacherFilter = TeacherFilter()
    val teacherList: List<Teacher> = listOf<Teacher>()
}