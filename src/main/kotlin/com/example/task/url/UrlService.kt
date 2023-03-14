package com.example.task.url

import com.google.common.hash.Hashing
import com.mongodb.DuplicateKeyException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.Period

@Service
class UrlService(val urlRepository: UrlRepository) {
    private val logger = logger()

    companion object {
        private val hashFunction = Hashing.murmur3_32_fixed()
    }

    fun shortenUrl(request: UrlDTO): String {
        val url = Url(
            id = hashFunction.hashString(request.url, Charsets.UTF_8).toString(),
            url = request.url,
            expiresAt = request.expiresAt ?: Instant.now().plus(Period.ofDays(7))
        )
        val savedUrl: Url

        try {
            savedUrl = urlRepository.insert(url)
        } catch (e: DuplicateKeyException) {
            logger.debug("ID with value {} already exists, updating existing document...", url.id)
            val optionalExistingUrl = urlRepository.findById(url.id)
            var updatedUrl: Url? = null
            if (optionalExistingUrl.isPresent) {
                optionalExistingUrl.get().also { it.expiresAt = Instant.now().plus(Period.ofDays(7)) }
                updatedUrl = urlRepository.save(optionalExistingUrl.get())
            }
            return updatedUrl!!.id
        }

        return savedUrl.id
    }

    fun getActualUrl(shortUrl: String): String {
        val optionalResult = urlRepository.findById(shortUrl)
        return if (optionalResult.isPresent) {
            optionalResult.get().url
        } else {
            logger.error("Unable to find a corresponding long url for the given id: {}", shortUrl)
            throw UrlNotFoundException("Unable to find a corresponding long url for the given shortUrl: $shortUrl")
        }
    }
}

inline fun <reified T> T.logger(): Logger =
    LoggerFactory.getLogger(T::class.java)
