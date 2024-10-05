package com.naruto.narutoquiz.data.network.util

import com.google.firebase.auth.FirebaseAuth
import com.naruto.narutoquiz.data.network.model.UserInfoModel
import javax.inject.Inject

class AuthProvider @Inject constructor(
    private val auth: FirebaseAuth
) {
    fun getUserInformation(): Resource<UserInfoModel> {
        val userMail = auth.currentUser?.email
        val userName = auth.currentUser?.displayName
        return if (userMail != null && userName != null) {
            Resource.success(
                UserInfoModel(
                    userName = userName,
                    userMail = userMail
                )
            )
        } else {
            Resource.error(null)
        }
    }

    fun getUserMail(): String? {
        return auth.currentUser?.email
    }

    fun getUserName(): String? {
        return auth.currentUser?.displayName
    }
}