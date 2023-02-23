package io.mcm.kotlinaspireassignment.configuration

import io.mcm.kotlinaspireassignment.model.entity.Course
import io.mcm.kotlinaspireassignment.model.entity.Department
import io.mcm.kotlinaspireassignment.model.entity.Student
import io.mcm.kotlinaspireassignment.model.entity.Teacher
import io.mcm.kotlinaspireassignment.repository.CourseRepository
import io.mcm.kotlinaspireassignment.repository.DepartmentRepository
import io.mcm.kotlinaspireassignment.repository.StudentRepository
import io.mcm.kotlinaspireassignment.repository.TeacherRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.text.SimpleDateFormat
import java.time.LocalDate

@Configuration
class DataInit {
    @Bean
    @Transactional(propagation = Propagation.REQUIRED)
    fun initDatabase(
        departmentRepository: DepartmentRepository,
        studentRepository: StudentRepository,
        courseRepository: CourseRepository,
        teacherRepository: TeacherRepository
    ) = ApplicationRunner {
        var course: Course
        var student: Student
        var teacher = Teacher()
        var department = Department()
        course = Course(
            name = "Assignment101",
            teacher = teacher,
            department = department,
            startDate = LocalDate.of(1992, 7, 6),
            endDate = LocalDate.now(),
            fileName = "",
            courseContent = byteArrayOf()
        )
        teacher = Teacher(name = "Teacher101", joiningDate = SimpleDateFormat("yyyy-MM-dd").parse("2001-02-02"), department = department, courseList = mutableListOf(course))
        student = Student(name = "Student101", courseList = mutableListOf(course), department = department)
        department = Department(
            name = "Kotlin with SpringBoot",
            courseList = mutableListOf(course),
            studentList = mutableListOf(student),
            teacherList = mutableListOf(teacher)
        )
        course.teacher = teacher
        course.department = department
        teacher.department = department
        teacher.courseList = mutableListOf(course)
        student.department = department

        courseRepository.save(course)
        studentRepository.save(student)
        teacherRepository.save(teacher)
        departmentRepository.save(department)
    }
}