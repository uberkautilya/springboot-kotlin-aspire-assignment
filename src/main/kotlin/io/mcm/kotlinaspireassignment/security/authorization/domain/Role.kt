package io.mcm.kotlinaspireassignment.security.authorization.domain

import javax.persistence.*

class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int = 0

    val name: String = ""

    @ManyToMany(mappedBy = "roles")
    val users: Set<User> = mutableSetOf()

    @ManyToMany(cascade = [CascadeType.MERGE, CascadeType.PERSIST], fetch = FetchType.EAGER)
    @JoinTable(name= "role_authority",
        joinColumns = [JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")],
        inverseJoinColumns = [JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID")])
    val authorities: Set<Authority> = mutableSetOf()
}