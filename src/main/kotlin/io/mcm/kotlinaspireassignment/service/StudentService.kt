package io.mcm.kotlinaspireassignment.service

import io.mcm.kotlinaspireassignment.exceptionhandling.exception.StudentException
import io.mcm.kotlinaspireassignment.model.StudentRequest
import io.mcm.kotlinaspireassignment.model.StudentResponse
import io.mcm.kotlinaspireassignment.model.entity.Student
import io.mcm.kotlinaspireassignment.repository.StudentRepository
import io.mcm.kotlinaspireassignment.specification.StudentSpecification
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class StudentService(val studentRepository: StudentRepository) {
    private val logger = LoggerFactory.getLogger(StudentService::class.java)

    @Value("\${default.pageSize.students:3}")
    private var defaultPageSize: Int = 1

    fun findAll(): StudentResponse {
        val studentList = studentRepository.findAll()
        return StudentResponse(studentList)
    }

    fun findById(id: Int): StudentResponse {
        val studentById = studentRepository.findById(id)
        val studentByIdVal = studentById.orElseThrow { throw StudentException.StudentNotFoundException() }
        return StudentResponse(mutableListOf(studentByIdVal))
    }

    fun save(studentRequest: StudentRequest): StudentResponse {
        val studentList = studentRepository.saveAll(studentRequest.studentList)
        return StudentResponse(studentList)
    }

    fun update(studentRequest: StudentRequest): StudentResponse {
        val studentInDBList = mutableListOf<Student>()
        for (student in studentRequest.studentList) {
            val studentInDB = studentRepository.findById(student.id)
                .orElseThrow { throw StudentException.StudentNotFoundException() }
            studentInDB.name = student.name
            studentInDB.courseList = student.courseList
            studentInDB.dept = student.dept
            studentInDBList.add(studentInDB)
        }
        val savedStudentList = studentRepository.saveAll(studentInDBList)
        return StudentResponse(savedStudentList)
    }

    fun delete(studentRequest: StudentRequest): StudentResponse {
        val studentInDBList = mutableListOf<Student>()
        for (student in studentRequest.studentList) {
            val studentInDB = studentRepository.findById(student.id)
                .orElseThrow { throw StudentException.StudentNotFoundException() }
            studentInDBList.add(studentInDB)
        }
        studentRepository.deleteAll(studentRequest.studentList)
        return StudentResponse(studentInDBList)
    }

    @Transactional(readOnly = true)
    fun filter(request: StudentRequest): StudentResponse {
        var page: Page<Student>
        if (Objects.isNull(request.pageNo)) {
            page = PageImpl(studentRepository.findAll(StudentSpecification.build(request)))
        } else {
            if (Objects.isNull(request.pageSize)) {
                request.pageSize = defaultPageSize
            }
            logger.debug("StudentService.filter: pageNumber: ${request.pageNo}, pageSize: ${request.pageSize}")
            val pageable = PageRequest.of(request.pageNo - 1, request.pageSize)
            page = studentRepository.findAll(StudentSpecification.build(request), pageable)
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
