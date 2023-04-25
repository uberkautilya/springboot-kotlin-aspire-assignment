package io.mcm.kotlinaspireassignment.security.config

import io.mcm.kotlinaspireassignment.security.RateLimitAuthenticationProvider
import io.mcm.kotlinaspireassignment.security.springguru.RestHeaderAuthFilter
import io.mcm.kotlinaspireassignment.security.RobotLoginConfigurer
import io.mcm.kotlinaspireassignment.security.SpecialAuthProvider
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationEventPublisher
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.event.AuthenticationSuccessEvent
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.ObjectPostProcessor
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private final val persistentTokenRepository: PersistentTokenRepository,
    private final val userDetailsService: UserDetailsService
) {

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
        val robotLoginConfigurer = RobotLoginConfigurer()
            .password("robotconfig").password("uberkautilya")

        //Not needed. Spring guru course follow along
        httpSecurity.addFilterBefore(
            restHeaderAuthFilter(httpSecurity.getSharedObject(AuthenticationManager::class.java)),
            UsernamePasswordAuthenticationFilter::class.java
        )

        return httpSecurity
            .csrf().disable()
            .authorizeHttpRequests {
                it.antMatchers("/").permitAll()
                it.antMatchers("/error").permitAll()
                it.antMatchers("/favicon.ico").permitAll()
                it.antMatchers("/login/**").permitAll()
                it.antMatchers(HttpMethod.GET,"/security/public").permitAll()
                //Permits the path parameter {id} with mvcMatchers()
                it.mvcMatchers(HttpMethod.GET, "/api/v1/courses/{id}").permitAll()
                it.anyRequest().authenticated()
            }
            .httpBasic(Customizer.withDefaults())
            .formLogin {
                println("In the config for formLogin")
                it.withObjectPostProcessor(
                    object : ObjectPostProcessor<AuthenticationProvider> {

                        override fun <O : AuthenticationProvider> postProcess(authProvider: O): O {
                            println("In object post processor")
                            return RateLimitAuthenticationProvider(authProvider) as O
                        }
                    }
                )
            }
            .rememberMe {
                it.tokenRepository(persistentTokenRepository)
                it.userDetailsService(userDetailsService)
            }
            .oauth2Login(Customizer.withDefaults())
            .apply(robotLoginConfigurer).and()
            .authenticationProvider(
                RateLimitAuthenticationProvider(SpecialAuthProvider())
            )
            .build()
    }

    @Bean
    fun successListener(): ApplicationListener<AuthenticationSuccessEvent> {
        return ApplicationListener { event ->
            println("\nevent.authentication.javaClass.name: ${event.authentication.javaClass.name}")
            println("\nevent.authentication.name: ${event.authentication.name}")
        }
    }

    //Not required. Spring guru course follow along
    fun restHeaderAuthFilter(authenticationManager: AuthenticationManager): RestHeaderAuthFilter {
        val filter = RestHeaderAuthFilter(AntPathRequestMatcher("/api/**"))
        return filter.apply { this.setAuthenticationManager(authenticationManager) }
    }


}