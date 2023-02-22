package io.mcm.kotlinaspireassignment.service

import io.mcm.kotlinaspireassignment.exceptionhandling.exception.CourseManagementException
import io.mcm.kotlinaspireassignment.model.DepartmentResponse
import io.mcm.kotlinaspireassignment.model.StudentResponse
import io.mcm.kotlinaspireassignment.model.TeacherRequest
import io.mcm.kotlinaspireassignment.model.TeacherResponse
import io.mcm.kotlinaspireassignment.model.entity.Department
import io.mcm.kotlinaspireassignment.model.entity.Student
import io.mcm.kotlinaspireassignment.model.entity.Teacher
import io.mcm.kotlinaspireassignment.repository.TeacherRepository
import io.mcm.kotlinaspireassignment.specification.TeacherSpecification
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.util.*

@Service
class TeacherService(val teacherRepository: TeacherRepository) {
    private val teacherServiceLogger = LoggerFactory.getLogger(TeacherService::class.java)

    @Value("\${default.pageSize.teachers:3}")
    private var defaultPageSize: Int = 0

    fun findAll(): TeacherResponse {
        val teacherList = teacherRepository.findAll()
        return TeacherResponse(teacherList)
    }

    fun findById(id: Int): TeacherResponse {
        val teacherByIdOptional = teacherRepository.findById(id)
        val teacherById = teacherByIdOptional.orElseThrow { throw CourseManagementException.TeacherNotFoundException() }
        return TeacherResponse(mutableListOf(teacherById))
    }

    fun save(teacherRequest: TeacherRequest): TeacherResponse {
        val teacherList = teacherRepository.saveAll(teacherRequest.teacherList)
        return TeacherResponse(teacherList)
    }

    fun update(teacherRequest: TeacherRequest): TeacherResponse {
        val teacherInDBList = mutableListOf<Teacher>()
        for (teacher in teacherRequest.teacherList) {
            val teacherInDB = teacherRepository.findById(teacher.id)
                .orElseThrow { throw CourseManagementException.TeacherNotFoundException() }
            teacherInDB.name = teacher.name
            teacherInDB.courseList = teacher.courseList
            teacherInDB.dept = teacher.dept
            teacherInDBList.add(teacherInDB)
        }
        val savedTeacherList = teacherRepository.saveAll(teacherInDBList)
        return TeacherResponse(savedTeacherList)
    }

    fun delete(teacherRequest: TeacherRequest): TeacherResponse {
        val teacherInDBList = mutableListOf<Teacher>()
        for (teacher in teacherRequest.teacherList) {
            val teacherInDB = teacherRepository.findById(teacher.id)
                .orElseThrow { throw CourseManagementException.TeacherNotFoundException() }
            teacherInDBList.add(teacherInDB)
        }
        teacherRepository.deleteAll(teacherRequest.teacherList)
        return TeacherResponse(teacherInDBList)
    }

    fun filter(request: TeacherRequest): TeacherResponse {
        var page: Page<Teacher>
        if (Objects.isNull(request.pageNo)) {
            page = PageImpl(teacherRepository.findAll(TeacherSpecification.build(request)))
        } else {
            if (Objects.isNull(request.pageSize)) {
                request.pageSize = defaultPageSize
            }
            teacherServiceLogger.debug("TeacherService.filter: pageNumber: ${request.pageNo}, pageSize: ${request.pageSize}")
            val pageable = PageRequest.of(request.pageNo - 1, request.pageSize)
            page = teacherRepository.findAll(TeacherSpecification.build(request), pageable)
        }
        val list: List<Teacher> = page.content
        val teacherResponse = TeacherResponse(mutableListOf<Teacher>())
        if (list.isNotEmpty()) {
            teacherResponse.totalPages = page.totalPages
            teacherResponse.totalCount = page.totalElements
            teacherResponse.pageNo = page.number + 1
            for (teacher in list) {
                teacherResponse.teacherList.add(teacher)
            }
        }
        return teacherResponse
    }
}
