package com.naruto.narutoquiz.data.local.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.naruto.narutoquiz.data.local.model.GameHistory
import com.naruto.narutoquiz.data.network.model.HistoryRowModel
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {

    @Query("SELECT game_mode,true_count,false_count FROM GameHistory WHERE user_mail = :userMail")
    fun getUserHistory(userMail: String): Flow<List<HistoryRowModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertGame(vararg game: GameHistory)
}