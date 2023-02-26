package io.mcm.kotlinaspireassignment.service

import io.mcm.kotlinaspireassignment.model.StudentRequest
import io.mcm.kotlinaspireassignment.model.StudentResponse

interface StudentService {
    fun findAll(): StudentResponse
    fun findById(id: Int): StudentResponse
    fun save(studentRequest: StudentRequest): StudentResponse
    fun update(studentRequest: StudentRequest): StudentResponse
    fun delete(studentRequest: StudentRequest): StudentResponse
    fun filter(studentRequest: StudentRequest): StudentResponse
}
