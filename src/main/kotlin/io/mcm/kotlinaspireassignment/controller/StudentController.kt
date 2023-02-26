package io.mcm.kotlinaspireassignment.controller

import io.mcm.kotlinaspireassignment.model.StudentRequest
import io.mcm.kotlinaspireassignment.model.StudentResponse
import io.mcm.kotlinaspireassignment.service.StudentService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/students", produces = [MediaType.APPLICATION_JSON_VALUE])
class StudentController(val studentService: StudentService) {
    @GetMapping
    fun findAll(): ResponseEntity<StudentResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.findAll())
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): ResponseEntity<StudentResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.findById(id))
    }

    @PostMapping
    fun save(@RequestBody studentRequest: StudentRequest): ResponseEntity<StudentResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.save(studentRequest))
    }

    @PutMapping
    fun update(@RequestBody studentRequest: StudentRequest): ResponseEntity<StudentResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.save(studentRequest))
    }

    @DeleteMapping
    fun delete(@RequestBody studentRequest: StudentRequest): ResponseEntity<StudentResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.delete(studentRequest))
    }

    @PostMapping("/filter")
    fun filter(@RequestBody studentRequest: StudentRequest): ResponseEntity<StudentResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.filter(studentRequest))
    }
}