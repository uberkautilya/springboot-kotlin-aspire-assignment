package io.mcm.kotlinaspireassignment.security.authorization

import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationEventPublisher
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
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

    /**
     * Once the password encoder bean is available, password for user shouldn't have {noop} as a prefix.
     * For other password encoding such as Ldap, {SSHA} prefix needs to be there
     */
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

    /*@Bean
    fun userDetailsService(): UserDetailsService {
    //Since the password encoder used is custom, which implements a DelegatingPasswordEncoder, it supports distinct encoders for different users
        val user = User.builder()
            .username("user")
            .password("{sha256}4f228d49d43d0876093ab31d509a4aa760e050b8bd7fef9b0bbfe621f0ecdd3fd6be40509ea9f94b")
            .authorities("ROLE_user")
            .build()
        val admin = User.builder()
            .username("admin").password("{bcrypt}\$2a\$12\$weqdMeMmuvyQdvjV5m155uJgCsMDvWisDRj3kkVaj73jrSPEsKjee")
            .authorities("ROLE_admin")
            .build()

        return InMemoryUserDetailsManager(
            mutableListOf(user, admin)
        )
    }*/

    @Bean
    fun authenticationManager(httpSecurity: HttpSecurity, eventPublisher: AuthenticationEventPublisher): AuthenticationManager {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder::class.java)
            .authenticationEventPublisher(eventPublisher).build();
    }
}