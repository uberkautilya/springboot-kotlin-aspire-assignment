package io.mcm.kotlinaspireassignment.service

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
import java.util.*

@Service
class StudentService(val studentRepository: StudentRepository) {
    private val studentServiceLogger = LoggerFactory.getLogger(StudentService::class.java)

    @Value("\${default.pageSize.students:3}")
    private var defaultPageSize: Int = 0

    fun findAll() {
        studentRepository.findAll()
    }

    fun findById(id: Int) {
        studentRepository.findById(id)
    }

    fun save(studentRequest: StudentRequest) {
        studentRepository.saveAll(studentRequest.studentList)
    }

    fun update(studentRequest: StudentRequest) {
        studentRepository.saveAll(studentRequest.studentList)
    }

    fun delete(studentRequest: StudentRequest) {
        studentRepository.deleteAll(studentRequest.studentList)
    }

    fun filter(request: StudentRequest): StudentResponse {
        var page: Page<Student>
        if (Objects.isNull(request.pageNo)) {
            page = PageImpl(studentRepository.findAll(StudentSpecification.build(request)))
        } else {
            if (Objects.isNull(request.pageSize)) {
                request.pageSize = defaultPageSize
            }
            studentServiceLogger.debug("StudentService.filter: pageNumber: ${request.pageNo}, pageSize: ${request.pageSize}")
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
