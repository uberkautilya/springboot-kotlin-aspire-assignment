package io.mcm.kotlinaspireassignment.model.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonManagedReference
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "courses")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
open class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var id: Int = 0
    open var name: String = ""

    @JsonProperty("startDate")
//    @Temporal(TemporalType.DATE) //Available only on Date and Calendar objects
    open var startDate: LocalDate = LocalDate.of(1, 1, 1)
//    @Temporal(TemporalType.DATE)
    open var endDate: LocalDate = LocalDate.of(1, 1, 1)

    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.PERSIST])
    @JsonManagedReference
    open var teacher: Teacher = Teacher()

    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.PERSIST])
    @JsonManagedReference
    open var dept: Department = Department()

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "courseList", cascade = [CascadeType.PERSIST])
    @JsonBackReference
    open var studentList: MutableList<Student> = mutableListOf()
    @Lob
    @Column(length = 10000)
    open var courseContent = byteArrayOf()
    open var fileName = ""

    constructor()
    constructor(
        id: Int = 0,
        name: String = "",
        startDate: LocalDate = LocalDate.of(1, 1, 1),
        endDate: LocalDate = LocalDate.of(1, 1, 1),
        teacher: Teacher = Teacher(),
        dept: Department = Department(),
        studentList: MutableList<Student> = mutableListOf(),
        courseContent: ByteArray = ByteArray(0),
        fileName: String = ""
    ) {
        this.id = id
        this.name = name
        this.startDate = startDate
        this.endDate = endDate
        this.teacher = teacher
        this.dept = dept
        this.studentList = studentList
        this.courseContent = courseContent
        this.fileName = fileName
    }

    override fun toString(): String {
        return "Course(id=$id, name='$name', startDate=$startDate, endDate=$endDate, teacher=${teacher.name}, dept=${dept.name}, studentList=${studentList.forEach { "${it.id}: ${it.name}" }}, fileName=$fileName)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Course

        if (id != other.id) return false
        if (name != other.name) return false
        if (startDate != other.startDate) return false
        if (endDate != other.endDate) return false
        if (teacher != other.teacher) return false
        if (dept != other.dept) return false
        if (studentList != other.studentList) return false
        if (!courseContent.contentEquals(other.courseContent)) return false
        if (fileName != other.fileName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + startDate.hashCode()
        result = 31 * result + endDate.hashCode()
        result = 31 * result + teacher.hashCode()
        result = 31 * result + dept.hashCode()
        result = 31 * result + studentList.hashCode()
        result = 31 * result + courseContent.contentHashCode()
        result = 31 * result + fileName.hashCode()
        return result
    }


}