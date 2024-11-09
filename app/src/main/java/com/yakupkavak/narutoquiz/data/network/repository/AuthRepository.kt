package com.yakupkavak.narutoquiz.data.network.repository

import com.google.firebase.auth.EmailAuthProvider
import com.yakupkavak.narutoquiz.data.base.BaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.UserProfileChangeRequest
import com.yakupkavak.narutoquiz.R
import com.yakupkavak.narutoquiz.data.network.util.AuthProvider
import com.yakupkavak.narutoquiz.data.network.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume

class AuthRepository @Inject constructor(
    val auth: FirebaseAuth,
    val authProvider: AuthProvider
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

    suspend fun changePassword(currentPassword: String, newPassword: String): Resource<Int> {
        val user = auth.currentUser
        val credential =
            EmailAuthProvider.getCredential(authProvider.getUserMail() ?: "", currentPassword)

        return withContext(Dispatchers.IO) {
            try {
                user?.reauthenticate(credential)?.await()
                user?.updatePassword(newPassword)?.await()
                return@withContext Resource.success(R.string.success)
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                return@withContext Resource.success(R.string.wrong_password)
            } catch (e: Exception) {
                return@withContext Resource.error(error = e)
            }
        }
    }

    suspend fun deleteAccount(currentPassword: String): Resource<Int> {
        val user = auth.currentUser
        val credential =
            EmailAuthProvider.getCredential(authProvider.getUserMail() ?: "", currentPassword)
        return withContext(Dispatchers.IO) {
            try {
                user?.reauthenticate(credential)?.await()
                user?.delete()?.await()
                return@withContext Resource.success(R.string.delete_success)
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                return@withContext Resource.error(error = e)
            } catch (e: Exception) {
                return@withContext Resource.error(error = e)
            }
        }
    }
}