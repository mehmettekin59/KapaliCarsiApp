package com.mehmettekin.kapalicarsiapp.di

import com.mehmettekin.kapalicarsiapp.data.KapaliCarsiApi
import com.mehmettekin.kapalicarsiapp.data.repository.KapaliCarsiRepositoryImpl
import com.mehmettekin.kapalicarsiapp.domain.repository.KapaliCarsiRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideKapaliCarsiApi(moshi: Moshi): KapaliCarsiApi {
        return Retrofit.Builder()
            .baseUrl("https://kapalicarsi.apiluna.org")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(KapaliCarsiApi::class.java)
    }

    @Provides
    @Singleton
    fun provideKapaliCarsiRepository(api: KapaliCarsiApi): KapaliCarsiRepository {
        return KapaliCarsiRepositoryImpl(api)
    }
}