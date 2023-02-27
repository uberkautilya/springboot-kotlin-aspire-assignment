package io.mcm.kotlinaspireassignment.service

import io.mcm.kotlinaspireassignment.model.CourseRequest
import io.mcm.kotlinaspireassignment.model.CourseResponse
import org.springframework.web.multipart.MultipartFile
import java.util.*

interface CourseService {

    fun findAll(): CourseResponse
    fun findById(id: Int, locale: Locale?): CourseResponse
    fun save(courseRequest: CourseRequest): CourseResponse
    fun update(courseRequest: CourseRequest): CourseResponse
    fun delete(courseRequest: CourseRequest): CourseResponse
    fun filter(courseRequest: CourseRequest): CourseResponse
    fun updateCourseContent(courseId: Int, courseContent: MultipartFile): CourseResponse

}
