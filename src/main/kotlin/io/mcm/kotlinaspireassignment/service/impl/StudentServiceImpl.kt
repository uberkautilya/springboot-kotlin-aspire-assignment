package io.mcm.kotlinaspireassignment.service.impl

import io.mcm.kotlinaspireassignment.exceptionhandling.exception.StudentException
import io.mcm.kotlinaspireassignment.model.StudentRequest
import io.mcm.kotlinaspireassignment.model.StudentResponse
import io.mcm.kotlinaspireassignment.model.dto.StudentDto
import io.mcm.kotlinaspireassignment.model.entity.Course
import io.mcm.kotlinaspireassignment.model.entity.Student
import io.mcm.kotlinaspireassignment.repository.CourseRepository
import io.mcm.kotlinaspireassignment.repository.StudentRepository
import io.mcm.kotlinaspireassignment.service.StudentService
import io.mcm.kotlinaspireassignment.specification.StudentSpecification
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.jvm.optionals.getOrDefault

@Service
class StudentServiceImpl(val studentRepository: StudentRepository): StudentService {
    private val logger = LoggerFactory.getLogger(StudentServiceImpl::class.java)
    @Autowired
    lateinit var courseRepository: CourseRepository

    @Value("\${default.pageSize.students:3}")
    private var defaultPageSize: Int = 1

    /**
     * Fetch all entries of Student entity from the DB
     */
    override fun findAll(): StudentResponse {
        val studentList = studentRepository.findAll()
        return StudentResponse(studentList)
    }

    /**
     * Fetch Student entity of id from the request
     */
    override fun findById(id: Int): StudentResponse {
        val studentById = studentRepository.findById(id)
        val studentByIdVal = studentById.orElseThrow { throw StudentException.StudentNotFoundException() }
        return StudentResponse(mutableListOf(studentByIdVal))
    }

    /**
     * Save a list of Student entities part of the StudentRequest
     */
    override fun save(studentRequest: StudentRequest): StudentResponse {
        val studentRequestList = StudentDto.getStudentEntityListFromDtoList(studentRequest.studentList)

        for (student in studentRequestList) {
            if (student.courseList != null) {
                val courseInDBList = mutableListOf<Course>()
                for (course in student.courseList!!) {
                    if (course.id != null) {
                        var courseInDB: Course = courseRepository.findById(course.id!!).getOrDefault(course)
                        courseInDBList.add(courseInDB)
                    } else {
                        //Create this record, since the id is not available
                        courseInDBList.add(course)
                    }
                }
                student.courseList = courseInDBList
            }
        }

        val studentList = studentRepository.saveAll(studentRequestList)
        return StudentResponse(studentList)
    }

    /**
     * Update Student entities in the list from the StudentRequest
     */
    override fun update(studentRequest: StudentRequest): StudentResponse {
        val studentRequestList = StudentDto.getStudentEntityListFromDtoList(studentRequest.studentList)
        val studentInDBList = mutableListOf<Student>()
        for (student in studentRequestList) {
            if (null == student.id) {
                continue
            }
            val studentInDB = studentRepository.findById(student.id!!)
                .orElseThrow { throw StudentException.StudentNotFoundException() }
            studentInDB.name = student.name
            studentInDB.emailId = student.emailId
            studentInDB.mobileNo = student.mobileNo
            studentInDB.gender = student.gender
            studentInDB.age = student.age
            studentInDB.courseList = student.courseList
            studentInDBList.add(studentInDB)
        }
        val savedStudentList = studentRepository.saveAll(studentInDBList)
        return StudentResponse(savedStudentList)
    }

    /**
     * Delete list of students provided in the StudentRequest
     */
    override fun delete(studentRequest: StudentRequest): StudentResponse {
        val studentInDBList = mutableListOf<Student>()
        for (student in studentRequest.studentList) {
            if (null == student.id) {
                continue
            }
            val studentInDB = studentRepository.findById(student.id)
                .orElseThrow { throw StudentException.StudentNotFoundException() }
            studentInDBList.add(studentInDB)
        }
        studentRepository.deleteAll(studentInDBList)
        return StudentResponse(studentInDBList)
    }

    /**
     * Filter Student entities as per the criteria in studentFilter, of StudentRequest
     */
    @Transactional(readOnly = true)
    override fun filter(studentRequest: StudentRequest): StudentResponse {
        var page: Page<Student>
        val studentFilter = studentRequest.studentFilter
        if (Objects.isNull(studentFilter.pageNo)) {
            page = PageImpl(studentRepository.findAll(StudentSpecification.build(studentFilter)))
        } else {
            if (Objects.isNull(studentFilter.pageSize) || studentFilter.pageSize == 0) {
                studentFilter.pageSize = defaultPageSize
            }
            logger.debug("StudentService.filter: pageNumber: ${studentFilter.pageNo}, pageSize: ${studentFilter.pageSize}")
            val pageable = PageRequest.of(studentFilter.pageNo!! - 1, studentFilter.pageSize!!)
            page = studentRepository.findAll(StudentSpecification.build(studentFilter), pageable)
        }
        val list: List<Student> = page.content
        val studentResponse = StudentResponse(mutableListOf<Student>())
        if (list.isNotEmpty()) {
            studentResponse.totalPages = page.totalPages
            studentResponse.totalCount = page.totalElements
            studentResponse.pageNo = page.number + 1
            for (student in list) {
                studentResponse.studentList.add(student)
            }
        }
        return studentResponse
    }
}
