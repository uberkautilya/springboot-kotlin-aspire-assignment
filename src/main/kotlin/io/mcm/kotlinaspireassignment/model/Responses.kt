package io.mcm.kotlinaspireassignment.model

import io.mcm.kotlinaspireassignment.model.entity.Course
import io.mcm.kotlinaspireassignment.model.entity.Department
import io.mcm.kotlinaspireassignment.model.entity.Student
import io.mcm.kotlinaspireassignment.model.entity.Teacher

class CourseResponse(val courseList: MutableList<Course>) {
    var totalPages = 0
    var totalCount: Long = 0L
    var pageNo: Int = 0
    override fun toString(): String {
        return "CourseResponse(courseList=$courseList, totalPages=$totalPages, totalCount=$totalCount, pageNo=$pageNo)"
    }
}

class DepartmentResponse(val departmentList: MutableList<Department>) {
    var pageNo: Int = 0
    var totalCount: Long = 0L
    var totalPages: Int = 0
    override fun toString(): String {
        return "DepartmentResponse(departmentList=$departmentList, pageNo=$pageNo, totalCount=$totalCount, totalPages=$totalPages)"
    }
}

class TeacherResponse(val teacherList: MutableList<Teacher>) {
    var pageNo: Int = 0
    var totalCount: Long = 0L
    var totalPages: Int = 0
    override fun toString(): String {
        return "TeacherResponse(teacherList=$teacherList, pageNo=$pageNo, totalCount=$totalCount, totalPages=$totalPages)"
    }
}

class StudentResponse(val studentList: MutableList<Student>) {
    var pageNo: Int = 0
    var totalCount: Long = 0L
    var totalPages: Int = 0
    override fun toString(): String {
        return "StudentResponse(studentList=$studentList, pageNo=$pageNo, totalCount=$totalCount, totalPages=$totalPages)"
    }
}