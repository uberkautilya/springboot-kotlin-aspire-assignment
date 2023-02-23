package io.mcm.kotlinaspireassignment.model.entity

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonManagedReference
import javax.persistence.*

@Entity
@Table(name = "students")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
open class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var id: Int = 0
    open var name: String = ""

    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.PERSIST])
    @JsonManagedReference
    open var dept: Department = Department()

    @ManyToMany(cascade = [CascadeType.PERSIST], fetch = FetchType.EAGER)
    @JoinTable(
        name = "STUDENT_COURSE_MAPPING",
        joinColumns = [JoinColumn(name = "student_id")],
        inverseJoinColumns = [JoinColumn(name = "course_id")]
    )
    @JsonManagedReference
    open var courseList: MutableList<Course> = mutableListOf()

    constructor()
    constructor(
        id: Int = 0,
        name: String = "",
        dept: Department = Department(),
        courseList: MutableList<Course> = mutableListOf()
    ) {
        this.id = id
        this.name = name
        this.dept = dept
        this.courseList = courseList
    }

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
        return "Student(id=$id, name='$name', dept=${dept.name}, courseList=${courseList.forEach { "${it.id}: ${it.name}" }})"
    }

}