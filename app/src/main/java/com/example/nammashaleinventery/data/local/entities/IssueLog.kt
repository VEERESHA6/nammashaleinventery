package com.example.nammashaleinventery.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "issue_logs")
data class IssueLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val assetId: Int,
    val description: String,
    val date: Long,
    val reason: String
)
