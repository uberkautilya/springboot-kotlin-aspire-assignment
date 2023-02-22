package io.mcm.kotlinaspireassignment

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@OpenAPIDefinition(info = Info(title = "Course Management API", version = "1.0", description = "Course Management with APIs that provide CRUD functionality on Course, Teachers, Departments and Students"))
class KotlinAspireAssignmentApplication

fun main(args: Array<String>) {
	runApplication<KotlinAspireAssignmentApplication>(*args)
}
