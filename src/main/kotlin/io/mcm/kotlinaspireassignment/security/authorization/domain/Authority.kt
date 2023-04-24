package io.mcm.kotlinaspireassignment.security.authorization.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToMany

@Entity
class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int = 0

    val permission: String = ""

    @ManyToMany(mappedBy = "authorities")
    val roles: Set<Role> = mutableSetOf()
}