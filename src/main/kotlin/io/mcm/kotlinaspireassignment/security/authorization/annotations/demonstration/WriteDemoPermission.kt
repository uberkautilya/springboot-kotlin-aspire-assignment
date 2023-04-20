package io.mcm.kotlinaspireassignment.security.authorization.annotations.demonstration

import org.springframework.security.access.prepost.PreAuthorize

@Retention(AnnotationRetention.RUNTIME)
@PreAuthorize("hasAuthority('demo.update')")
annotation class WriteDemoPermission(){

}
