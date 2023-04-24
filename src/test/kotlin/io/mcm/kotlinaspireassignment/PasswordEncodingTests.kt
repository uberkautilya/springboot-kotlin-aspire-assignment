package io.mcm.kotlinaspireassignment

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.LdapShaPasswordEncoder
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.StandardPasswordEncoder
import org.springframework.util.DigestUtils

class PasswordEncodingTests {
    private val PASSWORD = "password"

    @Test
    fun hashingExample() {
        println("{DigestUtils.md5DigestAsHex(PASSWORD.encodeToByteArray())}: ${DigestUtils.md5DigestAsHex(PASSWORD.encodeToByteArray())}")
        println("{DigestUtils.md5DigestAsHex(PASSWORD.encodeToByteArray())}: ${DigestUtils.md5DigestAsHex(PASSWORD.encodeToByteArray())}")

        val salted = PASSWORD + "A_SALT_VALUE"
        println("{DigestUtils.md5DigestAsHex(salted.encodeToByteArray())}: ${DigestUtils.md5DigestAsHex(salted.encodeToByteArray())}")
    }

    @Test
    fun testNoOp() {
        val noOP = NoOpPasswordEncoder.getInstance()
        println("{noOP.encode(PASSWORD)}: ${noOP.encode(PASSWORD)}")
    }

    @Test
    fun testLdapPasswordEncoder() {
        //Uses a random salt. Hence each encoding produces a distinct value
        val ldap = LdapShaPasswordEncoder()
        println("{ldap.encode(PASSWORD)}: ${ldap.encode(PASSWORD)}")
        println("{ldap.encode(PASSWORD)}: ${ldap.encode(PASSWORD)}")
        println("{ldap.encode(PASSWORD)}: ${ldap.encode(PASSWORD)}")

        assertTrue(ldap.matches(PASSWORD, ldap.encode(PASSWORD)))
        println("{ldap.matches(PASSWORD, ldap.encode(PASSWORD))}: ${ldap.matches(PASSWORD, ldap.encode(PASSWORD))}")
    }

    @Test
    fun testSha256() {
        //Biggest criticism is that it is quite fast. Brute force could be used to break it
        val sha256 = StandardPasswordEncoder()
        println("{sha256.encode(PASSWORD)}: ${sha256.encode(PASSWORD)}")
        println("{sha256.encode(PASSWORD)}: ${sha256.encode(PASSWORD)}")

        println(
            "sha256.matches(PASSWORD, sha256.encode(PASSWORD))}: ${
                sha256.matches(
                    PASSWORD,
                    sha256.encode(PASSWORD)
                )
            }"
        )
    }

    @Test
    fun testBCrypt() {
        val bcrypt = BCryptPasswordEncoder(12)
        println("{bcrypt.encode(PASSWORD)}: ${bcrypt.encode(PASSWORD)}")
        println("{bcrypt.encode(PASSWORD)}: ${bcrypt.encode(PASSWORD)}")

        println(
            "{bcrypt.matches(PASSWORD, bcrypt.encode(PASSWORD))}: ${
                bcrypt.matches(
                    PASSWORD,
                    bcrypt.encode(PASSWORD)
                )
            }"
        )
    }
}