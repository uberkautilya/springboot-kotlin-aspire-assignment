package io.mcm.kotlinaspireassignment.exceptionhandling.exception

sealed class CourseException(
    message: String? = null,
    cause: Throwable? = null,
    enableSuppression: Boolean = false,
    writableStackTrace: Boolean = false
) : Throwable(message, cause, enableSuppression, writableStackTrace) {
    class CourseNotFoundException(message: String? = null) : CourseException(message = message)
    class CourseNotPersistedException(message: String? = null): CourseException(message = message)
    class InvalidFileNameForCourseContentException(message: String? = null): CourseException(message = message)
}