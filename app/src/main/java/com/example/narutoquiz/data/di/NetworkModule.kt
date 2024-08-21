package com.example.narutoquiz.data.di

import com.example.narutoquiz.BuildConfig
import com.example.narutoquiz.data.repository.NarutoRepository
import com.example.narutoquiz.data.service.NarutoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofitService(): NarutoService {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
            .create(NarutoService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(pokeService: NarutoService): NarutoRepository {
        return NarutoRepository(pokeService)
    }
}