package io.mcm.kotlinaspireassignment.security

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/security")
class SecurityDemonstratorController {

    @GetMapping("/public")
    fun public(): ResponseEntity<String> {
        return ResponseEntity.ok("Unsecured 😒")
    }
    @GetMapping("/private")
    fun private(): ResponseEntity<String> {
        return ResponseEntity.ok("Secured 👍")
    }
}