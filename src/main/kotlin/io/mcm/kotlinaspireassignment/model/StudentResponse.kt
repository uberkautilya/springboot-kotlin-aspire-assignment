package io.mcm.kotlinaspireassignment.model

import io.mcm.kotlinaspireassignment.model.entity.Student

class StudentResponse(val studentList: MutableList<Student>) {
    var pageNo: Int = 0
    var totalCount: Long = 0L
    var totalPages: Int = 0
}