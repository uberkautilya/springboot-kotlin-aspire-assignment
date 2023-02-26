package io.mcm.kotlinaspireassignment.exceptionhandling

import io.mcm.kotlinaspireassignment.exceptionhandling.exception.CourseException
import io.mcm.kotlinaspireassignment.exceptionhandling.exception.DepartmentException
import io.mcm.kotlinaspireassignment.exceptionhandling.exception.StudentException
import io.mcm.kotlinaspireassignment.exceptionhandling.exception.TeacherException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val errorMap: MutableMap<String, String?> = HashMap()
        var status: HttpStatus = HttpStatus.BAD_REQUEST
        ex.bindingResult.fieldErrors.forEach { errorMap[it.field] = it.defaultMessage }
        return ResponseEntity(errorMap, status)
    }

    @ExceptionHandler(CourseException::class)
    fun handleCourseException(exception: CourseException, request: WebRequest): ResponseEntity<Any> {
        val mutableMap: MutableMap<String, Any> = HashMap()
        var status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR
        mutableMap["Error"] = when (exception) {
            is CourseException.CourseNotFoundException -> {
                status = HttpStatus.NOT_FOUND
                "Course not found: ${exception.message}: ${request.parameterMap.entries}"
            }

            is CourseException.CourseNotPersistedException -> {
                status = HttpStatus.BAD_REQUEST
                "Course not persisted: ${exception.message}: ${request.parameterMap.entries}"
            }

            is CourseException.InvalidFileNameForCourseContentException -> {
                status = HttpStatus.NO_CONTENT
                "Course content not updated: ${exception.message}: ${request.parameterMap.entries}"
            }
        }
        return ResponseEntity(mutableMap, status)
    }

    @ExceptionHandler(StudentException::class)
    fun handleStudentException(exception: StudentException, request: WebRequest): ResponseEntity<Any> {
        val mutableMap: MutableMap<String, Any> = HashMap()
        var status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR
        mutableMap["Error"] = when (exception) {
            is StudentException.StudentNotFoundException -> {
                status = HttpStatus.NOT_FOUND
                "Student not found: ${exception.message}: ${request.parameterMap.entries}"
            }

            is StudentException.StudentNotPersistedException -> {
                status = HttpStatus.BAD_REQUEST
                "Student not persisted: ${exception.message}: ${request.parameterMap.entries}"
            }
        }
        return ResponseEntity(mutableMap, status)
    }

    @ExceptionHandler(TeacherException::class)
    fun handleTeacherException(exception: TeacherException, request: WebRequest): ResponseEntity<Any> {
        val mutableMap: MutableMap<String, Any> = HashMap()
        var status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR
        mutableMap["Error"] = when (exception) {
            is TeacherException.TeacherNotFoundException -> {
                status = HttpStatus.NOT_FOUND
                "Teacher not found: ${exception.message}: ${request.parameterMap.entries}"
            }

            is TeacherException.TeacherNotPersistedException -> {
                status = HttpStatus.BAD_REQUEST
                "Teacher not persisted: ${exception.message}: ${request.parameterMap.entries}"
            }
        }
        return ResponseEntity(mutableMap, status)
    }

    @ExceptionHandler(DepartmentException::class)
    fun handleDepartmentException(exception: DepartmentException, request: WebRequest): ResponseEntity<Any> {
        val mutableMap: MutableMap<String, Any> = HashMap()
        var status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR
        mutableMap["Error"] = when (exception) {
            is DepartmentException.DepartmentNotFoundException -> {
                status = HttpStatus.NOT_FOUND
                "Department not found: ${exception.message}: ${request.parameterMap.entries}"
            }

            is DepartmentException.DepartmentNotPersistedException -> {
                status = HttpStatus.BAD_REQUEST
                "Department not persisted: ${exception.message}: ${request.parameterMap.entries}"
            }
        }
        return ResponseEntity(mutableMap, status)
    }

}
