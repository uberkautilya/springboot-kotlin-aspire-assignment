package io.mcm.kotlinaspireassignment.security

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException

class RobotAuthenticationProvider(
    private val supportedPasswords: List<String> = listOf("robot", "automated")
) : AuthenticationProvider {
    override fun authenticate(authentication: Authentication?): Authentication {
        val authRequest = authentication as RobotAuthentication
        val password = authRequest.getPassword()
        if (!supportedPasswords.contains(password)) {
            throw BadCredentialsException("You are not Mr. Robot")
        }
        return RobotAuthentication.authenticated()
    }

    override fun supports(authentication: Class<*>): Boolean {
        return RobotAuthentication::class.java.isAssignableFrom(authentication)
    }

}