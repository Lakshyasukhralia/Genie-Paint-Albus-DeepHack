package com.sketchart.sketchart.core.di

import android.app.Application
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.sketchart.sketchart.core.persistence.datastore.preference.AppPreferenceRepository
import com.sketchart.sketchart.core.persistence.datastore.preference.AppPreferenceRepositoryImpl
import com.sketchart.sketchart.core.persistence.datastore.proto.AppProtoDataStoreRepository
import com.sketchart.sketchart.core.persistence.datastore.proto.AppProtoDataStoreRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideHttpClient(
        app: Application,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
            .addInterceptor(
                ChuckerInterceptor.Builder(app)
                    .collector(ChuckerCollector(app))
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(false)
                    .build()
            )
            .build()
    }

    @Singleton
    @Provides
    fun providePreferenceDataStoreRepository(app: Application): AppPreferenceRepository =
        AppPreferenceRepositoryImpl(app)

    @Singleton
    @Provides
    fun provideProtoDataStoreRepository(app: Application): AppProtoDataStoreRepository =
        AppProtoDataStoreRepositoryImpl(app)
}