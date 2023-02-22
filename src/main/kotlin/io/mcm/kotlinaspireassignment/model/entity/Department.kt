package io.mcm.kotlinaspireassignment.model.entity

import com.fasterxml.jackson.annotation.JsonInclude
import javax.persistence.*

@Entity
@Table(name = "departments")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
open class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var id: Int = 0
    open var name: String = ""

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dept", cascade = [CascadeType.ALL])
    open var courseList: MutableList<Course> = mutableListOf()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dept", cascade = [CascadeType.REFRESH, CascadeType.PERSIST])
    open var teacherList: MutableList<Teacher> = mutableListOf()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dept", cascade = [CascadeType.PERSIST])
    open var studentList: MutableList<Student> = mutableListOf()

    constructor()
    constructor(
        id: Int = 0,
        name: String = "",
        courseList: MutableList<Course> = mutableListOf(),
        teacherList: MutableList<Teacher> = mutableListOf(),
        studentList: MutableList<Student> = mutableListOf()
    ) {
        this.id = id
        this.name = name
        this.courseList = courseList
        this.teacherList = teacherList
        this.studentList = studentList
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Department

        if (id != other.id) return false
        if (name != other.name) return false
        if (courseList != other.courseList) return false
        if (teacherList != other.teacherList) return false
        if (studentList != other.studentList) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + courseList.hashCode()
        result = 31 * result + teacherList.hashCode()
        result = 31 * result + studentList.hashCode()
        return result
    }

    override fun toString(): String {
        return """Department(id=$id, name='$name', courseList=${courseList.forEach { "${it.id} + ${it.name}" }}, 
            teacherList=${teacherList.forEach { "${it.id} + ${it.name}" }}, 
            studentList=${studentList.forEach { "${it.id} + ${it.name}" }})"""
    }

}
