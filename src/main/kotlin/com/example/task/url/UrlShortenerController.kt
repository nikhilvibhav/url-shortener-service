package com.example.task.url

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UrlShortenerController(val urlService: UrlService) {
    @PostMapping("/shorten-url")
    fun shorten(@RequestBody request: UrlDTO): String {
        return urlService.shortenUrl(request)
    }

    @GetMapping("/{shortUrl}")
    fun redirect(@PathVariable shortUrl: String) {
        return urlService.getActualUrl(shortUrl)
    }
}