package io.mcm.kotlinaspireassignment.model.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.module.kotlin.readValue
import io.mcm.kotlinaspireassignment.model.entity.Department
import javax.validation.constraints.NotBlank

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class DepartmentDto(
    val id: Int?,
    @field:NotBlank(message = "Department name cannot be blank")
    val name: String?,
    val courseList: MutableList<CourseDto>?,
    val teacherList: MutableList<TeacherDto>?
) {
    companion object {
        fun getDepartmentDtoFromEntity(department: Department): DepartmentDto {
            val departmentString = jObjMapper.writeValueAsString(department)
            return jObjMapper.readValue<DepartmentDto>(departmentString)
        }

        fun getDepartmentEntityFromDto(departmentDto: DepartmentDto): Department {
            val departmentDtoString = jObjMapper.writeValueAsString(departmentDto)
            return jObjMapper.readValue<Department>(departmentDtoString)
        }
        fun getDepartmentDtoListFromEntityList(departmentList: List<Department>): List<DepartmentDto> {
            val departmentString = jObjMapper.writeValueAsString(departmentList)
            return jObjMapper.readValue(departmentString)
        }

        fun getDepartmentEntityListFromDtoList(departmentDtoList: List<DepartmentDto>): List<Department> {
            val departmentDtoString = jObjMapper.writeValueAsString(departmentDtoList)
            return jObjMapper.readValue(departmentDtoString)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DepartmentDto

        if (id != other.id) return false
        if (name != other.name) return false
        if (courseList != other.courseList) return false
        if (teacherList != other.teacherList) return false

        return true
    }


    override fun toString(): String {
        return "DepartmentDto(id=$id, name='$name', courseList=$courseList, teacherList=$teacherList)"
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (courseList?.hashCode() ?: 0)
        result = 31 * result + (teacherList?.hashCode() ?: 0)
        return result
    }

}
