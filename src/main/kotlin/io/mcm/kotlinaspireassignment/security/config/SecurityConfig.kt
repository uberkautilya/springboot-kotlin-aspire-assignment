package io.mcm.kotlinaspireassignment.security.config

import io.mcm.kotlinaspireassignment.security.RateLimitAuthenticationProvider
import io.mcm.kotlinaspireassignment.security.RobotLoginConfigurer
import io.mcm.kotlinaspireassignment.security.SpecialAuthProvider
import io.mcm.kotlinaspireassignment.security.springguru.RestHeaderAuthFilter
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
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

/**
 * Under covers, Spring security uses AOP to intercept and use the AccessDecisionManager -> Method Security
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
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
        eventPublisher: AuthenticationEventPublisher,
        authenticationManager: AuthenticationManager
    ): SecurityFilterChain {
        /*
                //Would no longer be required in Spring security 5.8+
                run {
                    httpSecurity.getSharedObject(AuthenticationManagerBuilder::class.java)
                        .authenticationEventPublisher(eventPublisher).build()
                }*/
        val robotLoginConfigurer = RobotLoginConfigurer()
            .password("robotconfig").password("uberkautilya")

        //Not needed. Spring guru course follow along
        httpSecurity.addFilterBefore(
//            restHeaderAuthFilter(httpSecurity.getSharedObject(AuthenticationManager::class.java)),
            restHeaderAuthFilter(authenticationManager),
            UsernamePasswordAuthenticationFilter::class.java
        )

        return httpSecurity
            //csrf will be enabled except for the ant pattern specified - here disabled for all endpoints
            .csrf().ignoringAntMatchers("/**").and()
            .authorizeHttpRequests {
                it.antMatchers("/h2-console/**").permitAll()
                it.antMatchers("/").permitAll()
                it.antMatchers("/error").permitAll()
                it.antMatchers("/favicon.ico").permitAll()
                it.antMatchers("/login/**").permitAll()
                it.antMatchers(HttpMethod.GET, "/security/public").hasAnyRole("public")
                it.antMatchers(HttpMethod.GET, "/security/private").hasAnyRole("admin")
                //Permits the path parameter {id} with mvcMatchers()
                it.mvcMatchers(HttpMethod.GET, "/api/v1/courses/{id}").permitAll()
                it.anyRequest().authenticated()
            }
            //To allow frames of h2-console
            .headers().frameOptions().sameOrigin().and()
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
                //To customize the login page
                it.loginProcessingUrl("/login")
                it.loginPage("/")
                it.successForwardUrl("/")
                it.defaultSuccessUrl("/")
                it.failureUrl("/?error")
            }
            //To customize the logout page
            .logout {
                it.logoutRequestMatcher(AntPathRequestMatcher("/logout", "GET"))
                it.logoutSuccessUrl("/?logout")
                it.permitAll()
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