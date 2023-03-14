package com.example.task.url

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.ZonedDateTime

@Document
class Url(
    @Id
    var id: String,
    var url: String,
    var createdAt: ZonedDateTime = ZonedDateTime.now(),
    var expiresAt: ZonedDateTime = ZonedDateTime.now(),
)

data class UrlDTO(
    val url: String,
    val expiresAt: ZonedDateTime? = ZonedDateTime.now().plusDays(7),
)
