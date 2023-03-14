package com.example.task.url

import com.google.common.hash.Hashing
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.Instant
import java.time.Period
import java.util.Optional

@ExtendWith(SpringExtension::class)
class UrlServiceTest {
    private val urlRepository: UrlRepository = mockk()
    private val urlService = UrlService(urlRepository)

    @BeforeEach
    fun setUp() {
        clearAllMocks()
    }

    @Test
    fun `given request, when shortenUrl is invoked, then return a shortened url`() {
        // given
        val request = UrlDTO(url = "https://testurl.com")
        val savedUrl = savedUrl(request)

        every { urlRepository.save(any()) } returns savedUrl

        // when
        val result = assertDoesNotThrow { urlService.shortenUrl(request) }

        // then
        assertNotNull(result)
        assertEquals(savedUrl.id, result)
    }

    @Test
    fun `given shortUrl, when getActualUrl is invoked, then redirect to the original url`() {
        // given
        val shortUrl = "abc1234"
        val savedUrl = Url(
            id = shortUrl,
            url = "https://testurl.com",
            expiresAt = Instant.now().plus(Period.ofDays(7))
        )

        every { urlRepository.findById(shortUrl) } returns Optional.of(savedUrl)

        // when
        val result = assertDoesNotThrow { urlService.getActualUrl(shortUrl) }

        // then
        assertNotNull(result)
    }

    companion object {
        private fun savedUrl(request: UrlDTO) = Url(
            id = Hashing.murmur3_32_fixed().hashString(request.url, Charsets.UTF_8).toString(),
            url = request.url,
            expiresAt = Instant.now().plus(Period.ofDays(7))
        )
    }
}