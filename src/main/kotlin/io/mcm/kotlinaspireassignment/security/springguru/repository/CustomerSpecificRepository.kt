package io.mcm.kotlinaspireassignment.security.springguru.repository

import io.mcm.kotlinaspireassignment.security.authorization.domain.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

/**
 * Handles the requests that cater to the customer specific users
 * Here this should be accessing the Resources, not the Order. Also, this would return Order object
 *
 * This needs the SecurityEvaluationContextExtension bean
 * principal here is the authentication principal
 */
interface CustomerSpecificRepository : JpaRepository<Customer, Int>{

    @Query("select o from Order c where o.id = ?id and " +
            "(true = :#{hasAuthority('order.read') or o.customer.id = ?#{principal?.customer?.id})")
    fun getCustomerSecure(orderId: UUID): Customer
}