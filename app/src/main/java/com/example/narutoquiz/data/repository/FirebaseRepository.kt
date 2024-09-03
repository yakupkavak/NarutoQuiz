package com.example.narutoquiz.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.narutoquiz.data.base.BaseRepository
import com.example.narutoquiz.data.util.ServiceCountConst.COLLECTION_PATH
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseRepository @Inject constructor(
    val db: FirebaseFirestore,
    val auth: FirebaseAuth
) : BaseRepository() {

    suspend fun postGameScore(gameId: Int?, trueAnswer: Int?, falseAnswer: Int?) {
        if (gameId != null && trueAnswer != null && falseAnswer != null) {
            withContext(Dispatchers.IO) {
                val user = auth.currentUser
                user?.let { currentUser ->
                    val gameStation = hashMapOf(
                        "user" to currentUser.email,
                        "gameId" to gameId,
                        "trueCount" to trueAnswer,
                        "falseCount" to falseAnswer,
                    )
                    db.collection(COLLECTION_PATH).add(gameStation).addOnSuccessListener {
                        Log.d(TAG, "DocumentSnapshot added with ID: ${it.id}")
                    }.addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }
                }
            }
        } else {
            Log.w(TAG, "Null gameId, trueAnswer or falseAnswer")
        }
    }
}