package io.mcm.kotlinaspireassignment.security

import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.authentication.AuthenticationEventPublisher
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.event.AuthenticationSuccessEvent
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@EnableWebSecurity
@Configuration
class SecurityConfig {
    /**
     * If a particular filter is able to handle a particular request, subsequent filters are not applied
     * Distinct user services can be given to each of the filter chains
     * @Order annotation can be used to specify the order of filters being applied
     * A filter chain has a ProviderManager, which has a parent AuthenticationManager
     */
    @Order(1)
    @Bean
    fun securityFilterChain(
        httpSecurity: HttpSecurity,
        eventPublisher: AuthenticationEventPublisher
    ): SecurityFilterChain {

        //Would no longer be required in Spring security 5.8+
        run {
            httpSecurity.getSharedObject(AuthenticationManagerBuilder::class.java)
                .authenticationEventPublisher(eventPublisher)
        }

        val authProviderManager = ProviderManager(RobotAuthenticationProvider(listOf("robotadmin")))
            .also { it.setAuthenticationEventPublisher(eventPublisher) }

        return httpSecurity
            .csrf().disable()
            .authorizeHttpRequests {
                it.antMatchers("/").permitAll()
                it.antMatchers("/error").permitAll()
                it.antMatchers("/favicon.ico").permitAll()
                it.antMatchers("/login/*").permitAll()
                it.antMatchers("/security/public").permitAll()
                it.anyRequest().authenticated()
                /*
                it.antMatchers("/security/private").authenticated()
                This way any request that is not explicitly permitted, will need to be authenticated
                */
            }
            .httpBasic(Customizer.withDefaults())
            .formLogin(Customizer.withDefaults())
            .oauth2Login(Customizer.withDefaults())
            .addFilterBefore(RobotFilter(authProviderManager), UsernamePasswordAuthenticationFilter::class.java)
            .authenticationProvider(SpecialAuthProvider())
            .build()
    }

    @Bean
    fun userDetailsService(): UserDetailsService {

        return InMemoryUserDetailsManager(
            mutableListOf(
                User.builder()
                    .username("user").password("{n oop}password")
                    .authorities("ROLE_user")
                    .build(),
                User.builder()
                    .username("admin").password("{noop}admin")
                    .authorities("ROLE_admin")
                    .build()
            )
        )
    }

    @Bean
    fun successListener(): ApplicationListener<AuthenticationSuccessEvent> {
        return ApplicationListener { event ->
            println("\nevent.authentication.javaClass.name: ${event.authentication.javaClass.name}")
            println("\nevent.authentication.name: ${event.authentication.name}")
        }
    }


}