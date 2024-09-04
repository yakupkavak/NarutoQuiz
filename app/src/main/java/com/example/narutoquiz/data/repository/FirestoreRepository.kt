package com.example.narutoquiz.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.narutoquiz.data.base.BaseRepository
import com.example.narutoquiz.data.model.HistoryRowModel
import com.example.narutoquiz.data.model.RankRowModel
import com.example.narutoquiz.data.util.AuthProvider
import com.example.narutoquiz.data.util.Resource
import com.example.narutoquiz.data.util.ServiceCountConst.COLLECTION_PATH
import com.example.narutoquiz.data.util.ServiceCountConst.CREATE_DATE
import com.example.narutoquiz.data.util.ServiceCountConst.FALSE_COUNT
import com.example.narutoquiz.data.util.ServiceCountConst.GAME_ID
import com.example.narutoquiz.data.util.ServiceCountConst.TRUE_COUNT
import com.example.narutoquiz.data.util.ServiceCountConst.USER
import com.example.narutoquiz.ui.mainScreen.game.GameConst.ChallangeGameId
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirestoreRepository @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val authProvider: AuthProvider
) : BaseRepository() {

    suspend fun postGameScore(gameId: Int?, trueAnswer: Int?, falseAnswer: Int?) {
        if (gameId != null && trueAnswer != null && falseAnswer != null) {
            withContext(Dispatchers.IO) {
                val user = auth.currentUser
                user?.let { currentUser ->
                    val gameStation = hashMapOf(
                        USER to currentUser.displayName,
                        GAME_ID to gameId,
                        TRUE_COUNT to trueAnswer,
                        FALSE_COUNT to falseAnswer,
                        CREATE_DATE to FieldValue.serverTimestamp()
                    )
                    db.collection(COLLECTION_PATH).add(gameStation).addOnSuccessListener {
                        Log.d(TAG, "DocumentSnapshot added with ID: ${it.id}")
                    }.addOnFailureListener { e -> //TODO"HATAYA ÇIKAN NETWORK HATASI"
                        Log.w(TAG, "Error adding document", e)
                    }
                }
            }
        } else {
            Log.w(TAG, "Null gameId, trueAnswer or falseAnswer")
        }
    }

    suspend fun getChallengeData(): Resource<ArrayList<RankRowModel>> {
        var rankNumber = 1
        val returnList = ArrayList<RankRowModel>()
        return withContext(Dispatchers.IO) {
            try {
                val documents =
                    db.collection(COLLECTION_PATH).whereEqualTo(GAME_ID, ChallangeGameId)
                        .orderBy(TRUE_COUNT, Query.Direction.DESCENDING)
                        .get().await()
                for (document in documents) {
                    returnList.add(
                        RankRowModel(
                            userRank = rankNumber,
                            userName = ((document[USER]) ?: "").toString(),
                            userScore = (document[TRUE_COUNT] as Long?)?.toInt() ?: 0
                        )
                    )
                    rankNumber++
                }
                return@withContext Resource.success(returnList)
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext Resource.error(null)
            }
        }
    }
    suspend fun getUserHistory(): Resource<ArrayList<HistoryRowModel>> {
        val returnList = ArrayList<HistoryRowModel>()
        return withContext(Dispatchers.IO) {
            try {
                val documents =
                    db.collection(COLLECTION_PATH).whereEqualTo(USER, authProvider.getUserMail())
                        .orderBy(CREATE_DATE, Query.Direction.DESCENDING)
                        .get().await()
                for (document in documents) {
                    returnList.add(
                        HistoryRowModel(
                            gameMode = (document[GAME_ID] as Long?)?.toInt() ?: 0,
                            trueCount = (document[TRUE_COUNT] as Long?)?.toInt() ?: 0,
                            falseCount = (document[FALSE_COUNT] as Long?)?.toInt() ?: 0
                        )
                    )
                }
                return@withContext Resource.success(returnList)
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext Resource.error(null)
            }
        }
    }
}