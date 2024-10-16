package com.naruto.narutoquiz.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "GameHistory",
    indices = [Index(value = ["user_mail"], unique = false)]
)
data class GameHistory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "user_mail") val userMail: String,
    @ColumnInfo(name = "game_mode") val gameMode: Int,
    @ColumnInfo(name = "true_count") val trueCount: Int,
    @ColumnInfo(name = "false_count") val falseCount: Int,
)
