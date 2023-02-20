package io.mcm.kotlinaspireassignment.model.entity

import com.fasterxml.jackson.annotation.JsonInclude
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name="students")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
open class Student {
    var id: Int = 0
    var name: String = ""
    var dept: Department = Department()
    var courseList = mutableListOf<Course>()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Student

        if (id != other.id) return false
        if (name != other.name) return false
        if (dept != other.dept) return false
        if (courseList != other.courseList) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + dept.hashCode()
        result = 31 * result + courseList.hashCode()
        return result
    }

    override fun toString(): String {
        return "Student(id=$id, name='$name', dept=$dept, courseList=$courseList)"
    }


}