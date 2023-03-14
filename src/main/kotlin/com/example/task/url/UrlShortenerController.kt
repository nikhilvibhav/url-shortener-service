package com.example.task.url

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI

@RestController
class UrlShortenerController(val urlService: UrlService) {
    @PostMapping("/api/v1/shorten-url")
    fun shorten(@RequestBody request: UrlDTO, uriBuilder: UriComponentsBuilder): ResponseEntity<String> {
        val shortUrl = urlService.shortenUrl(request)
        val uri = uriBuilder.path("/{shortUrl}")
            .buildAndExpand(shortUrl)
            .toUri()

        return ResponseEntity.status(HttpStatus.CREATED)
            .location(uri)
            .build()
    }

    @GetMapping("/{shortUrl}")
    fun redirect(@PathVariable shortUrl: String): ResponseEntity<Unit> {
        val url = urlService.getActualUrl(shortUrl)

        return ResponseEntity.status(HttpStatus.FOUND)
            .location(URI.create(url))
            .build()
    }
}