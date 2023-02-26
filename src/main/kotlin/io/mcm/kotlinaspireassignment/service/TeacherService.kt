package io.mcm.kotlinaspireassignment.service

import io.mcm.kotlinaspireassignment.model.TeacherRequest
import io.mcm.kotlinaspireassignment.model.TeacherResponse

interface TeacherService {
    fun findAll(): TeacherResponse
    fun findById(id: Int): TeacherResponse
    fun save(teacherRequest: TeacherRequest): TeacherResponse
    fun update(teacherRequest: TeacherRequest): TeacherResponse
    fun delete(teacherRequest: TeacherRequest): TeacherResponse
    fun filter(teacherRequest: TeacherRequest): TeacherResponse
    fun findAllBySalaryAndJoiningDateBetween(
        salary: Long,
        joiningDateMin: String,
        joiningDateMax: String
    ): TeacherResponse
}
