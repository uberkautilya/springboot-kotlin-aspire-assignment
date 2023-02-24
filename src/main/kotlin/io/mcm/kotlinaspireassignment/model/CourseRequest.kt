package io.mcm.kotlinaspireassignment.model

import io.mcm.kotlinaspireassignment.model.entity.Course
import org.springframework.web.multipart.MultipartFile

class CourseRequest {
    val courseFilter: CourseFilter = CourseFilter()
    val courseList: List<Course> = listOf()
    //To add course content for a courseId
    val courseId: Int = 0
    val courseContent: MultipartFile? = null
}