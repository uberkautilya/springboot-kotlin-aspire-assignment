package io.mcm.kotlinaspireassignment.model.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.mcm.kotlinaspireassignment.model.entity.Course
import java.time.LocalDate

val jObjMapper = jacksonObjectMapper()

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class CourseDto(val id: Int, val name: String, val startDate: LocalDate, val endDate: LocalDate, val courseContent: ByteArray) {
    companion object {
        fun getCourseDtoFromEntity(course: Course): CourseDto {
            val courseString = jObjMapper.writeValueAsString(course)
            return jObjMapper.readValue(courseString)
        }
        fun getCourseEntityFromDto(courseDto: CourseDto): Course {
            val courseDtoString = jObjMapper.writeValueAsString(courseDto)
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
        if (!courseContent.contentEquals(other.courseContent)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + startDate.hashCode()
        result = 31 * result + courseContent.contentHashCode()
        return result
    }
}
