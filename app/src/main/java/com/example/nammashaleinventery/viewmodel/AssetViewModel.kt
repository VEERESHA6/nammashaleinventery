package com.example.nammashaleinventery.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.nammashaleinventery.data.local.database.AppDatabase
import com.example.nammashaleinventery.data.local.entities.Asset
import com.example.nammashaleinventery.data.local.entities.AssetCondition
import com.example.nammashaleinventery.data.local.entities.IssueLog
import com.example.nammashaleinventery.data.repository.AssetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class AssetViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AssetRepository
    val allAssets: LiveData<List<Asset>>
    val allIssueLogs: LiveData<List<IssueLog>>

    init {
        val assetDao = AppDatabase.getDatabase(application).assetDao()
        repository = AssetRepository(assetDao)
        allAssets = repository.allAssets.asLiveData()
        allIssueLogs = repository.allIssueLogs.asLiveData()
        
        // Optional: Pre-populate with dummy data if empty
        viewModelScope.launch {
            repository.allAssets.collect { assets ->
                if (assets.isEmpty()) {
                    repository.insertAsset(Asset(name = "Sports Kit", serialNumber = "SP001", category = "Sports", purchaseDate = System.currentTimeMillis(), condition = AssetCondition.WORKING))
                    repository.insertAsset(Asset(name = "Lab Microscope", serialNumber = "LB042", category = "Lab", purchaseDate = System.currentTimeMillis(), condition = AssetCondition.NEEDS_REPAIR))
                    repository.insertAsset(Asset(name = "Android Tablet", serialNumber = "TB015", category = "IT", purchaseDate = System.currentTimeMillis(), condition = AssetCondition.BROKEN))
                }
            }
        }
    }

    fun insertAsset(asset: Asset) = viewModelScope.launch {
        repository.insertAsset(asset)
    }

    fun updateAsset(asset: Asset) = viewModelScope.launch {
        repository.updateAsset(asset)
    }

    fun deleteAsset(asset: Asset) = viewModelScope.launch {
        repository.deleteAsset(asset)
    }

    fun getAssetById(id: Int, onResult: (Asset?) -> Unit) = viewModelScope.launch {
        onResult(repository.getAssetById(id))
    }

    fun insertIssueLog(issueLog: IssueLog) = viewModelScope.launch {
        repository.insertIssueLog(issueLog)
    }

    fun getIssueLogsForAsset(assetId: Int): LiveData<List<IssueLog>> {
        return repository.getIssueLogsForAsset(assetId).asLiveData()
    }

    fun getAssetsByCondition(condition: AssetCondition): LiveData<List<Asset>> {
        return repository.getAssetsByCondition(condition).asLiveData()
    }
}
