package io.mcm.kotlinaspireassignment.exceptionhandling.exception

sealed class StudentException(
    message: String? = null,
    cause: Throwable? = null,
    enableSuppression: Boolean = false,
    writableStackTrace: Boolean = false
) : Throwable(message, cause, enableSuppression, writableStackTrace) {
    class StudentNotFoundException(message: String? = null) : StudentException(message = message)
    class StudentNotPersistedException(message: String? = null): StudentException(message = message)
}