package io.mcm.kotlinaspireassignment.exceptionhandling.exception

sealed class DepartmentException(
    message: String? = null,
    cause: Throwable? = null,
    enableSuppression: Boolean = false,
    writableStackTrace: Boolean = false
) : Throwable(message, cause, enableSuppression, writableStackTrace) {
    class DepartmentNotFoundException(message: String? = null) : DepartmentException(message = message)
    class DepartmentNotPersistedException(message: String? = null): DepartmentException(message = message)
}