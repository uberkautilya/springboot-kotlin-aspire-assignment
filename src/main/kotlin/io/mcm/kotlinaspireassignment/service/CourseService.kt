package io.mcm.kotlinaspireassignment.service

import io.mcm.kotlinaspireassignment.model.CourseRequest
import io.mcm.kotlinaspireassignment.model.CourseResponse
import io.mcm.kotlinaspireassignment.model.entity.Course
import io.mcm.kotlinaspireassignment.repository.CourseRepository
import io.mcm.kotlinaspireassignment.specification.CourseSpecification
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.util.*

@Service
class CourseService(val courseRepository: CourseRepository, val courseSpecification: CourseSpecification) {

    private val courseServiceLogger = LoggerFactory.getLogger(CourseService::class.java)

    @Value("\${courses.default.pageSize}")
    private var defaultPageSize: Int = 0

    fun findAll() {
        courseRepository.findAll()
    }

    fun findById(id: Int) {
        courseRepository.findById(id)
    }

    fun save(courseRequest: CourseRequest) {
        courseRepository.saveAll(courseRequest.courseList)
    }

    fun update(courseRequest: CourseRequest) {
        courseRepository.saveAll(courseRequest.courseList)
    }

    fun delete(courseRequest: CourseRequest) {
        courseRepository.deleteAll(courseRequest.courseList)
    }

    fun filter(request: CourseRequest): CourseResponse {
        var page: Page<Course>
        if (Objects.isNull(request.pageNo)) {
            page = PageImpl(courseRepository.findAll(courseSpecification.build(request)))
        } else {
            if (Objects.isNull(request.pageSize)) {
                request.pageSize = defaultPageSize
            }
            courseServiceLogger.debug("CourseService.filter: pageNumber: ${request.pageNo}, pageSize: ${request.pageSize}")
            val pageable = PageRequest.of(request.pageNo - 1, request.pageSize)
            page = courseRepository.findAll(courseSpecification.build(request), pageable)
        }
        val list: List<Course> = page.content
        val courseResponse = CourseResponse(mutableListOf())
        if (list.isNotEmpty()) {
            courseResponse.totalPages = page.totalPages
            courseResponse.totalCount = page.totalElements
            courseResponse.pageNo = page.number + 1
            for (course in list) {
                courseResponse.courseList.add(course)
            }
        }
        return courseResponse
    }

}
