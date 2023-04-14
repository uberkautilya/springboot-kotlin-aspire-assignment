package io.mcm.kotlinaspireassignment.security

import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

const val PASSWORD_HEADER = "robot-password"

//When an authentication manager is part of a filter, it is used to transform an authentication object into an authenticated object
class RobotFilter(private val authenticationManager: AuthenticationManager) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        println("Robot Filter engaged")
        //0. Should we execute the filter
        if (!Collections.list(request.headerNames).contains(PASSWORD_HEADER)) {
            //Continue to the rest of the filters as the password header is not present
            filterChain.doFilter(request, response)
            return
        }
        //1. Authentication Decision
        val password = request.getHeader(PASSWORD_HEADER)
        val authRequest = RobotAuthentication.unauthenticated(password)

        try {
            //authenticationManager needs an authentication provider to transform a unauthorized authentication object into an authenticated one
            val authenticated = authenticationManager.authenticate(authRequest)
            val newContext = SecurityContextHolder.createEmptyContext()
            newContext.authentication = authenticated
            SecurityContextHolder.setContext(newContext)
            filterChain.doFilter(request, response)
            return
        } catch (e: AuthenticationException) {
            println("AuthenticationException block")
            response.status = HttpStatus.FORBIDDEN.value()
            response.characterEncoding = "utf-8"
            response.setHeader("Content-type", "text/plain;charset=utf-8")
            response.writer.use { it.println("${e.message}. Access is Forbidden!") }
            return
        }

        /*
        if (!"robot".equals(password, true)) {
            response.status = HttpStatus.FORBIDDEN.value()
            response.characterEncoding = "utf-8"
            response.setHeader("Content-type", "text/plain;charset=utf-8")
            response.writer.use { it.println("Not Mr. Robot. Access is Forbidden!") }
        } else {
            val newContext = SecurityContextHolder.createEmptyContext()
            newContext.authentication = RobotAuthentication()
            SecurityContextHolder.setContext(newContext)
            filterChain.doFilter(request, response)
            return
        }
         */
    }

}