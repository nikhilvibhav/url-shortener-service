package com.example.task.url

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.time.Period

@Document
class Url(
    @Id
    var id: String,
    var url: String,
    var createdAt: Instant = Instant.now(),
    @Indexed(expireAfterSeconds = 0)
    var expiresAt: Instant = Instant.now(),
)

data class UrlDTO(
    val url: String,
    val expiresAt: Instant? = Instant.now().plus(Period.ofDays(7)),
)
