package com.github.kiolk.overlayview.di.modules

import com.github.kiolk.overlayview.data.datasources.OverlayService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

const val BASE_URL = "BASE_URL"
const val LOGGING_INTERCEPTOR = "LOGGING_INTERCEPTOR"
const val CONTENT_TYPE = "application/json"

val networkModule = module {
    single<String>(named(BASE_URL)) { "https://appostropheanalytics.herokuapp.com/" }
    single {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
    }
    single<Interceptor>(named(LOGGING_INTERCEPTOR)) {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
    single {
        OkHttpClient.Builder()
            .addInterceptor(get<Interceptor>(named(LOGGING_INTERCEPTOR)))
            .build()
    }
    single {
        val contentType = CONTENT_TYPE.toMediaType()
        Retrofit.Builder()
            .baseUrl(get<String>(named(BASE_URL)))
            .client(get())
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
    }

    single { get<Retrofit>().create(OverlayService::class.java) }
}