package io.mcm.kotlinaspireassignment.security.authorization

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.*

/**
 * this is a custom delegating password encoder, adopted from the PasswordEncoderFactories class
 */
class PasswordEncoderFactory private constructor() {
    companion object {
        //DelegatingPasswordEncoders allows storage of password hashes in multiple formats
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
