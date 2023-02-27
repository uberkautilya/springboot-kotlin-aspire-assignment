package io.mcm.kotlinaspireassignment.model.entity

import com.fasterxml.jackson.annotation.*
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "courses")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator::class, property = "id")
open class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var id: Int? = null

    open var name: String? = null

    @JsonProperty("startDate")
    open var startDate: LocalDate? = null
    open var endDate: LocalDate? = null

    @ManyToOne(
        fetch = FetchType.EAGER,
        cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH]
    )
    @JsonBackReference("courseList-in-teacher")
    open var teacher: Teacher? = null

    @ManyToOne(
        fetch = FetchType.EAGER,
        cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH]
    )
    @JsonBackReference(value = "courseList-in-department")
    open var department: Department? = null

    @ManyToMany(
        fetch = FetchType.LAZY,
        mappedBy = "courseList",
        cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH]
    )
    open var studentList: MutableList<Student>? = null

    @Lob
    @Column(length = 10000)
    open var courseContent: ByteArray? = null
    open var fileName: String? = null

    constructor()
    constructor(
        id: Int? = null,
        name: String? = null,
        startDate: LocalDate? = null,
        endDate: LocalDate? = null,
        teacher: Teacher? = null,
        department: Department? = null,
        studentList: MutableList<Student>? = null,
        courseContent: ByteArray? = null,
        fileName: String? = null
    ) {
        this.id = id
        this.name = name
        this.startDate = startDate
        this.endDate = endDate
        this.teacher = teacher
        this.department = department
        this.studentList = studentList
        this.courseContent = courseContent
        this.fileName = fileName
    }

    override fun toString(): String {
        return """
            Course(id=$id, name='$name', startDate=$startDate, endDate=$endDate, fileName=$fileName)
            ${if (teacher != null) ", teacher=${teacher!!.name}" else ""}
            ${if (department != null) ", department=${department!!.name}" else ""}
            ${if (studentList != null) ", studentList=${studentList!!.forEach { "${it.id}: ${it.name}" }}" else ""}""".trimMargin()
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
        if (department != other.department) return false
        if (studentList != other.studentList) return false
        if (!courseContent.contentEquals(other.courseContent)) return false
        if (fileName != other.fileName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (startDate?.hashCode() ?: 0)
        result = 31 * result + (endDate?.hashCode() ?: 0)
        result = 31 * result + (teacher?.hashCode() ?: 0)
        result = 31 * result + (department?.hashCode() ?: 0)
        result = 31 * result + (studentList?.hashCode() ?: 0)
        result = 31 * result + (courseContent?.contentHashCode() ?: 0)
        result = 31 * result + (fileName?.hashCode() ?: 0)
        return result
    }


}