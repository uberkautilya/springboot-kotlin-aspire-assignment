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
    override fun toString(): String {
        return "CourseRequest(courseFilter=$courseFilter, courseList=$courseList, courseId=$courseId, courseContent=$courseContent)"
    }
}

class DepartmentRequest {
    val departmentFilter: DepartmentFilter = DepartmentFilter()
    @field:Valid
    val departmentList: List<DepartmentDto> = listOf()
    override fun toString(): String {
        return "DepartmentRequest(departmentFilter=$departmentFilter, departmentList=$departmentList)"
    }

}

class TeacherRequest {
    val teacherFilter: TeacherFilter = TeacherFilter()
    @field:Valid
    val teacherList: List<TeacherDto> = listOf()
    override fun toString(): String {
        return "TeacherRequest(teacherFilter=$teacherFilter, teacherList=$teacherList)"
    }
}

class StudentRequest {
    val studentFilter: StudentFilter = StudentFilter()
    @field:Valid
    val studentList: List<StudentDto> = listOf()
    override fun toString(): String {
        return "StudentRequest(studentFilter=$studentFilter, studentList=$studentList)"
    }
}