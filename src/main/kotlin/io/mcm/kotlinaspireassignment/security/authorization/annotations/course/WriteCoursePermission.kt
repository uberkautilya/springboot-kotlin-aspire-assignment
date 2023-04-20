package io.mcm.kotlinaspireassignment.security.authorization.annotations.course

import org.springframework.security.access.prepost.PreAuthorize

@Retention(AnnotationRetention.RUNTIME)
@PreAuthorize("hasAuthority('course.update')")
annotation class WriteCoursePermission(){

}
