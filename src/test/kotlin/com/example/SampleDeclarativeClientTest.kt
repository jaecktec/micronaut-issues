package com.example

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.configureFor
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.notFound
import com.github.tomakehurst.wiremock.client.WireMock.okJson
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import javax.inject.Inject

private const val WIREMOCK_PORT = 1080

@MicronautTest
class SampleDeclarativeClientTest {
    companion object {
        private val wireMockServer: WireMockServer by lazy {
            WireMockServer(WireMockConfiguration.wireMockConfig().port(WIREMOCK_PORT))
        }

        @BeforeAll
        @JvmStatic
        fun setUpAll() {
            wireMockServer.start()
            configureFor("localhost", WIREMOCK_PORT)
        }

        @AfterAll
        fun tearDown() {
            wireMockServer.stop()
        }
    }

    @Inject
    private lateinit var subject: SampleDeclarativeClient

    @Test
    fun `with 200 response - returns hello world object`(): Unit = runBlocking {
        stubFor(get(urlPathEqualTo("/endpoint"))
            .willReturn(okJson("""{"hello": "world"}""")))

        val actual = subject.getPossibleNull()
        Assertions.assertNotNull(actual) { "response must not be null" }
        Assertions.assertEquals("world", actual!!.hello)
    }

    @Test
    fun `with 404 response - returns null`(): Unit = runBlocking {
        stubFor(get(urlPathEqualTo("/endpoint"))
            .willReturn(notFound()))

        val actual = subject.getPossibleNull()
        Assertions.assertNull(actual) { "response must not null" }
    }
}
