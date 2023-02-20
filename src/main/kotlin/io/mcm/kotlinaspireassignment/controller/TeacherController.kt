package io.mcm.kotlinaspireassignment.controller

import io.mcm.kotlinaspireassignment.service.TeacherService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/api/teachers")
class TeacherController(val teacherService: TeacherService) {
}