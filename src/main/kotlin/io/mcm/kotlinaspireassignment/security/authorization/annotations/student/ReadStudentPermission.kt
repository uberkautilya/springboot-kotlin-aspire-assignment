package io.mcm.kotlinaspireassignment.security.authorization.annotations.student

import org.springframework.security.access.prepost.PreAuthorize

@Retention(AnnotationRetention.RUNTIME)
@PreAuthorize("hasAuthority('student.read')")
annotation class ReadStudentPermission(){

}
