package io.mcm.kotlinaspireassignment.model.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.module.kotlin.readValue
import io.mcm.kotlinaspireassignment.model.entity.Teacher
import java.util.*
import javax.validation.constraints.*

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class TeacherDto(
    val id: Int?,
    @NotBlank(message = "Teacher name cannot be blank")
    val name: String?,
    @Min(18, message = "Minimum age allowed is 18")
    @Max(65, message = "Maximum age allowed is 65")
    val age: Int?,
    val gender: String?,
    @Pattern(regexp = "^\\d{10}$", message = "The mobile number must be 10 digits")
    val mobileNo: String?,
    val salary: Long?,
    val joiningDate: Date?,
    val courseList: MutableList<CourseDto>?,
    val department: DepartmentDto?,
    @Email(message = "The email address needs to have the appropriate pattern")
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TeacherDto

        if (id != other.id) return false
        if (name != other.name) return false
        if (age != other.age) return false
        if (gender != other.gender) return false
        if (mobileNo != other.mobileNo) return false
        if (courseList != other.courseList) return false
        if (department != other.department) return false

        return true
    }


    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (age?.hashCode() ?: 0)
        result = 31 * result + (gender?.hashCode() ?: 0)
        result = 31 * result + (mobileNo?.hashCode() ?: 0)
        result = 31 * result + (salary?.hashCode() ?: 0)
        result = 31 * result + (joiningDate?.hashCode() ?: 0)
        result = 31 * result + (courseList?.hashCode() ?: 0)
        result = 31 * result + (department?.hashCode() ?: 0)
        result = 31 * result + (emailId?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "TeacherDto(id=$id, name=$name, age=$age, gender=$gender, mobileNo=$mobileNo, salary=$salary, joiningDate=$joiningDate, courseList=$courseList, department=$department, emailId=$emailId)"
    }

}