package io.mcm.kotlinaspireassignment.model.dto

import com.fasterxml.jackson.module.kotlin.readValue
import io.mcm.kotlinaspireassignment.model.entity.Student

data class StudentDto(val id: Int, val name: String, val department: DepartmentDto, val course: CourseDto){
    companion object{
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
        if (department != other.department) return false
        if (course != other.course) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + department.hashCode()
        result = 31 * result + course.hashCode()
        return result
    }

    override fun toString(): String {
        return "StudentDto(id=$id, name='$name', department=$department, course=$course)"
    }
}
