package io.mcm.kotlinaspireassignment.model

import io.mcm.kotlinaspireassignment.model.entity.Course
import io.mcm.kotlinaspireassignment.model.entity.Department
import io.mcm.kotlinaspireassignment.model.entity.Student
import io.mcm.kotlinaspireassignment.model.entity.Teacher

class CourseFilter : Course() {
    val orderBy: String? = null
    var pageSize: Int? = null
    val pageNo: Int? = null
    override fun toString(): String {
        return "CourseFilter(orderBy='$orderBy', pageSize=$pageSize, pageNo=$pageNo) ${super.toString()}"
    }
}
class DepartmentFilter: Department() {
    val orderBy: String? = null
    var pageSize: Int? = null
    val pageNo: Int? = null
    override fun toString(): String {
        return "DepartmentFilter(orderBy='$orderBy', pageSize=$pageSize, pageNo=$pageNo) ${super.toString()}"
    }
}
class StudentFilter: Student() {
    val orderBy: String? = null
    var pageSize: Int? = null
    val pageNo: Int? = null
    override fun toString(): String {
        return "StudentFilter(orderBy='$orderBy', pageSize=$pageSize, pageNo=$pageNo) ${super.toString()}"
    }
}
class TeacherFilter: Teacher() {
    val orderBy: String? = null
    var pageSize: Int? = null
    val pageNo: Int? = null
    override fun toString(): String {
        return "TeacherFilter(orderBy='$orderBy', pageSize=$pageSize, pageNo=$pageNo) ${super.toString()}"
    }
}


