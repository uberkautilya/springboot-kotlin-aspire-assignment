package io.mcm.kotlinaspireassignment.security

import io.mcm.kotlinaspireassignment.security.authorization.annotations.demonstration.AdminOrCustomerOnlyPermission
import io.mcm.kotlinaspireassignment.security.authorization.annotations.demonstration.ReadPrivatePermission
import io.mcm.kotlinaspireassignment.security.authorization.annotations.demonstration.ReadPublicPermission
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/security")
class SecurityDemonstratorController {

    @GetMapping("/public")
    @ReadPublicPermission
    fun public(): ResponseEntity<String> {
        return ResponseEntity.ok("Unsecured 😒")
    }

    @GetMapping("/private")
    @ReadPrivatePermission
    fun private(auth: Authentication): ResponseEntity<String> {
        return ResponseEntity.ok("Secured 👍. Welcome " + getName())
    }

    /*
    Didn't work: TODO
    @PreAuthorize("isAuthenticated() and  hasRole('admin')")
    */
    @ReadPrivatePermission
    @GetMapping("/admin")
    fun admin(auth: Authentication): ResponseEntity<String> {
        return ResponseEntity.ok("Secured 👍. Welcome " + getName())
    }

    @GetMapping("/customer")
    @AdminOrCustomerOnlyPermission
    fun customer(auth: Authentication): ResponseEntity<String> {
        return ResponseEntity.ok("Secured for customer 'CUSTOMER' 👍. Welcome " + getName())
    }

    private fun getName(): String {
        val auth = SecurityContextHolder.getContext().authentication
        println("""SecurityDemonstratorController.kt:: getName()
            |auth.authorities: ${auth.authorities}""".trimMargin())

        val authentication = auth as? OAuth2AuthenticationToken ?: return auth.name
        val principal = authentication.principal as? OidcUser ?: return auth.name
        return principal.email
    }
}