package com.example.nammashaleinventery.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.nammashaleinventery.data.local.dao.AssetDao
import com.example.nammashaleinventery.data.local.entities.Asset
import com.example.nammashaleinventery.data.local.entities.IssueLog
import androidx.room.TypeConverter
import com.example.nammashaleinventery.data.local.entities.AssetCondition

class Converters {
    @TypeConverter
    fun fromAssetCondition(value: AssetCondition): String {
        return value.name
    }

    @TypeConverter
    fun toAssetCondition(value: String): AssetCondition {
        return AssetCondition.valueOf(value)
    }
}

@Database(entities = [Asset::class, IssueLog::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun assetDao(): AssetDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "namma_shaale_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
