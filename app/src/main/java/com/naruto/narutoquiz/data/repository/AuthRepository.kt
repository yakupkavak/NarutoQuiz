package com.naruto.narutoquiz.data.repository

import com.naruto.narutoquiz.data.base.BaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import javax.inject.Inject

class AuthRepository @Inject constructor(
    val auth: FirebaseAuth
) : BaseRepository() {

    suspend fun signIn(userMail: String, userPassword: String) =
        firebaseJob { auth.signInWithEmailAndPassword(userMail, userPassword) }

    suspend fun signUp(userMail: String, userPassword: String) =
        firebaseJob { auth.createUserWithEmailAndPassword(userMail, userPassword) }

    suspend fun addUserName(userName: String) =
        firebaseJob {
            auth.currentUser?.let { user ->
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(userName)
                    .build()
                user.updateProfile(profileUpdates)
            } ?: throw IllegalStateException("No user is currently signed in.")
        }

    suspend fun recoverMail(userMail: String) =
        firebaseJob { auth.sendPasswordResetEmail(userMail) }

}