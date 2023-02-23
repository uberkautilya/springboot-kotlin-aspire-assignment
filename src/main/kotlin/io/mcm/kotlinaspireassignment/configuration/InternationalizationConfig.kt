package io.mcm.kotlinaspireassignment.configuration

import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import org.springframework.web.servlet.i18n.SessionLocaleResolver
import java.util.*


@Configuration
class InternationalizationConfig : WebMvcConfigurer {

    @Bean
    fun localeResolver(): SessionLocaleResolver {
        val sessionLocaleResolver = SessionLocaleResolver()
        sessionLocaleResolver.setDefaultLocale(Locale.US)
        sessionLocaleResolver.setLocaleAttributeName("session.current.locale")
        sessionLocaleResolver.setTimeZoneAttributeName("session.current.timezone")
        return sessionLocaleResolver
    }

    /**
     * LocaleChangeInterceptor bean:
     * LocalizationConfig that will switch to a new locale based on the value of the language parameter appended to an HTTP request URL.
     * Reference: https://reflectoring.io/spring-boot-internationalization/
     */
    @Bean
    fun localeChangeInterceptor(): LocaleChangeInterceptor? {
        val localeChangeInterceptor = LocaleChangeInterceptor()
        localeChangeInterceptor.paramName = "language"
        return localeChangeInterceptor
    }

    /**
     * Adds localeChangeInterceptor() bean to the InterceptorRegistry
     */
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(localeChangeInterceptor()!!)
    }

    /**
     * Defines the base name of our resource bundle as language/messages
     */
    @Bean("messageSource")
    fun messageSource(): MessageSource? {
        val messageSource = ResourceBundleMessageSource()
        messageSource.setBasenames("language/messages")
        messageSource.setDefaultEncoding("UTF-8")
        return messageSource
    }
}