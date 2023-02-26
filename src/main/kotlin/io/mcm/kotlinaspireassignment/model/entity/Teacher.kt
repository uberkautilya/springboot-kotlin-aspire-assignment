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
    open var id: Int? = null
    open var name: String? = null
    open var age: Int? = null
    open var gender: String? = null
    open var mobileNo: String? = null
    open var salary: Long? = null

    @Temporal(TemporalType.DATE)
    open var joiningDate: Date? = null

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "teacher", cascade = [CascadeType.ALL])
    @JsonManagedReference(value = "courseList-in-teacher")
    open var courseList: MutableList<Course>? = null

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JsonBackReference(value = "teacherList-in-department")
    open var department: Department? = null

    open var emailId: String? = null

    constructor()
    constructor(
        id: Int? = null,
        name: String? = null,
        age: Int? = null,
        gender: String? = null,
        mobileNo: String? = null,
        courseList: MutableList<Course>? = null,
        department: Department? = null,
        joiningDate: Date? = null,
        salary: Long? = null,
        emailId: String? = null
    ) {
        this.id = id
        this.name = name
        this.age = age
        this.gender = gender
        this.mobileNo = mobileNo
        this.courseList = courseList
        this.department = department
        this.joiningDate = joiningDate
        this.salary = salary
        this.emailId = emailId
    }

    override fun toString(): String {
        return """Teacher(id=$id, name='$name', salary=$salary, joiningDate=${joiningDate}, emailId=$emailId
            ${if (null != courseList) ", courseList=${courseList!!.forEach { "${it.id} + ${it.name}" }}" else ""}
            ${if (null != department) ", department=${department!!.name}" else ""})""".trimMargin()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Teacher

        if (id != other.id) return false
        if (name != other.name) return false
        if (age != other.age) return false
        if (gender != other.gender) return false
        if (mobileNo != other.mobileNo) return false
        if (salary != other.salary) return false
        if (joiningDate != other.joiningDate) return false
        if (courseList != other.courseList) return false
        if (department != other.department) return false
        if (emailId != other.emailId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (age?.hashCode() ?: 0)
        result = 31 * result + (gender?.hashCode() ?: 0)
        result = 31 * result + (mobileNo?.hashCode() ?: 0)
        result = 31 * result + (salary?.hashCode() ?: 0)
        result = 31 * result + (joiningDate?.hashCode() ?: 0)
        result = 31 * result + (courseList?.hashCode() ?: 0)
        result = 31 * result + (department?.hashCode() ?: 0)
        result = 31 * result + (emailId?.hashCode() ?: 0)
        return result
    }


}