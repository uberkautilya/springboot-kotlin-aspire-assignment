package io.mcm.kotlinaspireassignment.security.authorization.domain

import java.sql.Timestamp
import java.util.*

open class BaseEntity (
    var id: UUID = UUID.randomUUID(),
    var version: Long = 0,
    var createdDate: Timestamp? = null,
    var lastModifiedDate: Timestamp? = null
)