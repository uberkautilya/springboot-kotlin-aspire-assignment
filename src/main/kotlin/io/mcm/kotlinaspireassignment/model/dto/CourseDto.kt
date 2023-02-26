package io.mcm.kotlinaspireassignment.model.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.mcm.kotlinaspireassignment.model.entity.Course
import java.time.LocalDate
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

val jObjMapper = jacksonObjectMapper()

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class CourseDto(
    val id: Int?,
    @field:NotNull(message = "Course name cannot be null")
    @field:NotBlank(message = "Course name cannot be blank")
    val name: String?,
    val startDate: LocalDate?,
    val endDate: LocalDate?,
    val courseContent: ByteArray?,
    val fileName: String?,
    val teacherDto: TeacherDto?,
    val department: DepartmentDto?,
    val studentList: MutableList<StudentDto>?
) {
    companion object {
        fun getCourseDtoFromEntity(course: Course): CourseDto {
            val courseString = jObjMapper.writeValueAsString(course)
            return jObjMapper.readValue(courseString)
        }

        fun getCourseEntityFromDto(courseDto: CourseDto): Course {
            val courseDtoString = jObjMapper.writeValueAsString(courseDto)
            return jObjMapper.readValue(courseDtoString)
        }

        fun getCourseDtoListFromEntityList(courseList: List<Course>): List<CourseDto> {
            val courseString = jObjMapper.writeValueAsString(courseList)
            return jObjMapper.readValue(courseString)
        }

        fun getCourseEntityListFromDtoList(courseDtoList: List<CourseDto>): List<Course> {
            val courseDtoString = jObjMapper.writeValueAsString(courseDtoList)
            return jObjMapper.readValue(courseDtoString)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CourseDto

        if (id != other.id) return false
        if (name != other.name) return false
        if (startDate != other.startDate) return false
        if (endDate != other.endDate) return false
        if (!courseContent.contentEquals(other.courseContent)) return false
        if (fileName != other.fileName) return false
        if (teacherDto != other.teacherDto) return false
        if (department != other.department) return false
        if (studentList != other.studentList) return false

        return true
    }


    override fun toString(): String {
        return "CourseDto(id=$id, name='$name', startDate=$startDate, endDate=$endDate, courseContent=${courseContent.contentToString()}, fileName='$fileName', teacherDto=$teacherDto, department=$department, studentList=$studentList)"
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (startDate?.hashCode() ?: 0)
        result = 31 * result + (endDate?.hashCode() ?: 0)
        result = 31 * result + (courseContent?.contentHashCode() ?: 0)
        result = 31 * result + (fileName?.hashCode() ?: 0)
        result = 31 * result + (teacherDto?.hashCode() ?: 0)
        result = 31 * result + (department?.hashCode() ?: 0)
        result = 31 * result + (studentList?.hashCode() ?: 0)
        return result
    }

}
