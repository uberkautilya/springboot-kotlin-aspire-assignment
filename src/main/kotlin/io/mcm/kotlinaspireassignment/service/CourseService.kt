package io.mcm.kotlinaspireassignment.service

import io.mcm.kotlinaspireassignment.exceptionhandling.exception.CourseException
import io.mcm.kotlinaspireassignment.model.CourseRequest
import io.mcm.kotlinaspireassignment.model.CourseResponse
import io.mcm.kotlinaspireassignment.model.entity.Course
import io.mcm.kotlinaspireassignment.repository.CourseRepository
import io.mcm.kotlinaspireassignment.specification.CourseSpecification
import org.apache.commons.lang3.StringUtils
import org.apache.tomcat.util.http.fileupload.InvalidFileNameException
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
class CourseService(val courseRepository: CourseRepository) {

    private val logger = LoggerFactory.getLogger(CourseService::class.java)

    @Value("\${default.pageSize.courses:3}")
    private var defaultPageSize: Int = 1

    fun findAll(): MutableList<Course> {
        val courseList = courseRepository.findAll()
        logger.info("$courseList")
        return courseList
    }

    fun findById(id: Int): CourseResponse {
        val courseById =
            courseRepository.findById(id).orElseThrow { CourseException.CourseNotFoundException() }
        val courseContentFileName = courseById.fileName
        if (StringUtils.isNotBlank(courseContentFileName)) {
            val fileName = courseContentFileName.split(".")
            val parentPath = Paths.get("D:/Assignment/${fileName[0]}")
            Files.createDirectories(parentPath)
            val filePath =
                Paths.get("D:/Assignment/${fileName[0]}/$courseContentFileName")
            Files.write(filePath, courseById.courseContent)
        }
        return CourseResponse(mutableListOf(courseById))
    }

    fun save(courseRequest: CourseRequest): CourseResponse {
        val courseList = courseRepository.saveAll(courseRequest.courseList)
        return CourseResponse(courseList)
    }

    fun update(courseRequest: CourseRequest): CourseResponse {
        val courseInDBList = mutableListOf<Course>()
        for (course in courseRequest.courseList) {
            val courseInDB = courseRepository.findById(course.id)
                .orElseThrow { throw CourseException.CourseNotFoundException() }
            courseInDB.name = course.name
            courseInDB.dept = course.dept
            courseInDB.endDate = course.endDate
            courseInDB.startDate = course.startDate
            courseInDB.studentList = course.studentList
            courseInDB.teacher = course.teacher
            courseInDBList.add(courseInDB)
        }
        val savedCourseList = courseRepository.saveAll(courseInDBList)
        return CourseResponse(savedCourseList)
    }

    fun delete(courseRequest: CourseRequest): CourseResponse {
        val courseInDBList = mutableListOf<Course>()
        for (course in courseRequest.courseList) {
            val courseInDB = courseRepository.findById(course.id)
                .orElseThrow { throw CourseException.CourseNotFoundException() }
            courseInDBList.add(courseInDB)
        }
        courseRepository.deleteAll(courseRequest.courseList)
        return CourseResponse(courseInDBList)
    }

    fun filter(request: CourseRequest): CourseResponse {
        var page: Page<Course>
        if (Objects.isNull(request.pageNo)) {
            page = PageImpl(courseRepository.findAll(CourseSpecification.build(request)))
        } else {
            if (Objects.isNull(request.pageSize)) {
                request.pageSize = defaultPageSize
            }
            logger.debug("CourseService.filter: pageNumber: ${request.pageNo}, pageSize: ${request.pageSize}")
            val pageable = PageRequest.of(request.pageNo - 1, request.pageSize)
            page = courseRepository.findAll(CourseSpecification.build(request), pageable)
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

    fun updateCourseContent(courseId: Int, courseContent: MultipartFile): CourseResponse {
        val fileNameWithExtension = courseContent.originalFilename
        logger.debug("courseContent.originalFilename: $fileNameWithExtension")
        logger.debug("courseContent.size: ${courseContent.size}")
        val fileNameParts = fileNameWithExtension?.split(".")
            ?: throw InvalidFileNameException("courseContent", "$fileNameWithExtension is not a valid file name")
//        val parentPath = Paths.get("D:/Assignment/multipart-file/${fileNameParts[0]}")
//        Files.createDirectories(parentPath)
//        val filePath =
//            Paths.get("D:/Assignment/multipart-file/${fileNameParts[0]}/${fileNameParts[0]}.${fileNameParts[1]}")
//        Files.write(filePath, courseContent.bytes)
        val course = courseRepository.findById(courseId).orElseThrow {
            CourseException.CourseNotFoundException("Course Id: $courseId not found")
        }
        course.courseContent = courseContent.bytes
        course.fileName = fileNameWithExtension
        return CourseResponse(mutableListOf(courseRepository.save(course)))
    }

}
