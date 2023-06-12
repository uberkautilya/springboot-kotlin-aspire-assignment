package io.mcm.kotlinaspireassignment.security.springguru.repository

import io.mcm.kotlinaspireassignment.security.authorization.domain.Authority
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthorityRepository: JpaRepository<Authority, Int> {
}