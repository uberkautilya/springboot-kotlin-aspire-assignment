package io.mcm.kotlinaspireassignment.security.authorization.domain

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.sql.Timestamp
import javax.persistence.*

@Entity
class AuthFailure {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    @ManyToOne
    private var username: String = ""

    private var sourceIp: String = ""

    @CreationTimestamp
    @Column(updatable = false)
    private var createdDate: Timestamp? = null

    @UpdateTimestamp
    private var lastModifiedDate: Timestamp? = null
}