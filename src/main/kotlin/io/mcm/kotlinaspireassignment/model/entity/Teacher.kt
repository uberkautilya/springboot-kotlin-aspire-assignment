package io.mcm.kotlinaspireassignment.model.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonManagedReference
import javax.persistence.*

@Entity
@Table(name = "teachers")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
open class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var id: Int = 0
    open var name: String = ""

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "teacher", cascade = [CascadeType.PERSIST])
    @JsonBackReference
    open var courseList: MutableList<Course> = mutableListOf()

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST])
    @JsonManagedReference
    open var dept = Department()

    constructor()
    constructor(
        id: Int = 0,
        name: String = "",
        courseList: MutableList<Course> = mutableListOf(),
        dept: Department = Department()
    ) {
        this.id = id
        this.name = name
        this.courseList = courseList
        this.dept = dept
    }

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
        return "Teacher(id=$id, name='$name', courseList=${courseList.forEach { "${it.id}: ${it.name}" }}, dept=${dept.name})"
    }

}