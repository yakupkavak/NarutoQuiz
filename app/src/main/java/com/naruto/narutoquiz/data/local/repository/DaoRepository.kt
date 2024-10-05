package com.naruto.narutoquiz.data.local.repository

import androidx.annotation.WorkerThread
import com.naruto.narutoquiz.data.local.model.GameHistory
import com.naruto.narutoquiz.data.local.service.GameDao
import com.naruto.narutoquiz.data.network.model.HistoryRowModel
import com.naruto.narutoquiz.data.network.util.AuthProvider
import kotlinx.coroutines.flow.Flow

class DaoRepository(private val gameDao: GameDao, private val authProvider: AuthProvider) {

    val allHistory: Flow<List<HistoryRowModel>> =
        gameDao.getUserHistory(authProvider.getUserMail() ?: "")

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