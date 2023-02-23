package io.mcm.kotlinaspireassignment.model.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.module.kotlin.readValue
import io.mcm.kotlinaspireassignment.model.entity.Teacher

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class TeacherDto(val id: Int, val name: String, val courseList: MutableList<CourseDto>, val department: DepartmentDto) {
    companion object {
        fun getTeacherDtoFromEntity(teacher: Teacher): TeacherDto {
            val teacherString = jObjMapper.writeValueAsString(teacher)
            return jObjMapper.readValue<TeacherDto>(teacherString)
        }
        fun getTeacherEntityFromDto(teacherDto: TeacherDto): Teacher {
            val teacherDtoString = jObjMapper.writeValueAsString(teacherDto)
            return jObjMapper.readValue<Teacher>(teacherDtoString)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TeacherDto

        if (id != other.id) return false
        if (name != other.name) return false
        if (courseList != other.courseList) return false
        if (department != other.department) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + courseList.hashCode()
        result = 31 * result + department.hashCode()
        return result
    }

    override fun toString(): String {
        return "TeacherDto(id=$id, name='$name', courseList=$courseList, department=$department)"
    }

}