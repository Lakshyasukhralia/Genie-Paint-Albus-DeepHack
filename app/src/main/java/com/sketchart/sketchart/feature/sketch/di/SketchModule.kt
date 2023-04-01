package com.sketchart.sketchart.feature.sketch.di

import android.app.Application
import com.sketchart.sketchart.feature.sketch.data.api.SketchApi
import com.sketchart.sketchart.feature.sketch.data.repository.SketchRepositoryImpl
import com.sketchart.sketchart.feature.sketch.domain.repository.SketchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SketchModule {

    @Provides
    @Singleton
    fun provideUserAuthApi(httpClient: OkHttpClient): SketchApi {
        return Retrofit.Builder().baseUrl(SketchApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
            .create(SketchApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserAuthRepository(api: SketchApi, app: Application): SketchRepository {
        return SketchRepositoryImpl(sketchApi = api, app = app)
    }
}