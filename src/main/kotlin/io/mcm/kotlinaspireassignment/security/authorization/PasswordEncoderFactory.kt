package io.mcm.kotlinaspireassignment.security.authorization

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.*

class PasswordEncoderFactory private constructor() {
    companion object {
        fun createDelegatingPasswordEncoder(): PasswordEncoder {
            val encodingId = "bcrypt10"
            val encoders = hashMapOf<String, PasswordEncoder>()
            encoders.put(encodingId, BCryptPasswordEncoder(10))
            encoders.put("bcrypt", BCryptPasswordEncoder())
            encoders.put("ldap", LdapShaPasswordEncoder())
            encoders.put("noop", NoOpPasswordEncoder.getInstance())
            encoders.put("sha256", StandardPasswordEncoder())
            return DelegatingPasswordEncoder(encodingId, encoders)
        }
    }

}
