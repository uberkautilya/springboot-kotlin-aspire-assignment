package io.mcm.kotlinaspireassignment.security.springguru

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.RequestMatcher
import org.thymeleaf.util.StringUtils
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Not required. Follow along of spring guru course
 */
class RestHeaderAuthFilter(requestMatcher: RequestMatcher) : AbstractAuthenticationProcessingFilter(requestMatcher) {
    private var log = LoggerFactory.getLogger(RestHeaderAuthFilter::class.java)

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain) {

        try {
            val authenticationResult = attemptAuthentication(
                request as HttpServletRequest, response as HttpServletResponse
            ) ?: // return immediately as subclass has indicated that it hasn't completed
            return
            successfulAuthentication(request, response, chain, authenticationResult)
        } catch (e: AuthenticationException) {
            log.error("Authentication failed for ${(request as HttpServletRequest).getHeader("Api-Key")}")
            unsuccessfulAuthentication(request as HttpServletRequest, response as HttpServletResponse, e)
        }
    }

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication? {
        val apiKey = getApiKey(request) ?: ""
        val apiSecret = getApiSecret(request) ?: ""
        log.info("apiSecret: $apiSecret")
        //If there are no credentials provided, skip to the next filter by returning null
        if (StringUtils.isEmpty(apiKey)) return null

        val token = UsernamePasswordAuthenticationToken(apiKey, apiSecret)
        //Filters have associated authenticationManagers?
        return this.authenticationManager.authenticate(token)
    }

    private fun getApiKey(request: HttpServletRequest) = request.getHeader("Api-Key")
    private fun getApiSecret(request: HttpServletRequest) = request.getHeader("Api-Secret")

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(
        request: HttpServletRequest?, response: HttpServletResponse?, chain: FilterChain?,
        authResult: Authentication?
    ) {
        log.debug("Authentication successful. Updating security context ✌️")
        SecurityContextHolder.getContext().authentication = authResult
    }

    @Throws(IOException::class, ServletException::class)
    override fun unsuccessfulAuthentication(
        request: HttpServletRequest, response: HttpServletResponse,
        failed: AuthenticationException?
    ) {
        log.error("Authentication failed for ${request.getHeader("Api-Key")}⛔")
        SecurityContextHolder.clearContext()

        response.sendError(
            HttpStatus.UNAUTHORIZED.value(),
            HttpStatus.UNAUTHORIZED.reasonPhrase
        )
    }
}