package io.mcm.kotlinaspireassignment.controller

import io.mcm.kotlinaspireassignment.model.CourseRequest
import io.mcm.kotlinaspireassignment.model.CourseResponse
import io.mcm.kotlinaspireassignment.service.CourseService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/api/courses")
class CourseController(val courseService: CourseService) {
    fun findAll() {
        return courseService.findAll()
    }
    @GetMapping(name = "/{id}")
    fun findById(@PathVariable id: Int) {
        return courseService.findById(id)
    }
    @PostMapping
    fun save(@RequestBody courseRequest: CourseRequest) {
        return courseService.save(courseRequest)
    }
    @PutMapping
    fun update(@RequestBody courseRequest: CourseRequest) {
        return courseService.update(courseRequest)
    }
    @DeleteMapping
    fun delete(@RequestBody courseRequest: CourseRequest) {
        return courseService.delete(courseRequest)
    }
    @PostMapping("/filter")
    fun filterFind(@RequestBody courseRequest: CourseRequest): CourseResponse {
        return courseService.filter(courseRequest)
    }
}