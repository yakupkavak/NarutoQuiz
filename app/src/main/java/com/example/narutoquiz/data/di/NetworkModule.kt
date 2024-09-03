package com.example.narutoquiz.data.di

import com.example.narutoquiz.BuildConfig
import com.example.narutoquiz.data.repository.FirebaseRepository
import com.example.narutoquiz.data.repository.NarutoRepository
import com.example.narutoquiz.data.service.NarutoService
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideObjectMapper(): ObjectMapper {
        return ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
    }

    @Provides
    @Singleton
    fun provideRetrofitService(mapper: ObjectMapper): NarutoService {
        return Retrofit.Builder().addConverterFactory(JacksonConverterFactory.create(mapper))
            .baseUrl(BuildConfig.BASE_URL)
            .build()
            .create(NarutoService::class.java)
    }

    @Provides
    @Singleton
    fun provideNarutoRepository(narutoService: NarutoService): NarutoRepository {
        return NarutoRepository(narutoService)
    }

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideFireauth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseRepository(db: FirebaseFirestore, auth: FirebaseAuth): FirebaseRepository {
        return FirebaseRepository(db, auth)
    }
}