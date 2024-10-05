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

    @Query("SELECT game_mode as gameMode,true_count as trueCount,false_count as falseCount FROM GameHistory WHERE user_mail = :userMail")
    fun getUserHistory(userMail: String): Flow<List<HistoryRowModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertGame(vararg game: GameHistory)
}