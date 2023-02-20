package io.mcm.kotlinaspireassignment.model.entity

import com.fasterxml.jackson.annotation.JsonInclude
import io.mcm.kotlinaspireassignment.model.dto.CourseDto
import io.mcm.kotlinaspireassignment.model.dto.DepartmentDto
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "teachers")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
open class Teacher {
    var id: Int = 0
    var name: String =""
    var courseList = mutableListOf<CourseDto>()
    var dept = Department()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Teacher

        if (id != other.id) return false
        if (name != other.name) return false
        if (courseList != other.courseList) return false
        if (dept != other.dept) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + courseList.hashCode()
        result = 31 * result + dept.hashCode()
        return result
    }

    override fun toString(): String {
        return "Teacher(id=$id, name='$name', courseList=$courseList, dept=$dept)"
    }

}