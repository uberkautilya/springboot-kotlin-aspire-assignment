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

}
