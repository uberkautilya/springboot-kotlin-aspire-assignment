package io.mcm.kotlinaspireassignment.model.entity

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonManagedReference
import javax.persistence.*

@Entity
@Table(name = "departments")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
open class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DepartmentIdGenerator")
    @SequenceGenerator(name = "DepartmentIdGenerator", sequenceName = "DEPARTMENT_ID_SEQ")
    open var id: Int? = null
    open var name: String? = null

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "department", cascade = [CascadeType.ALL])
    @JsonManagedReference(value = "courseList-in-department")
    open var courseList: MutableList<Course>? = null

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "department",
        cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH]
    )
    @JsonManagedReference(value = "teacherList-in-department")
    open var teacherList: MutableList<Teacher>? = null

    constructor()
    constructor(
        id: Int? = null,
        name: String? = null,
        courseList: MutableList<Course>? = null,
        teacherList: MutableList<Teacher>? = null
    ) {
        this.id = id
        this.name = name
        this.courseList = courseList
        this.teacherList = teacherList
    }

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

    override fun toString(): String {
        return """Department(id=$id, name='$name'
            ${if (null != courseList) ", courseList=${courseList!!.forEach { "${it.id} + ${it.name}" }}" else ""}
            ${if (null != teacherList) ", teacherList=${teacherList!!.forEach { "${it.id} + ${it.name}" }}" else ""})""".trimMargin()
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (courseList?.hashCode() ?: 0)
        result = 31 * result + (teacherList?.hashCode() ?: 0)
        return result
    }

}
