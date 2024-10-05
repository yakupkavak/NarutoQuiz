package com.naruto.narutoquiz.data.di

import android.content.Context
import androidx.room.Room
import com.naruto.narutoquiz.BuildConfig
import com.naruto.narutoquiz.data.network.repository.AuthRepository
import com.naruto.narutoquiz.data.network.repository.FirestoreRepository
import com.naruto.narutoquiz.data.network.repository.GeminiRepository
import com.naruto.narutoquiz.data.network.repository.NarutoRepository
import com.naruto.narutoquiz.data.network.service.NarutoService
import com.naruto.narutoquiz.data.network.util.AuthProvider
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.naruto.narutoquiz.data.base.GameRoomDatabase
import com.naruto.narutoquiz.data.local.repository.DaoRepository
import com.naruto.narutoquiz.data.local.service.GameDao
import com.naruto.narutoquiz.data.local.util.ServiceConst.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): GameRoomDatabase {
        return Room.databaseBuilder(appContext, GameRoomDatabase::class.java, DATABASE_NAME).build()
    }

    @Provides
    @Singleton
    fun provideDaoService(db: GameRoomDatabase): GameDao = db.gameDao()

    @Provides
    @Singleton
    fun provideDaoRepository(gameDao: GameDao, authProvider: AuthProvider) =
        DaoRepository(gameDao, authProvider)
}