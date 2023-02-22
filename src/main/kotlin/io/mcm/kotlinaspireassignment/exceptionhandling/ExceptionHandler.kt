package io.mcm.kotlinaspireassignment.exceptionhandling

import io.mcm.kotlinaspireassignment.exceptionhandling.exception.CourseException
import io.mcm.kotlinaspireassignment.exceptionhandling.exception.DepartmentException
import io.mcm.kotlinaspireassignment.exceptionhandling.exception.StudentException
import io.mcm.kotlinaspireassignment.exceptionhandling.exception.TeacherException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(CourseException::class)
    fun handleCourseException(exception: CourseException, request: WebRequest): ResponseEntity<Any> {
        val mutableMap: MutableMap<String, Any> = HashMap()
        mutableMap["Error"] = when (exception) {
            is CourseException.CourseNotFoundException -> "Course not found: ${exception.message}: ${request.parameterMap.entries}"
            is CourseException.CourseNotPersistedException -> "Course not persisted: ${exception.message}: ${request.parameterMap.entries}"
        }
        return ResponseEntity(mutableMap, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(StudentException::class)
    fun handleStudentException(exception: StudentException, request: WebRequest): ResponseEntity<Any> {
        val mutableMap: MutableMap<String, Any> = HashMap()
        mutableMap["Error"] = when (exception) {
            is StudentException.StudentNotFoundException -> "Student not found: ${exception.message}: ${request.parameterMap.entries}"
            is StudentException.StudentNotPersistedException -> "Student not persisted: ${exception.message}: ${request.parameterMap.entries}"
        }
        return ResponseEntity(mutableMap, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(TeacherException::class)
    fun handleTeacherException(exception: TeacherException, request: WebRequest): ResponseEntity<Any> {
        val mutableMap: MutableMap<String, Any> = HashMap()
        mutableMap["Error"] = when (exception) {
            is TeacherException.TeacherNotFoundException -> "Teacher not found: ${exception.message}: ${request.parameterMap.entries}"
            is TeacherException.TeacherNotPersistedException -> "Teacher not persisted: ${exception.message}: ${request.parameterMap.entries}"
        }
        return ResponseEntity(mutableMap, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(DepartmentException::class)
    fun handleDepartmentException(exception: DepartmentException, request: WebRequest): ResponseEntity<Any> {
        val mutableMap: MutableMap<String, Any> = HashMap()
        mutableMap["Error"] = when (exception) {
            is DepartmentException.DepartmentNotFoundException -> "Department not found: ${exception.message}: ${request.parameterMap.entries}"
            is DepartmentException.DepartmentNotPersistedException -> "Department not persisted: ${exception.message}: ${request.parameterMap.entries}"
        }
        return ResponseEntity(mutableMap, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
