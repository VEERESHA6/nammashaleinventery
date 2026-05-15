package com.example.nammashaleinventery.data.repository

import com.example.nammashaleinventery.data.local.dao.AssetDao
import com.example.nammashaleinventery.data.local.entities.Asset
import com.example.nammashaleinventery.data.local.entities.AssetCondition
import com.example.nammashaleinventery.data.local.entities.IssueLog
import kotlinx.coroutines.flow.Flow

class AssetRepository(private val assetDao: AssetDao) {
    val allAssets: Flow<List<Asset>> = assetDao.getAllAssets()
    val allIssueLogs: Flow<List<IssueLog>> = assetDao.getAllIssueLogs()

    suspend fun insertAsset(asset: Asset) = assetDao.insertAsset(asset)
    suspend fun updateAsset(asset: Asset) = assetDao.updateAsset(asset)
    suspend fun deleteAsset(asset: Asset) = assetDao.deleteAsset(asset)
    suspend fun getAssetById(id: Int) = assetDao.getAssetById(id)
    fun getAssetsByCondition(condition: AssetCondition) = assetDao.getAssetsByCondition(condition)

    suspend fun insertIssueLog(issueLog: IssueLog) = assetDao.insertIssueLog(issueLog)
    fun getIssueLogsForAsset(assetId: Int) = assetDao.getIssueLogsForAsset(assetId)
}
