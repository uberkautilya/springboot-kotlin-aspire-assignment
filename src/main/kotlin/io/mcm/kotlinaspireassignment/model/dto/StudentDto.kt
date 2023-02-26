package io.mcm.kotlinaspireassignment.model.dto

import com.fasterxml.jackson.module.kotlin.readValue
import io.mcm.kotlinaspireassignment.model.entity.Student

data class StudentDto(
    val id: Int?,
    val name: String?,
    val emailId: String?,
    val courseList: MutableList<CourseDto>?) {
    companion object {
        fun getStudentDtoFromEntity(student: Student): StudentDto {
            val studentString = jObjMapper.writeValueAsString(student)
            return jObjMapper.readValue<StudentDto>(studentString)
        }

        fun getStudentEntityFromDto(studentDto: StudentDto): Student {
            val studentDtoString = jObjMapper.writeValueAsString(studentDto)
            return jObjMapper.readValue<Student>(studentDtoString)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StudentDto

        if (id != other.id) return false
        if (name != other.name) return false
        if (emailId != other.emailId) return false
        if (courseList != other.courseList) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (emailId?.hashCode() ?: 0)
        result = 31 * result + (courseList?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "StudentDto(id=$id, name=$name, emailId=$emailId, courseList=$courseList)"
    }


}
