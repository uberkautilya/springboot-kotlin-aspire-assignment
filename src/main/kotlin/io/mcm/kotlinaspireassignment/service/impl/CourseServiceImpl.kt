package io.mcm.kotlinaspireassignment.service.impl

import io.mcm.kotlinaspireassignment.exceptionhandling.exception.CourseException
import io.mcm.kotlinaspireassignment.model.CourseRequest
import io.mcm.kotlinaspireassignment.model.CourseResponse
import io.mcm.kotlinaspireassignment.model.entity.Course
import io.mcm.kotlinaspireassignment.repository.CourseRepository
import io.mcm.kotlinaspireassignment.service.CourseService
import io.mcm.kotlinaspireassignment.specification.CourseSpecification
import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

@Service
class CourseServiceImpl(val courseRepository: CourseRepository): CourseService {

    private val logger = LoggerFactory.getLogger(CourseServiceImpl::class.java)

    @Value("\${default.pageSize.courses:3}")
    private var defaultPageSize: Int = 1

    override fun findAll(): CourseResponse {
        val courseList = courseRepository.findAll()
        logger.info("$courseList")
        return CourseResponse(courseList)
    }

    override fun findById(id: Int): CourseResponse {
        val courseById =
            courseRepository.findById(id).orElseThrow { CourseException.CourseNotFoundException() }
        val courseContentFileName = courseById.fileName
        if (StringUtils.isNotBlank(courseContentFileName)) {
            val fileName = courseContentFileName!!.split(".")
            val parentPath = Paths.get("D:/Assignment/${fileName[0]}")
            Files.createDirectories(parentPath)
            val filePath =
                Paths.get("D:/Assignment/${fileName[0]}/$courseContentFileName")
            Files.write(filePath, courseById.courseContent)
        } else {
            logger.warn("Course content file name is null. Not saving data on disk")
        }
        return CourseResponse(mutableListOf(courseById))
    }

    override fun save(courseRequest: CourseRequest): CourseResponse {
        val courseList = courseRepository.saveAll(courseRequest.courseList)
        return CourseResponse(courseList)
    }

    override fun update(courseRequest: CourseRequest): CourseResponse {
        val courseInDBList = mutableListOf<Course>()
        for (course in courseRequest.courseList) {
            if (null == course.id) {
                continue
            }
            val courseInDB = courseRepository.findById(course.id!!)
                .orElseThrow { throw CourseException.CourseNotFoundException() }
            courseInDB.name = course.name
            courseInDB.department = course.department
            courseInDB.endDate = course.endDate
            courseInDB.startDate = course.startDate
            courseInDB.studentList = course.studentList
            courseInDB.teacher = course.teacher
            courseInDBList.add(courseInDB)
        }
        val savedCourseList = courseRepository.saveAll(courseInDBList)
        return CourseResponse(savedCourseList)
    }

    override fun delete(courseRequest: CourseRequest): CourseResponse {
        val courseInDBList = mutableListOf<Course>()
        for (course in courseRequest.courseList) {
            if (null == course.id) {
                continue
            }
            val courseInDB = courseRepository.findById(course.id!!)
                .orElseThrow { throw CourseException.CourseNotFoundException() }
            courseInDBList.add(courseInDB)
        }
        courseRepository.deleteAll(courseInDBList)
        return CourseResponse(courseInDBList)
    }

    override fun filter(courseRequest: CourseRequest): CourseResponse {
        var page: Page<Course>
        val courseFilter = courseRequest.courseFilter
        if (Objects.isNull(courseFilter.pageNo)) {
            page = PageImpl(courseRepository.findAll(CourseSpecification.build(courseFilter)))
        } else {
            if (Objects.isNull(courseFilter.pageSize) || courseFilter.pageSize == 0) {
                courseFilter.pageSize = defaultPageSize
            }
            logger.debug("CourseService.filter: pageNumber: ${courseFilter.pageNo}, pageSize: ${courseFilter.pageSize}")
            val pageable = PageRequest.of(courseFilter.pageNo!! - 1, courseFilter.pageSize!!)
            page = courseRepository.findAll(CourseSpecification.build(courseFilter), pageable)
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

    override fun updateCourseContent(courseId: Int, courseContent: MultipartFile): CourseResponse {
        if (0 == courseId) {
            throw CourseException.CourseNotFoundException()
        }
        if (null == courseContent.originalFilename || courseContent.originalFilename == "") {
            throw CourseException.InvalidFileNameForCourseContentException()
        }
        val fileNameWithExtension: String = courseContent.originalFilename!!
        val course = courseRepository.findById(courseId).orElseThrow {
            CourseException.CourseNotFoundException("Course Id: $courseId not found")
        }
        course.courseContent = courseContent.bytes
        course.fileName = fileNameWithExtension
        return CourseResponse(mutableListOf(courseRepository.save(course)))
    }

}
