package io.mcm.kotlinaspireassignment.security.authorization.domain

import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import java.sql.Timestamp
import java.util.*
import javax.persistence.*

@Getter
@Setter
@NoArgsConstructor
@Entity
class Customer(id: UUID, version: Long, createdDate: Timestamp, modifiedDate: Timestamp) :
    BaseEntity(id, version, createdDate, modifiedDate) {

    var customerName: String = ""

    @Column(length = 36, columnDefinition = "varchar")
    var apiKey: UUID = UUID.randomUUID()

    @OneToMany(mappedBy = "customer", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var users: Set<User> = mutableSetOf()

    constructor(
        id: UUID, version: Long, createdDate: Timestamp, modifiedDate: Timestamp,
        customerName: String, apiKey: UUID
    ) : this(id, version, createdDate, modifiedDate) {
        this.customerName = customerName
        this.apiKey = apiKey
    }

}