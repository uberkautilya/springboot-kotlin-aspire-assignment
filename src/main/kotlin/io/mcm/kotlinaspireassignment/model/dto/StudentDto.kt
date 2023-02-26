package io.mcm.kotlinaspireassignment.model.dto

import com.fasterxml.jackson.module.kotlin.readValue
import io.mcm.kotlinaspireassignment.model.entity.Student
import javax.validation.constraints.*

data class StudentDto(
    val id: Int?,
    @NotBlank(message = "Student name cannot be blank")
    val name: String?,
    @Email(message = "The email address needs to have the appropriate pattern")
    val emailId: String?,
    @Min(3, message = "Minimum age allowed is 3")
    @Max(60, message = "Maximum age allowed is 60")
    val age: Int?,
    val gender: String?,
    @Pattern(regexp = "^\\d{10}$", message = "The mobile number must be 10 digits")
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StudentDto

        if (id != other.id) return false
        if (name != other.name) return false
        if (age != other.age) return false
        if (gender != other.gender) return false
        if (mobileNo != other.mobileNo) return false
        if (emailId != other.emailId) return false
        if (courseList != other.courseList) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (age?.hashCode() ?: 0)
        result = 31 * result + (gender?.hashCode() ?: 0)
        result = 31 * result + (mobileNo?.hashCode() ?: 0)
        result = 31 * result + (emailId?.hashCode() ?: 0)
        result = 31 * result + (courseList?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "StudentDto(id=$id, name=$name, emailId=$emailId, age=$age, gender=$gender, mobileNo=$mobileNo, courseList=$courseList)"
    }


}
