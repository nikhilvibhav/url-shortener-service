package com.example.task.url

import org.springframework.stereotype.Service

@Service
class UrlService(val urlRepository: UrlRepository) {
    fun shortenUrl(request: UrlDTO): String {
        TODO("Not yet implemented")
    }

    fun getActualUrl(shortUrl: String) {
        TODO("Not yet implemented")
    }
}