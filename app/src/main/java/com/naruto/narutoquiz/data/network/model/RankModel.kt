package com.naruto.narutoquiz.data.network.model

import com.google.firebase.firestore.DocumentReference

data class RankModel(
    val documentReference: DocumentReference?,
    val trueCount: Int?,
)
