package com.prmto.core_testing.util

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object MockWebServerUtil {
    inline fun <reified Service> createApiService(
        mockWebServer: MockWebServer
    ): Service {
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(Service::class.java)
    }

}

fun MockWebServer.enqueueMockResponse(
    fileName: String,
    mockResponse: MockResponse = MockResponse()
) {
    javaClass.classLoader?.let {
        val inputStream = it.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        enqueue(mockResponse)
    }
}