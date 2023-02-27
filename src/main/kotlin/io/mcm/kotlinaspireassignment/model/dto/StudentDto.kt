package io.mcm.kotlinaspireassignment.model.dto

import com.fasterxml.jackson.module.kotlin.readValue
import io.mcm.kotlinaspireassignment.model.entity.Student
import javax.validation.constraints.*

data class StudentDto(
    val id: Int?,
    @field:NotBlank(message = "Student name cannot be blank")
    val name: String?,
    @field:Email(message = "The email address needs to have the appropriate pattern")
    val emailId: String?,
    @field:Min(3, message = "Minimum age allowed is 3")
    @field:Max(60, message = "Maximum age allowed is 60")
    val age: Int?,
    val gender: String?,
    @field:Pattern(regexp = "^\\d{10}$", message = "The mobile number must be 10 digits")
    val mobileNo: String?,
    val courseList: MutableList<CourseDto>?
) {
    companion object {
        fun getStudentDtoFromEntity(student: Student): StudentDto {
            val studentString = jObjMapper.writeValueAsString(student)
            return jObjMapper.readValue<StudentDto>(studentString)
        }

        fun getStudentEntityFromDto(studentDto: StudentDto): Student {
            val studentDtoString = jObjMapper.writeValueAsString(studentDto)
            return jObjMapper.readValue<Student>(studentDtoString)
        }

        fun getStudentDtoListFromEntityList(studentList: List<Student>): List<StudentDto> {
            val studentString = jObjMapper.writeValueAsString(studentList)
            return jObjMapper.readValue(studentString)
        }

        fun getStudentEntityListFromDtoList(studentDtoList: List<StudentDto>): List<Student> {
            val studentDtoString = jObjMapper.writeValueAsString(studentDtoList)
            return jObjMapper.readValue(studentDtoString)
        }
    }

}
