package io.mcm.kotlinaspireassignment.controller

import io.mcm.kotlinaspireassignment.service.StudentService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/api/students")
class StudentController(val studentService: StudentService) {
}