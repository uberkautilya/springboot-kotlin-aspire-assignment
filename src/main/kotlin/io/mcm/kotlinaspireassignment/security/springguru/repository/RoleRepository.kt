package io.mcm.kotlinaspireassignment.security.springguru.repository

import io.mcm.kotlinaspireassignment.security.authorization.domain.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository: JpaRepository<Role, Int> {
}