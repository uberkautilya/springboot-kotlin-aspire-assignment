package io.mcm.kotlinaspireassignment.security.authorization.annotations.department

import org.springframework.security.access.prepost.PreAuthorize

@Retention(AnnotationRetention.RUNTIME)
@PreAuthorize("hasAuthority('department.read')")
annotation class ReadDepartmentPermission(){

}
