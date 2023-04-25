package io.mcm.kotlinaspireassignment.security.springguru

import io.mcm.kotlinaspireassignment.security.springguru.repository.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@RequiredArgsConstructor
@Service
class JpaUserDetailsService : UserDetailsService {
    lateinit var userRepository: UserRepository

    @Transactional
    override fun loadUserByUsername(username: String): UserDetails {
        println("\nJpaUserDetailsService.kt:: loadUserByUsername($username)")
        return userRepository.findByUsername(username).orElseThrow { UsernameNotFoundException("$username not found") }
    }
}