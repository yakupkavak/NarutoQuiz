package com.yakupkavak.narutoquiz.data.local.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.yakupkavak.narutoquiz.data.local.model.GameHistory
import com.yakupkavak.narutoquiz.data.local.service.GameDao
import com.yakupkavak.narutoquiz.data.network.model.HistoryRowModel
import com.yakupkavak.narutoquiz.data.network.util.AuthProvider
import kotlinx.coroutines.flow.Flow

class DaoRepository(private val gameDao: GameDao, private val authProvider: AuthProvider) {

    fun getHistoryList() : LiveData<List<HistoryRowModel>>{
        val userMail = authProvider.getUserMail() ?: ""
        println("current user mail: $userMail")
        return gameDao.getUserHistory(authProvider.getUserMail() ?: "").asLiveData()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertGame(gameId: Int, trueCount: Int, falseCount: Int) {
        gameDao.insertGame(
            GameHistory(
                gameMode = gameId,
                trueCount = trueCount,
                falseCount = falseCount,
                userMail = authProvider.getUserMail() ?: "couldn't find mail"
            )
        )
    }
}