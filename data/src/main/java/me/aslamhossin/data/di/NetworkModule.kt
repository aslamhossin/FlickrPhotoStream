package me.aslamhossin.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import me.aslamhossin.core.errorhandling.ApiErrorInterceptor
import me.aslamhossin.data.BuildConfig
import me.aslamhossin.data.converter.DateTimeSerializer
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Provides network-related dependencies.
 */
@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    /**
     * Provides a JSON serializer configured to ignore unknown keys and use the DateTimeSerializer.
     *
     * @return Configured Json instance.
     */
    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            serializersModule = SerializersModule {
                contextual(DateTimeSerializer)
            }
        }
    }

    /**
     * Provides an OkHttpClient with custom timeouts and logging.
     *
     * @param json The Json instance for the API error interceptor.
     * @return Configured OkHttpClient instance.
     */
    @Provides
    @Singleton
    fun provideHttpClient(json: Json): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
            .connectTimeout(45, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            clientBuilder.addInterceptor(logging)
        }
        clientBuilder.addInterceptor(ApiErrorInterceptor(json))
        return clientBuilder.build()
    }

    /**
     * Provides a Retrofit instance configured with the OkHttpClient and Json converter.
     *
     * @param client The OkHttpClient instance.
     * @param json The Json instance for serialization.
     * @return Configured Retrofit instance.
     */
    @Provides
    @Singleton
    @ExperimentalSerializationApi
    fun provideRetrofit(client: OkHttpClient, json: Json): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    /**
     * Provides an implementation of the FlickerApi interface.
     *
     * @param retrofit The Retrofit instance.
     * @return Implementation of FlickerApi.
     */
    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): me.aslamhossin.data.api.FlickerApi {
        return retrofit.create(me.aslamhossin.data.api.FlickerApi::class.java)
    }
}
