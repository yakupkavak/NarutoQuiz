package com.naruto.narutoquiz.data.base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.naruto.narutoquiz.data.local.model.GameHistory
import com.naruto.narutoquiz.data.local.service.GameDao
import com.naruto.narutoquiz.data.local.util.ServiceConst.DATABASE_NAME

@Database(entities = arrayOf(GameHistory::class), version = 1, exportSchema = false)
abstract class GameRoomDatabase : RoomDatabase() {

    abstract fun gameDao(): GameDao

    companion object {
        @Volatile
        private var INSTANCE: GameRoomDatabase? = null

        fun getDatabase(context: Context): GameRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, GameRoomDatabase::class.java, DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}