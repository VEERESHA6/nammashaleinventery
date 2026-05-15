package com.example.nammashaleinventery.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class AssetCondition {
    WORKING, NEEDS_REPAIR, BROKEN, LOST
}

@Entity(tableName = "assets")
data class Asset(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val serialNumber: String,
    val category: String,
    val purchaseDate: Long,
    val condition: AssetCondition,
    val photoUri: String? = null
)
