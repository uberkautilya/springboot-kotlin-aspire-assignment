package io.mcm.kotlinaspireassignment.model.entity

import com.fasterxml.jackson.annotation.*
import javax.persistence.*

@Entity
@Table(name = "students")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator::class, property = "id")
open class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "StudentIdGenerator")
    @SequenceGenerator(name = "StudentIdGenerator", sequenceName = "STUDENT_ID_SEQ")
    open var id: Int? = null
    open var name: String? = null
    open var age: Int? = null
    open var gender: String? = null
    open var mobileNo: String? = null
    open var emailId: String? = null

    @ManyToMany(
        fetch = FetchType.EAGER,
        cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH]
    )
    @JoinTable(
        name = "STUDENT_COURSE_MAPPING",
        joinColumns = [JoinColumn(name = "student_id")],
        inverseJoinColumns = [JoinColumn(name = "course_id")]
    )
    open var courseList: MutableList<Course>? = null

    constructor()
    constructor(
        id: Int? = null,
        name: String? = null,
        age: Int? = null,
        gender: String? = null,
        mobileNo: String? = null,
        emailId: String? = null,
        courseList: MutableList<Course>? = null
    ) {
        this.id = id
        this.name = name
        this.age = age
        this.gender = gender
        this.mobileNo = mobileNo
        this.emailId = emailId
        this.courseList = courseList
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Student

        if (id != other.id) return false
        if (name != other.name) return false
        if (age != other.age) return false
        if (gender != other.gender) return false
        if (mobileNo != other.mobileNo) return false
        if (emailId != other.emailId) return false
        if (courseList != other.courseList) return false

        return true
    }


    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (age?.hashCode() ?: 0)
        result = 31 * result + (gender?.hashCode() ?: 0)
        result = 31 * result + (mobileNo?.hashCode() ?: 0)
        result = 31 * result + (emailId?.hashCode() ?: 0)
        result = 31 * result + (courseList?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Student(id=$id, name=$name, age=$age, gender=$gender, mobileNo=$mobileNo, emailId=$emailId)"
    }

}