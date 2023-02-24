package io.mcm.kotlinaspireassignment.model.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonManagedReference
import java.text.SimpleDateFormat
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "teachers")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
open class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var id: Int = 0
    open var name: String = ""
    open var salary: Long = 0

    @Temporal(TemporalType.DATE)
    open var joiningDate: Date = SimpleDateFormat("yyyy-MM-dd").parse("2000-01-01")

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "teacher", cascade = [CascadeType.ALL])
    @JsonManagedReference(value = "courseList-in-teacher")
    open var courseList: MutableList<Course> = mutableListOf()

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JsonBackReference(value = "teacherList-in-department")
    open var department = Department()

    constructor()
    constructor(
        id: Int = 0,
        name: String = "",
        courseList: MutableList<Course> = mutableListOf(),
        department: Department = Department(),
        joiningDate: Date = SimpleDateFormat("yyyy-MM-dd").parse("2000-01-01"),
        salary: Long = 0L
    ) {
        this.id = id
        this.name = name
        this.courseList = courseList
        this.department = department
        this.joiningDate = joiningDate
        this.salary = salary
    }

    override fun toString(): String {
        return "Teacher(id=$id, name='$name', salary=$salary, joiningDate=${joiningDate}, courseList=${courseList.forEach { "${it.id}: ${it.name}" }}, department=${department.name})"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Teacher

        if (id != other.id) return false
        if (name != other.name) return false
        if (salary != other.salary) return false
        if (joiningDate != other.joiningDate) return false
        if (courseList != other.courseList) return false
        if (department != other.department) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + salary.hashCode()
        result = 31 * result + joiningDate.hashCode()
        result = 31 * result + courseList.hashCode()
        result = 31 * result + department.hashCode()
        return result
    }

}