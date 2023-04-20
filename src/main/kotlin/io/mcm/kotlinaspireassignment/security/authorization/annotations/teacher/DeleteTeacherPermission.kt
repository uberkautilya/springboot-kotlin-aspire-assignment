package io.mcm.kotlinaspireassignment.security.authorization.annotations.teacher

import org.springframework.security.access.prepost.PreAuthorize

@Retention(AnnotationRetention.RUNTIME)
@PreAuthorize("hasAuthority('teacher.delete')")
annotation class DeleteTeacherPermission(){

}
