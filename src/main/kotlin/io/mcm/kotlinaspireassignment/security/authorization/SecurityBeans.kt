package io.mcm.kotlinaspireassignment.security.authorization

import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationEventPublisher
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository
import javax.sql.DataSource

@Configuration
class SecurityBeans {
    /*
    //Needed for use with Spring DataJPA SPeL
    @Bean
    fun securityEvaluationContextExtension(): SecurityEvaluationContextExtension{
        return SecurityEvaluationContextExtension()
    }*/

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactory.createDelegatingPasswordEncoder()
    }

    @Bean
    fun authenticationEventPublisher(applicationEventPublisher: ApplicationEventPublisher): AuthenticationEventPublisher {
        return DefaultAuthenticationEventPublisher(applicationEventPublisher)
    }

    @Bean
    fun persistentTokenRepository(dataSource: DataSource): PersistentTokenRepository {
        val tokenRepository = JdbcTokenRepositoryImpl()
        tokenRepository.setDataSource(dataSource)
        return tokenRepository
    }

    @Bean
    fun userDetailsService(): UserDetailsService {

        return InMemoryUserDetailsManager(
            mutableListOf(
                User.builder()
                    .username("user").password("{noop}password")
                    .authorities("ROLE_user")
                    .build(),
                User.builder()
                    .username("admin").password("{noop}admin")
                    .authorities("ROLE_admin")
                    .build()
            )
        )
    }
}