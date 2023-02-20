package io.mcm.kotlinaspireassignment.model

import io.mcm.kotlinaspireassignment.model.dto.StudentFilter
import io.mcm.kotlinaspireassignment.model.entity.Student

class StudentRequest {
    val studentFilter: StudentFilter = StudentFilter()
    var pageSize: Int = 0
    val pageNo: Int = 0
    val studentList: List<Student> = listOf()
}