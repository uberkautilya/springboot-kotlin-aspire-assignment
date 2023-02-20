package io.mcm.kotlinaspireassignment.model.entity

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "courses")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
open class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0
    var name: String = ""

    @JsonProperty("startDate")
    var startDate: LocalDate = LocalDate.of(0, 0, 0)
    var endDate: LocalDate = LocalDate.of(0, 0, 0)
    var teacher: Teacher = Teacher()
    var dept: Department = Department()
    var courseContent: ByteArray = ByteArray(0)

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
        if (!courseContent.contentEquals(other.courseContent)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + startDate.hashCode()
        result = 31 * result + endDate.hashCode()
        result = 31 * result + teacher.hashCode()
        result = 31 * result + dept.hashCode()
        result = 31 * result + courseContent.contentHashCode()
        return result
    }

    override fun toString(): String {
        return "Course(id=$id, name='$name', startDate=$startDate, endDate=$endDate, teacher=$teacher, dept=$dept, courseContent=${courseContent.contentToString()})"
    }


}