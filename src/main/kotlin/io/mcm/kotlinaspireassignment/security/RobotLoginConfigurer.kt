package io.mcm.kotlinaspireassignment.security

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor

/**
 * Take all configurers in a filter chain and init them in a loop. Then a specific step. Then all the configurers in another loop
 */
class RobotLoginConfigurer : AbstractHttpConfigurer<RobotLoginConfigurer, HttpSecurity>() {
    private val passwords: List<String> = mutableListOf()

    override fun init(http: HttpSecurity) {
        //Contains all the authentication providers
        http.authenticationProvider(RobotAuthenticationProvider(passwords))
    }

    override fun configure(http: HttpSecurity) {
        //Contain all the filters
        //Each filter chain may have its own authentication manager, so spring security has its own alternative to the spring context
        val authManager = http.getSharedObject(AuthenticationManager::class.java)
        http.addFilterBefore(RobotFilter(authManager), FilterSecurityInterceptor::class.java)
    }

    fun password(password: String): RobotLoginConfigurer {
        this.passwords.plus(password)
        return this
    }
}
