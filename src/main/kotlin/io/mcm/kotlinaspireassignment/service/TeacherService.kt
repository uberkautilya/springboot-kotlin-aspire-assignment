package io.mcm.kotlinaspireassignment.service

import io.mcm.kotlinaspireassignment.model.TeacherRequest
import io.mcm.kotlinaspireassignment.model.TeacherResponse
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
class TeacherService(val teacherRepository: TeacherRepository, val teacherSpecification: TeacherSpecification) {
    private val teacherServiceLogger = LoggerFactory.getLogger(TeacherService::class.java)

    @Value("\${teachers.default.pageSize}")
    private var defaultPageSize: Int = 0

    fun findAll() {
        teacherRepository.findAll()
    }

    fun findById(id: Int) {
        teacherRepository.findById(id)
    }

    fun save(teacherRequest: TeacherRequest) {
        teacherRepository.saveAll(teacherRequest.teacherList)
    }

    fun update(teacherRequest: TeacherRequest) {
        teacherRepository.saveAll(teacherRequest.teacherList)
    }

    fun delete(teacherRequest: TeacherRequest) {
        teacherRepository.deleteAll(teacherRequest.teacherList)
    }

    fun filter(request: TeacherRequest): TeacherResponse {
        var page: Page<Teacher>
        if (Objects.isNull(request.pageNo)) {
            page = PageImpl(teacherRepository.findAll(teacherSpecification.build(request)))
        } else {
            if (Objects.isNull(request.pageSize)) {
                request.pageSize = defaultPageSize
            }
            teacherServiceLogger.debug("TeacherService.filter: pageNumber: ${request.pageNo}, pageSize: ${request.pageSize}")
            val pageable = PageRequest.of(request.pageNo - 1, request.pageSize)
            page = teacherRepository.findAll(teacherSpecification.build(request), pageable)
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
