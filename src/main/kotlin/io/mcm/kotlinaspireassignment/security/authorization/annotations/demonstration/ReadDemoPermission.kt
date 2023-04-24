package io.mcm.kotlinaspireassignment.security.authorization.annotations.demonstration

import org.springframework.security.access.prepost.PreAuthorize

@Retention(AnnotationRetention.RUNTIME)
@PreAuthorize("hasAuthority('demo.read')")
annotation class ReadDemoPermission(){

}