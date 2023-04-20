package io.mcm.kotlinaspireassignment.security

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import java.time.Duration
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap

class RateLimitAuthenticationProvider(private val delegate: AuthenticationProvider) : AuthenticationProvider {

    private var authToInstantMap: Map<String, Instant> = ConcurrentHashMap<String, Instant>()

    override fun authenticate(authentication: Authentication?): Authentication? {
        val parentAuth = delegate.authenticate(authentication) ?: return null
        if (isLoginAllowed(parentAuth)) {
            return parentAuth
        }
        throw BadCredentialsException("Authentication not allowed at this rate")
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return delegate.supports(authentication)
    }

    /**
     * Login is allowed if the last login attempt is older than 1 minute
     */
    private fun isLoginAllowed(auth: Authentication): Boolean {
        println("\nRateLimitAuthenticationProvider.kt:: isLoginAllowed()")

        val currentInstant = Instant.now()
        val previousInstant = authToInstantMap[auth.name]
        authToInstantMap = authToInstantMap.plus(auth.name to currentInstant)

        return previousInstant == null || currentInstant.isAfter(
            previousInstant.plus(Duration.ofMinutes(1))
        )
    }
}