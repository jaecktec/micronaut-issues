package com.example.endpoints

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import kotlinx.coroutines.delay
import kotlinx.coroutines.slf4j.MDCContext
import kotlinx.coroutines.withContext
import org.slf4j.MDC

@Controller("/todos")
class TodosController() {

    @Get("/no-mdc-todos")
    @Produces(MediaType.TEXT_PLAIN)
    suspend fun noMdc(): String {
        delay(1)
        return MDC.get("traceId") ?: "NULL"
    }

    @Get("/with-mdc-todos")
    @Produces(MediaType.TEXT_PLAIN)
    suspend fun withMdc(): String = withContext(MDCContext()) {
        delay(1)
        MDC.get("traceId") ?: "NULL"
    }
}
