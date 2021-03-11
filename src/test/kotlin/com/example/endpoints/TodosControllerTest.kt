package com.example.endpoints

import io.micronaut.http.HttpRequest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest
class TodosControllerTest{

    @Inject
    @field:Client("/")
    lateinit var client : RxHttpClient

    @Test
    fun `withMdcContext - should return trace header`() {
        val request = HttpRequest.GET<String>("/todos/with-mdc-todos").apply {
            headers.add("X-B3-TraceId", "some-trace-id")
        }
        val response = client.toBlocking().retrieve(request)
        assertEquals("some-trace-id", response)
    }

    @Test
    fun `withoutMdcContext - should return trace header`() {
        val request = HttpRequest.GET<String>("/todos/no-mdc-todos").apply {
            headers.add("X-B3-TraceId", "some-trace-id")
        }
        val response = client.toBlocking().retrieve(request)
        assertEquals("some-trace-id", response)
    }
}
