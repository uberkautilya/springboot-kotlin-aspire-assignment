package io.mcm.kotlinaspireassignment.controller

import io.mcm.kotlinaspireassignment.model.CourseRequest
import io.mcm.kotlinaspireassignment.model.CourseResponse
import io.mcm.kotlinaspireassignment.service.impl.CourseServiceImpl
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/courses", produces = [MediaType.APPLICATION_JSON_VALUE])
@Validated
class CourseController(val courseService: CourseServiceImpl) {
    @GetMapping
    fun findAll(): ResponseEntity<CourseResponse> {
        return ResponseEntity.ok(CourseResponse(courseService.findAll()))
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): ResponseEntity<CourseResponse> {
        return ResponseEntity.ok(courseService.findById(id))
    }

    @PostMapping
    fun save(@RequestBody courseRequest: @Valid CourseRequest): ResponseEntity<CourseResponse> {
        return ResponseEntity.ok(courseService.save(courseRequest))
    }

    @PutMapping
    fun update(@RequestBody courseRequest: CourseRequest): ResponseEntity<CourseResponse> {
        return ResponseEntity.ok(courseService.update(courseRequest))
    }

    @PutMapping(
        value = (["/uploadContent"]), consumes = [
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE
        ]
    )
    fun updateCourseContent(
        @RequestPart courseId: Int,
        @RequestPart(value = "courseContent", required = true) courseContent: MultipartFile
    ): ResponseEntity<CourseResponse> {
        return ResponseEntity.ok(courseService.updateCourseContent(courseId, courseContent))
    }

    @DeleteMapping
    fun delete(@RequestBody courseRequest: CourseRequest): ResponseEntity<CourseResponse> {
        return ResponseEntity.ok(courseService.delete(courseRequest))
    }

    @PostMapping("/filter")
    fun filterFind(@RequestBody courseRequest: CourseRequest): ResponseEntity<CourseResponse> {
        return ResponseEntity.ok(courseService.filter(courseRequest))
    }
}