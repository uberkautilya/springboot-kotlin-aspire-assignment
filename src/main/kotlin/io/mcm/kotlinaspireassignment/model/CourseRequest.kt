package io.mcm.kotlinaspireassignment.model

import io.mcm.kotlinaspireassignment.model.dto.CourseFilter
import io.mcm.kotlinaspireassignment.model.entity.Course

class CourseRequest {
    var pageSize: Int = 0
    val pageNo: Int = 0
    val courseFilter: CourseFilter = CourseFilter()
    val courseList: List<Course> = listOf()
}