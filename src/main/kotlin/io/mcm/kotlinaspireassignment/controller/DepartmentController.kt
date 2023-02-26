package io.mcm.kotlinaspireassignment.controller

import io.mcm.kotlinaspireassignment.model.DepartmentRequest
import io.mcm.kotlinaspireassignment.model.DepartmentResponse
import io.mcm.kotlinaspireassignment.service.DepartmentService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/departments", produces = [MediaType.APPLICATION_JSON_VALUE])
class DepartmentController(val departmentService: DepartmentService) {
    @GetMapping
    fun findAll(): ResponseEntity<DepartmentResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(departmentService.findAll())
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): ResponseEntity<DepartmentResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(departmentService.findById(id))
    }

    @PostMapping
    fun save(@RequestBody departmentRequest: DepartmentRequest): ResponseEntity<DepartmentResponse> {
        return ResponseEntity.status(HttpStatus.OK).body((departmentService.save(departmentRequest)))
    }

    @PutMapping
    fun update(@RequestBody departmentRequest: DepartmentRequest): ResponseEntity<DepartmentResponse> {
        return ResponseEntity.status(HttpStatus.OK).body((departmentService.save(departmentRequest)))
    }

    @DeleteMapping
    fun delete(@RequestBody departmentRequest: DepartmentRequest): ResponseEntity<DepartmentResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(departmentService.delete(departmentRequest))
    }

    @PostMapping("/filter")
    fun filter(@RequestBody departmentRequest: DepartmentRequest): ResponseEntity<DepartmentResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(departmentService.filter(departmentRequest))
    }
}