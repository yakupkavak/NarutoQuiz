package com.naruto.narutoquiz.data.di

import com.naruto.narutoquiz.BuildConfig
import com.naruto.narutoquiz.data.repository.AuthRepository
import com.naruto.narutoquiz.data.repository.FirestoreRepository
import com.naruto.narutoquiz.data.repository.GeminiRepository
import com.naruto.narutoquiz.data.repository.NarutoRepository
import com.naruto.narutoquiz.data.service.NarutoService
import com.naruto.narutoquiz.data.util.AuthProvider
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
    fun provideAuthProvider(auth: FirebaseAuth): AuthProvider {
        return AuthProvider(auth)
    }

    @Provides
    @Singleton
    fun provideFirebaseRepository(
        db: FirebaseFirestore,
        authProvider: AuthProvider
    ): FirestoreRepository {
        return FirestoreRepository(db, authProvider)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(auth: FirebaseAuth): AuthRepository {
        return AuthRepository(auth)
    }

    @Provides
    @Singleton
    fun provideGeminiRepository(): GeminiRepository {
        return GeminiRepository()
    }
}