package io.mcm.kotlinaspireassignment.security

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.AuthorityUtils

/**
 * Multiple authentication providers can work together as in a filter chain
 * In case one is not able to authenticate, the next one attempts to provide authentication
 */
class SpecialAuthProvider: AuthenticationProvider{
    override fun authenticate(authentication: Authentication): Authentication? {
        val userName = authentication.name
        if ("special".equals(userName, true)) {
            println("Special user sign in")
            return UsernamePasswordAuthenticationToken.authenticated(
                "special",
                null,
                AuthorityUtils.createAuthorityList("ROLE_admin")
            )
        }
        if ("denied".equals(userName, true)) {
            println("Forbidden user sign in attempt")
            throw InsufficientAuthenticationException("Forbidden Username")
        }
        return null
    }

    override fun supports(authentication: Class<*>): Boolean {
        return UsernamePasswordAuthenticationToken::class.java.isAssignableFrom(authentication)
    }
}