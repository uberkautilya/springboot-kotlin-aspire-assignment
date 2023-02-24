package io.mcm.kotlinaspireassignment.model

import io.mcm.kotlinaspireassignment.model.entity.Course
import io.mcm.kotlinaspireassignment.model.entity.Department
import io.mcm.kotlinaspireassignment.model.entity.Student
import io.mcm.kotlinaspireassignment.model.entity.Teacher
import org.springframework.web.multipart.MultipartFile
import javax.validation.Valid

class CourseRequest {
    val courseFilter: CourseFilter = CourseFilter()
    val courseList: List<Course> = listOf()

    //To add course content for a courseId
    val courseId: Int = 0
    val courseContent: MultipartFile? = null
}

class DepartmentRequest {
    val departmentList: MutableList<Department> = mutableListOf()
    val departmentFilter: DepartmentFilter = DepartmentFilter()
}

class TeacherRequest {
    val teacherFilter: TeacherFilter = TeacherFilter()
    val teacherList: List<Teacher> = listOf<Teacher>()
}

class StudentRequest {
    val studentFilter: StudentFilter = StudentFilter()
    val studentList: List<Student> = listOf()
}