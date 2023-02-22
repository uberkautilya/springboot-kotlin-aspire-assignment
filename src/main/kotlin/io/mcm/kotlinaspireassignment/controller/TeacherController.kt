package io.mcm.kotlinaspireassignment.controller

import io.mcm.kotlinaspireassignment.model.TeacherRequest
import io.mcm.kotlinaspireassignment.model.TeacherResponse
import io.mcm.kotlinaspireassignment.service.TeacherService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/teachers", produces = [MediaType.APPLICATION_JSON_VALUE])
class TeacherController(val teacherService: TeacherService) {
    @GetMapping
    fun findAll(): ResponseEntity<TeacherResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(teacherService.findAll())
    }
    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): ResponseEntity<TeacherResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(teacherService.findById(id))
    }
    @PostMapping
    fun save(@RequestBody teacherRequest: TeacherRequest): ResponseEntity<TeacherResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(teacherService.save(teacherRequest))
    }
    @PutMapping
    fun update(@RequestBody teacherRequest: TeacherRequest): ResponseEntity<TeacherResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(teacherService.save(teacherRequest))
    }
    @DeleteMapping
    fun delete(@RequestBody teacherRequest: TeacherRequest): ResponseEntity<TeacherResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(teacherService.delete(teacherRequest))
    }
    @PostMapping("/filter")
    fun filter(@RequestBody teacherRequest: TeacherRequest): ResponseEntity<TeacherResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(teacherService.filter(teacherRequest))
    }
}