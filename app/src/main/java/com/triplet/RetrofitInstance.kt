package com.triplet

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import retrofit2.Retrofit

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType

object RetrofitInstance {
    private const val BASE_URL = "https://www.anapioficeandfire.com/api/"

    private val json = Json { ignoreUnknownKeys = true }

    @OptIn(ExperimentalSerializationApi::class)
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(ApiService::class.java)
    }
}
