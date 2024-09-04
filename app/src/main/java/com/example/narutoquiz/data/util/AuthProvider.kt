package com.example.narutoquiz.data.util

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class AuthProvider @Inject constructor(
    val auth: FirebaseAuth
) {
    fun getUserMail(): String? {
        return auth.currentUser?.email
    }

    fun getUserName(): String?{
        return auth.currentUser?.displayName
    }
}