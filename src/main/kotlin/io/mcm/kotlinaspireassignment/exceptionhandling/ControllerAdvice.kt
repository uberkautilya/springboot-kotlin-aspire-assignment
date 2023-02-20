package io.mcm.kotlinaspireassignment.exceptionhandling

import io.mcm.kotlinaspireassignment.exceptionhandling.exception.CourseManagementException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ExceptionHandler: ResponseEntityExceptionHandler() {

    @ExceptionHandler(CourseManagementException::class)
    fun handleCourseManagementException(exception: CourseManagementException, request: WebRequest): ResponseEntity<Any> {
        return ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
