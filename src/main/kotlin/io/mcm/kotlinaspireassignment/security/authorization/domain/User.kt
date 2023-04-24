package io.mcm.kotlinaspireassignment.security.authorization.domain

import org.springframework.security.core.CredentialsContainer
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collectors
import javax.persistence.*


@Entity
class User : UserDetails, CredentialsContainer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0
    private val username: String = ""
    private var password: String? = null

    @ManyToMany(cascade = [CascadeType.MERGE], fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
        joinColumns = [JoinColumn(name = "USER_ID", referencedColumnName = "ID")],
        inverseJoinColumns = [JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")])
    val roles: Set<Role> = mutableSetOf()

    @Transient
    override fun getAuthorities(): Set<GrantedAuthority> {
        return roles.stream().map { it.authorities }
            .flatMap { it.stream() }
            .map { SimpleGrantedAuthority(it.permission) }
            .collect(Collectors.toSet())
    }

    override fun getPassword(): String? {
        return password
    }

    override fun getUsername(): String {
        return username
    }


    override fun isAccountNonExpired(): Boolean {
        return isAccountNonExpired
    }

    override fun isAccountNonLocked(): Boolean {
        return isAccountNonLocked
    }

    override fun isCredentialsNonExpired(): Boolean {
        return isCredentialsNonExpired
    }

    override fun isEnabled(): Boolean {
        return isEnabled
    }

    override fun eraseCredentials() {
        password = null
    }

}