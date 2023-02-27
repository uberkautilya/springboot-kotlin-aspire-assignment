package io.mcm.kotlinaspireassignment.model.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.module.kotlin.readValue
import io.mcm.kotlinaspireassignment.model.entity.Teacher
import java.util.*
import javax.validation.constraints.*

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class TeacherDto(
    val id: Int?,
    @field:NotBlank(message = "Teacher name cannot be blank")
    val name: String?,
    @field:Min(18, message = "Minimum age allowed is 18")
    @field:Max(65, message = "Maximum age allowed is 65")
    val age: Int?,
    val gender: String?,
    @field:Pattern(regexp = "^\\d{10}$", message = "The mobile number must be 10 digits")
    val mobileNo: String?,
    val salary: Long?,
    val joiningDate: Date?,
    val courseList: MutableList<CourseDto>?,
    val department: DepartmentDto?,
    @field:Email(message = "The email address needs to have the appropriate pattern")
    val emailId: String?
) {
    companion object {
        fun getTeacherDtoFromEntity(teacher: Teacher): TeacherDto {
            val teacherString = jObjMapper.writeValueAsString(teacher)
            return jObjMapper.readValue<TeacherDto>(teacherString)
        }

        fun getTeacherEntityFromDto(teacherDto: TeacherDto): Teacher {
            val teacherDtoString = jObjMapper.writeValueAsString(teacherDto)
            return jObjMapper.readValue<Teacher>(teacherDtoString)
        }

        fun getTeacherDtoListFromEntityList(teacherList: List<Teacher>): List<TeacherDto> {
            val teacherString = jObjMapper.writeValueAsString(teacherList)
            return jObjMapper.readValue(teacherString)
        }

        fun getTeacherEntityListFromDtoList(teacherDtoList: List<TeacherDto>): List<Teacher> {
            val teacherDtoString = jObjMapper.writeValueAsString(teacherDtoList)
            return jObjMapper.readValue(teacherDtoString)
        }
    }

}