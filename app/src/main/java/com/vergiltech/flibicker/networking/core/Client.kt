package com.vergiltech.flibicker.networking.core

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.xml.xml
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Ktor @Inject constructor() {
    private val _client = HttpClient(CIO) {
        expectSuccess = true
        install(ContentNegotiation) {
            xml()
        }
    }

    @Singleton
    @Provides
    fun client(): HttpClient {
        return _client
    }
}