package com.naruto.narutoquiz.data.network.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.naruto.narutoquiz.data.base.BaseRepository
import com.naruto.narutoquiz.data.network.util.AuthProvider
import com.naruto.narutoquiz.data.network.util.Resource
import com.naruto.narutoquiz.data.network.util.ServiceCountConst.COLLECTION_PATH_RANK
import com.naruto.narutoquiz.data.network.util.ServiceCountConst.CREATE_DATE
import com.naruto.narutoquiz.data.network.util.ServiceCountConst.TRUE_COUNT
import com.naruto.narutoquiz.data.network.util.ServiceCountConst.USER_NAME
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.naruto.narutoquiz.data.network.model.RankModel
import com.naruto.narutoquiz.data.network.model.RankRowModel
import com.naruto.narutoquiz.data.network.util.ServiceCountConst.COLLECTION_PATH_TOKEN
import com.naruto.narutoquiz.data.network.util.ServiceCountConst.TOKEN_COUNT
import com.naruto.narutoquiz.data.network.util.ServiceCountConst.USER_MAIL
import com.naruto.narutoquiz.data.network.util.Status
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

    suspend fun postGameScore(trueAnswer: Int?) {
        if (trueAnswer != null) {
            withContext(Dispatchers.IO) {
                val user = authProvider.getUserName()
                user?.let { currentUserName ->
                    val gameStation = hashMapOf(
                        USER_NAME to currentUserName,
                        TRUE_COUNT to trueAnswer,
                        CREATE_DATE to FieldValue.serverTimestamp()
                    )
                    val currentDocument = getUserRankData()
                    if (currentDocument.status == Status.SUCCESS) {
                        if (currentDocument.data?.trueCount == null) {
                            db.collection(COLLECTION_PATH_RANK).add(gameStation)
                                .addOnSuccessListener {
                                    Log.d(TAG, "DocumentSnapshot added with ID: ${it.id}")
                                }.addOnFailureListener { e -> //TODO"HATAYA ÇIKAN NETWORK HATASI"
                                    Log.w(TAG, "Error adding document", e)
                                }
                        } else {
                            if (trueAnswer > currentDocument.data.trueCount){
                                currentDocument.data.documentReference?.update(gameStation)
                            }
                        }
                    }
                }
            }
        } else {
            Log.w(TAG, "Null trueAnswer")
        }
    }

    private suspend fun getUserRankData(): Resource<RankModel> {
        val userName = authProvider.getUserName()
        return withContext(Dispatchers.IO) {
            try {
                val documents =
                    db.collection(COLLECTION_PATH_RANK).whereEqualTo(USER_NAME, userName)
                        .get().await()
                for (document in documents.documents) {
                    val trueCount = document.getLong(TRUE_COUNT)?.toInt() ?: 0
                    val documentReference = document.reference
                    return@withContext Resource.success(
                        RankModel(
                            documentReference = documentReference,
                            trueCount = trueCount
                        )
                    )
                }
                return@withContext Resource.success(
                    RankModel(
                        documentReference = null,
                        trueCount = null
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext Resource.error(null)
            }
        }
    }

    suspend fun getChallengeData(): Resource<ArrayList<RankRowModel>> {
        var rankNumber = 1
        val returnList = ArrayList<RankRowModel>()
        return withContext(Dispatchers.IO) {
            try {
                val documents =
                    db.collection(COLLECTION_PATH_RANK)
                        .orderBy(TRUE_COUNT, Query.Direction.DESCENDING)
                        .get().await()
                for (document in documents.documents) {
                    returnList.add(
                        RankRowModel(
                            userRank = rankNumber,
                            userName = ((document?.get(USER_NAME)) as? String
                                ?: "Unknown").toString(),
                            userScore = (document?.get(TRUE_COUNT) as Long?)?.toInt() ?: 0
                        )
                    )
                    rankNumber++
                    //TODO KULLANICININ GÜNCEL RANK BİLGİSİ BURADAN ALINACAK
                }
                return@withContext Resource.success(returnList)
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext Resource.error(null)
            }
        }
    }

    suspend fun updateUserToken(updateToken: Int): Resource<Int> {
        return withContext(Dispatchers.IO) {
            try {
                val user = authProvider.getUserMail()
                user?.let { currentUserMail ->
                    return@withContext suspendCancellableCoroutine<Resource<Int>> { continuation ->
                        db.collection(COLLECTION_PATH_TOKEN)
                            .whereEqualTo(USER_MAIL, currentUserMail).get()
                            .addOnSuccessListener { document ->
                                val currentDocument = document.documents[0].reference
                                currentDocument.update(TOKEN_COUNT, updateToken)
                                    .addOnSuccessListener {
                                        Log.d("Firestore", "Update document")
                                        continuation.resume(Resource.success(updateToken))
                                    }.addOnFailureListener { e ->
                                        Log.w("Firestore", "Can't update document", e)
                                        continuation.resume(Resource.error(error = e))
                                    }
                            }
                    }
                }
                return@withContext Resource.error(null)
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext Resource.error(error = e)
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
                return@withContext Resource.error(e)
            }
        }
    }

}