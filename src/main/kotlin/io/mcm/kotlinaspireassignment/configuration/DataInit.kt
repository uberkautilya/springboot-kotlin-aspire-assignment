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
        if (!courseRepository.findById(1).isPresent) {
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
            teacher = Teacher(
                name = "Teacher101",
                salary = 100L,
                age = 56,
                emailId = "teacher101@gmail.com",
                mobileNo = "0123456789",
                gender = "Male",
                joiningDate = SimpleDateFormat("yyyy-MM-dd").parse("2001-02-02"),
                department = department,
                courseList = mutableListOf(course)
            )
            student = Student(
                name = "Student101",
                age = 16,
                emailId = "student101@gmail.com",
                mobileNo = "1122334455",
                gender = "Female",
                courseList = mutableListOf(course)
            )
            department = Department(
                name = "Kotlin with SpringBoot",
                courseList = mutableListOf(course),
                teacherList = mutableListOf(teacher)
            )
            course.teacher = teacher
            course.department = department
            teacher.department = department
            teacher.courseList = mutableListOf(course)
            course.studentList = mutableListOf(student)

            courseRepository.save(course)
            departmentRepository.save(department)
            studentRepository.save(student)
            teacherRepository.save(teacher)
        }
    }
}