package io.mcm.kotlinaspireassignment.security.authorization.domain

import lombok.*
import org.springframework.security.core.CredentialsContainer
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collectors
import javax.persistence.*

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
class User : UserDetails, CredentialsContainer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0
    private var username: String = ""
    private var password: String? = null

    @Builder.Default
    private val accountNonExpired = true
    @Builder.Default
    private val accountNonLocked = true
    @Builder.Default
    private val credentialsNonExpired = true
    @Builder.Default
    private val enabled = true

    @Singular
    @ManyToMany(cascade = [CascadeType.MERGE], fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
        joinColumns = [JoinColumn(name = "USER_ID", referencedColumnName = "ID")],
        inverseJoinColumns = [JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")])
    var roles: Set<Role> = mutableSetOf()

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
    fun setPassword(password: String) {
        this.password = password
    }

    override fun getUsername(): String {
        return username
    }
    fun setUsername(username: String) {
        this.username = username
    }


    override fun isAccountNonExpired(): Boolean {
        return accountNonExpired
    }

    override fun isAccountNonLocked(): Boolean {
        return accountNonLocked
    }

    override fun isCredentialsNonExpired(): Boolean {
        return credentialsNonExpired
    }

    override fun isEnabled(): Boolean {
        return enabled
    }

    override fun eraseCredentials() {
        password = null
    }

}