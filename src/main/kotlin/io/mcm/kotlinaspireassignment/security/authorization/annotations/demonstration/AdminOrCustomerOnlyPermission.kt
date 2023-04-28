package io.mcm.kotlinaspireassignment.security.authorization.annotations.demonstration

import org.springframework.security.access.prepost.PreAuthorize

/**
 * SpEL: Spring Expression language
 * @customerAuthenticationManager.customerIdMatches calls up the method from the spring bean, which by convention begins with a small case letter
 * authentication refers to such a spring bean, #customerId will plug in the customerId parameter of the method
 */
@PreAuthorize("hasAuthority('admin') OR " +
        "hasAuthority('customer.all') AND @customerAuthenticationManager.customerIdMatches(authentication, #customerId)")
@Retention(AnnotationRetention.RUNTIME)
annotation class AdminOrCustomerOnlyPermission()
