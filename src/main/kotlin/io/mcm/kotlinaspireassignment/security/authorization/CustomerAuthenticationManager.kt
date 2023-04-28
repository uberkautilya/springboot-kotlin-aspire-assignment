package io.mcm.kotlinaspireassignment.security.authorization

import io.mcm.kotlinaspireassignment.security.authorization.domain.User
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class CustomerAuthenticationManager {
    val log = LoggerFactory.getLogger(CustomerAuthenticationManager::class.java)

    fun customerIdMatches(authentication: Authentication, customerId: UUID): Boolean {
        val authenticatedUser = authentication.principal as User
        log.debug("Authenticated user's customer Id: {}", authenticatedUser.customer?.id)

        return authenticatedUser.customer?.id == customerId
    }
}