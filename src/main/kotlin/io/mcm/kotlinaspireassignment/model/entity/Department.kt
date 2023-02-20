package io.mcm.kotlinaspireassignment.model.entity

import com.fasterxml.jackson.annotation.JsonInclude
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "departments")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
open class Department {
    var id: Int = 0
    var name: String = ""
    var courseList = mutableListOf<Course>()
    var teacherList = mutableListOf<Teacher>()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Department

        if (id != other.id) return false
        if (name != other.name) return false
        if (courseList != other.courseList) return false
        if (teacherList != other.teacherList) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + courseList.hashCode()
        result = 31 * result + teacherList.hashCode()
        return result
    }

    override fun toString(): String {
        return "Department(id=$id, name='$name', courseList=$courseList, teacherList=$teacherList)"
    }

}
