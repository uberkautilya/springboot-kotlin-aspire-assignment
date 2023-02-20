package io.mcm.kotlinaspireassignment.controller

import io.mcm.kotlinaspireassignment.model.DepartmentRequest
import io.mcm.kotlinaspireassignment.model.DepartmentResponse
import io.mcm.kotlinaspireassignment.model.entity.Department
import io.mcm.kotlinaspireassignment.service.DepartmentService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/v1/api/departments")
class DepartmentController(val departmentService: DepartmentService) {
    @GetMapping
    fun findAll(): MutableList<Department> {
        return departmentService.findAll()
    }
    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): Optional<Department> {
        return departmentService.findById(id)
    }
    @PostMapping
    fun save(@RequestBody departmentRequest: DepartmentRequest): MutableList<Department> {
        return departmentService.save(departmentRequest)
    }
    @PutMapping
    fun update(@RequestBody departmentRequest: DepartmentRequest): MutableList<Department> {
        return departmentService.save(departmentRequest)
    }
    @DeleteMapping
    fun delete(@RequestBody departmentRequest: DepartmentRequest) {
        return departmentService.delete(departmentRequest)
    }
    @PostMapping("/filter")
    fun filter(@RequestBody departmentRequest: DepartmentRequest): DepartmentResponse {
        return departmentService.filter(departmentRequest)
    }
}