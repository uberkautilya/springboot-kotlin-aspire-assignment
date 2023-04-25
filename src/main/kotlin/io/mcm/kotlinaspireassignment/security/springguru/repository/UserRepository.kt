package io.mcm.kotlinaspireassignment.security.springguru.repository

import io.mcm.kotlinaspireassignment.security.authorization.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Int> {
    fun findByUsername(username: String): Optional<User>
}