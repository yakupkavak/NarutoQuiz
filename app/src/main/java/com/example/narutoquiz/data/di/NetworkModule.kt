package com.example.narutoquiz.data.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    /*
    @Provides
    @Singleton
    fun provideRetrofitService(): NarutoService {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL_API)
            .build()
            .create(NarutoService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(pokeService: NarutoService): NarutoRepository {
        return NarutoRepository(pokeService)
    }

     */
}