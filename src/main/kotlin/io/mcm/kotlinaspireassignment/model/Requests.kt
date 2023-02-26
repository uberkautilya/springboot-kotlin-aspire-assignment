package io.mcm.kotlinaspireassignment.model

import io.mcm.kotlinaspireassignment.model.dto.CourseDto
import io.mcm.kotlinaspireassignment.model.dto.DepartmentDto
import io.mcm.kotlinaspireassignment.model.dto.StudentDto
import io.mcm.kotlinaspireassignment.model.dto.TeacherDto
import org.springframework.web.multipart.MultipartFile
import javax.validation.Valid

class CourseRequest {
    val courseFilter: CourseFilter = CourseFilter()

    @field:Valid
    val courseList: List<CourseDto> = listOf()

    //To add course content for a courseId
    val courseId: Int = 0
    val courseContent: MultipartFile? = null
}

class DepartmentRequest {
    val departmentFilter: DepartmentFilter = DepartmentFilter()
    val departmentList: List<DepartmentDto> = listOf()
}

class TeacherRequest {
    val teacherFilter: TeacherFilter = TeacherFilter()
    val teacherList: List<TeacherDto> = listOf()
}

class StudentRequest {
    val studentFilter: StudentFilter = StudentFilter()
    val studentList: List<StudentDto> = listOf()
}