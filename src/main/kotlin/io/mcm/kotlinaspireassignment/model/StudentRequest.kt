package io.mcm.kotlinaspireassignment.model

import io.mcm.kotlinaspireassignment.model.entity.Student

class StudentRequest {
    val studentFilter: StudentFilter = StudentFilter()
    val studentList: List<Student> = listOf()
}