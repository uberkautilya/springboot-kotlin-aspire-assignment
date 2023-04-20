package io.mcm.kotlinaspireassignment.security.authorization.annotations.course

import org.springframework.security.access.prepost.PreAuthorize
import java.lang.annotation.RetentionPolicy

@Retention(AnnotationRetention.RUNTIME)
@PreAuthorize("hasAuthority('course.update')")
annotation class UpdateCoursePermission(){

}
