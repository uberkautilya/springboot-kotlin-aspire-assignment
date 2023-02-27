package io.mcm.kotlinaspireassignment.controller

import io.mcm.kotlinaspireassignment.model.TeacherRequest
import io.mcm.kotlinaspireassignment.model.TeacherResponse
import io.mcm.kotlinaspireassignment.service.TeacherService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

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
    fun save(@Valid @RequestBody teacherRequest: TeacherRequest): ResponseEntity<TeacherResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(teacherService.save(teacherRequest))
    }

    @PutMapping
    fun update(@Valid @RequestBody teacherRequest: TeacherRequest): ResponseEntity<TeacherResponse> {
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

    @GetMapping("/findAllByJoiningDateBetween")
    fun findAllByJoiningDateBetween(
        @RequestParam("joiningDateMin") @DateTimeFormat(style = "yyyy-MM-dd") joiningDateMin: String,
        @RequestParam("joiningDateMax") @DateTimeFormat(style = "yyyy-MM-dd") joiningDateMax: String
    ): ResponseEntity<TeacherResponse> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(teacherService.findAllByJoiningDateBetween(joiningDateMin, joiningDateMax))
    }

    @GetMapping("/findAllBySalaryBetweenAndAgeBetween")
    fun findAllBySalaryBetweenAndAgeBetween(
        @RequestParam("salaryMin") salaryMin: Long,
        @RequestParam("salaryMax") salaryMax: Long,
        @RequestParam("ageMin") ageMin: Int,
        @RequestParam("ageMax") ageMax: Int
    ): ResponseEntity<TeacherResponse> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(teacherService.findAllBySalaryBetweenAndAgeBetween(salaryMin, salaryMax, ageMin, ageMax))
    }
}