package io.mcm.kotlinaspireassignment.security

import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import java.util.*

class RobotAuthentication private constructor(
    private val password: String?,
    private val authorities: MutableList<GrantedAuthority>? = AuthorityUtils.createAuthorityList("ROLE_Robot"),
    private val isAuthenticated: Boolean =  password == null
) : Authentication {

    companion object{
        fun unauthenticated(password: String?): RobotAuthentication {
            return RobotAuthentication(password, Collections.emptyList())
        }

        fun authenticated(): RobotAuthentication {
            return RobotAuthentication(null, AuthorityUtils.createAuthorityList("ROLE_robot"))
        }
    }
    override fun getName(): String {
        return "Mr. Robot"
    }

    override fun getAuthorities(): MutableList<GrantedAuthority>? {
        return authorities
    }

    override fun getCredentials(): Any? {
        return null
    }

    override fun getDetails(): Any? {
        return null
    }

    override fun getPrincipal(): Any {
        return name
    }

    override fun isAuthenticated(): Boolean {
        return isAuthenticated
    }

    override fun setAuthenticated(isAuthenticated: Boolean) {
        throw IllegalArgumentException("Immutable. Don't alter")
    }

    fun getPassword(): String? {
        return password;
    }
}
