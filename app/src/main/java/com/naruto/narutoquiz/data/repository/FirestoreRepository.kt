package com.naruto.narutoquiz.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.naruto.narutoquiz.data.base.BaseRepository
import com.naruto.narutoquiz.data.model.HistoryRowModel
import com.naruto.narutoquiz.data.model.RankRowModel
import com.naruto.narutoquiz.data.util.AuthProvider
import com.naruto.narutoquiz.data.util.Resource
import com.naruto.narutoquiz.data.util.ServiceCountConst.COLLECTION_PATH_HISTORY
import com.naruto.narutoquiz.data.util.ServiceCountConst.CREATE_DATE
import com.naruto.narutoquiz.data.util.ServiceCountConst.FALSE_COUNT
import com.naruto.narutoquiz.data.util.ServiceCountConst.GAME_ID
import com.naruto.narutoquiz.data.util.ServiceCountConst.TRUE_COUNT
import com.naruto.narutoquiz.data.util.ServiceCountConst.USER_NAME
import com.naruto.narutoquiz.ui.mainScreen.game.GameConst.ChallangeGameId
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.naruto.narutoquiz.data.util.ServiceCountConst.COLLECTION_PATH_TOKEN
import com.naruto.narutoquiz.data.util.ServiceCountConst.TOKEN_COUNT
import com.naruto.narutoquiz.data.util.ServiceCountConst.USER_MAIL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume

class FirestoreRepository @Inject constructor(
    private val db: FirebaseFirestore,
    private val authProvider: AuthProvider
) : BaseRepository() {

    suspend fun postGameScore(gameId: Int?, trueAnswer: Int?, falseAnswer: Int?) {
        if (gameId != null && trueAnswer != null && falseAnswer != null) {
            withContext(Dispatchers.IO) {
                val user = authProvider.getUserName()
                user?.let { currentUserName ->
                    val gameStation = hashMapOf(
                        USER_NAME to currentUserName,
                        GAME_ID to gameId,
                        TRUE_COUNT to trueAnswer,
                        FALSE_COUNT to falseAnswer,
                        CREATE_DATE to FieldValue.serverTimestamp()
                    )
                    db.collection(COLLECTION_PATH_HISTORY).add(gameStation).addOnSuccessListener {
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
                    db.collection(COLLECTION_PATH_HISTORY).whereEqualTo(GAME_ID, ChallangeGameId)
                        .orderBy(TRUE_COUNT, Query.Direction.DESCENDING)
                        .get().await()
                val filteredDocuments = documents
                    .groupBy { it.getString(USER_NAME) }
                    .mapValues { entry ->
                        entry.value.maxByOrNull { it.getLong(TRUE_COUNT) ?: 0 }
                    }
                    .values
                for (document in filteredDocuments) {
                    returnList.add(
                        RankRowModel(
                            userRank = rankNumber,
                            userName = ((document?.get(USER_NAME)) ?: "").toString(),
                            userScore = (document?.get(TRUE_COUNT) as Long?)?.toInt() ?: 0
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
            val userName = authProvider.getUserName()
            try {
                val documents =
                    db.collection(COLLECTION_PATH_HISTORY).whereEqualTo(USER_NAME, userName)
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

    suspend fun postUserToken(tokenCount: Int): Resource<Boolean?> {
        return withContext(Dispatchers.IO) {
            try {
                val user = authProvider.getUserMail()
                user?.let { currentUserMail ->
                    val gameStation = hashMapOf(
                        USER_MAIL to currentUserMail,
                        TOKEN_COUNT to tokenCount,
                        CREATE_DATE to FieldValue.serverTimestamp()
                    )
                    return@withContext suspendCancellableCoroutine<Resource<Boolean?>> { continuation ->
                        db.collection(COLLECTION_PATH_TOKEN).add(gameStation)
                            .addOnSuccessListener { data ->
                                Log.d(TAG, "DocumentSnapshot added with ID: ${data.id}")
                                continuation.resume(Resource.success(null))
                            }.addOnFailureListener { e -> //TODO"HATAYA ÇIKAN NETWORK HATASI"
                                Log.w(TAG, "Error adding document", e)
                                continuation.resume(Resource.error(null))
                            }
                    }
                }
                return@withContext Resource.error(null)
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext Resource.error(null)
            }
        }
    }

    suspend fun updateUserToken(tokenCount: Int): Resource<Boolean?> {
        return withContext(Dispatchers.IO) {
            try {
                val user = authProvider.getUserMail()
                user?.let { currentUserMail ->
                    return@withContext suspendCancellableCoroutine<Resource<Boolean?>> { continuation ->
                        db.collection(COLLECTION_PATH_TOKEN)
                            .whereEqualTo(USER_MAIL, currentUserMail).get()
                            .addOnSuccessListener { document ->
                                val currentDocument = document.documents[0].reference
                                currentDocument.update(TOKEN_COUNT, tokenCount)
                                    .addOnSuccessListener {
                                        Log.d("Firestore", "Update document")
                                        continuation.resume(Resource.success(null))
                                    }.addOnFailureListener { e ->
                                        Log.w("Firestore", "Can't update document", e)
                                        continuation.resume(Resource.error(null))
                                    }
                            }
                    }
                }
                return@withContext Resource.error(null)
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext Resource.error(null)
            }
        }
    }

    suspend fun getUserToken(): Resource<Int> {
        return withContext(Dispatchers.IO) {
            try {
                val tokenCount: Int
                val userMail = authProvider.getUserMail()
                val documents =
                    db.collection(COLLECTION_PATH_TOKEN).whereEqualTo(USER_MAIL, userMail)
                        .get().await()

                tokenCount = (documents.documents[0][TOKEN_COUNT] as Long?)?.toInt() ?: 0

                if (tokenCount == 0) {
                    return@withContext Resource.success(0)
                } else {
                    return@withContext Resource.success(tokenCount)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext Resource.error(0)
            }
        }
    }

}