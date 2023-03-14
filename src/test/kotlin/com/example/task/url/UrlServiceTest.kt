package com.example.task.url

import io.mockk.clearAllMocks
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension

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

        // when
        val result = assertDoesNotThrow { urlService.shortenUrl(request) }

        // then
        assertNotNull(result)
    }

    @Test
    fun `given shortUrl, when getActualUrl is invoked, then redirect to the original url`() {
        // given
        val shortUrl = "https://testurl.com"

        // when
        val result = assertDoesNotThrow { urlService.getActualUrl(shortUrl) }

        // then
        assertNotNull(result)
    }
}