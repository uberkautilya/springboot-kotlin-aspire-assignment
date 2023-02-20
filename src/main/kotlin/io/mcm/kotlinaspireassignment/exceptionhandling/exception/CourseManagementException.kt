package io.mcm.kotlinaspireassignment.exceptionhandling.exception

sealed class CourseManagementException(
    message: String? = null,
    cause: Throwable? = null,
    enableSuppression: Boolean = false,
    writableStackTrace: Boolean = false
) : Throwable(message, cause, enableSuppression, writableStackTrace) {
    class DepartmentNotFoundException(message: String? = null) : CourseManagementException(message = message)
    class CourseNotFoundException(message: String? = null) : CourseManagementException(message = message)
    class StudentNotFoundException(message: String? = null) : CourseManagementException(message = message)
    class TeacherNotFoundException(message: String? = null) : CourseManagementException(message = message)
    class DepartmentNotPersistedException(message: String? = null): CourseManagementException(message = message)
    class CourseNotPersistedException(message: String? = null): CourseManagementException(message = message)
    class StudentNotPersistedException(message: String? = null): CourseManagementException(message = message)
    class TeacherNotPersistedException(message: String? = null): CourseManagementException(message = message)
}