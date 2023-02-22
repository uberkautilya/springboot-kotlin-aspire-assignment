package io.mcm.kotlinaspireassignment.exceptionhandling.exception

sealed class TeacherException(
    message: String? = null,
    cause: Throwable? = null,
    enableSuppression: Boolean = false,
    writableStackTrace: Boolean = false
) : Throwable(message, cause, enableSuppression, writableStackTrace) {
    class TeacherNotFoundException(message: String? = null) : TeacherException(message = message)
    class TeacherNotPersistedException(message: String? = null): TeacherException(message = message)
}