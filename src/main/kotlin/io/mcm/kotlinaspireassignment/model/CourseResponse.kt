package io.mcm.kotlinaspireassignment.model

import io.mcm.kotlinaspireassignment.model.entity.Course

class CourseResponse(val courseList: MutableList<Course>) {
    var totalPages = 0
    var totalCount: Long = 0L
    var pageNo: Int = 0
    override fun toString(): String {
        return "CourseResponse(courseList=$courseList, totalPages=$totalPages, totalCount=$totalCount, pageNo=$pageNo)"
    }
}