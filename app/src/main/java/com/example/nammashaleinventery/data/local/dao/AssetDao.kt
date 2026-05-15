package com.example.nammashaleinventery.data.local.dao

import androidx.room.*
import com.example.nammashaleinventery.data.local.entities.Asset
import com.example.nammashaleinventery.data.local.entities.AssetCondition
import com.example.nammashaleinventery.data.local.entities.IssueLog
import kotlinx.coroutines.flow.Flow

@Dao
interface AssetDao {
    @Query("SELECT * FROM assets")
    fun getAllAssets(): Flow<List<Asset>>

    @Query("SELECT * FROM assets WHERE id = :id")
    suspend fun getAssetById(id: Int): Asset?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsset(asset: Asset)

    @Update
    suspend fun updateAsset(asset: Asset)

    @Delete
    suspend fun deleteAsset(asset: Asset)

    @Query("SELECT * FROM assets WHERE condition = :condition")
    fun getAssetsByCondition(condition: AssetCondition): Flow<List<Asset>>

    // Issue Logs
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIssueLog(issueLog: IssueLog)

    @Query("SELECT * FROM issue_logs WHERE assetId = :assetId")
    fun getIssueLogsForAsset(assetId: Int): Flow<List<IssueLog>>

    @Query("SELECT * FROM issue_logs")
    fun getAllIssueLogs(): Flow<List<IssueLog>>
}
