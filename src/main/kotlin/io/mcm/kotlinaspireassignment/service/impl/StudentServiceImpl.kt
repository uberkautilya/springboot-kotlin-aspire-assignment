package io.mcm.kotlinaspireassignment.service.impl

import io.mcm.kotlinaspireassignment.exceptionhandling.exception.StudentException
import io.mcm.kotlinaspireassignment.model.StudentRequest
import io.mcm.kotlinaspireassignment.model.StudentResponse
import io.mcm.kotlinaspireassignment.model.dto.StudentDto
import io.mcm.kotlinaspireassignment.model.entity.Student
import io.mcm.kotlinaspireassignment.repository.StudentRepository
import io.mcm.kotlinaspireassignment.service.StudentService
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
class StudentServiceImpl(val studentRepository: StudentRepository): StudentService {
    private val logger = LoggerFactory.getLogger(StudentServiceImpl::class.java)

    @Value("\${default.pageSize.students:3}")
    private var defaultPageSize: Int = 1

    override fun findAll(): StudentResponse {
        val studentList = studentRepository.findAll()
        return StudentResponse(studentList)
    }

    override fun findById(id: Int): StudentResponse {
        val studentById = studentRepository.findById(id)
        val studentByIdVal = studentById.orElseThrow { throw StudentException.StudentNotFoundException() }
        return StudentResponse(mutableListOf(studentByIdVal))
    }

    override fun save(studentRequest: StudentRequest): StudentResponse {
        val studentRequestList = StudentDto.getStudentEntityListFromDtoList(studentRequest.studentList)
        val studentList = studentRepository.saveAll(studentRequestList)
        return StudentResponse(studentList)
    }

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
            studentInDB.courseList = student.courseList
            studentInDBList.add(studentInDB)
        }
        val savedStudentList = studentRepository.saveAll(studentInDBList)
        return StudentResponse(savedStudentList)
    }

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
