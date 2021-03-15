package com.example

import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client

@Client("sample-client")
interface SampleDeclarativeClient {

    @Get("/endpoint")
    suspend fun getPossibleNull(): SomeResponseObject?

    data class SomeResponseObject(
        val hello: String
    )
}
