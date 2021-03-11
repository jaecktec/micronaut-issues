package com.example.logging

import io.micronaut.http.HttpRequest
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Filter
import io.micronaut.http.filter.HttpServerFilter
import io.micronaut.http.filter.ServerFilterChain
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.publish
import kotlinx.coroutines.slf4j.MDCContext
import org.reactivestreams.Publisher
import org.slf4j.MDC
import javax.inject.Singleton

@Filter("/**")
class TraceIdServerFilter: HttpServerFilter {
    @ExperimentalCoroutinesApi
    override fun doFilter(request: HttpRequest<*>, chain: ServerFilterChain): Publisher<MutableHttpResponse<*>> {
        request.headers["X-B3-TraceId"]?.run {
            MDC.put("traceId", this)
        }
        return chain.proceed(request)
    }
}
