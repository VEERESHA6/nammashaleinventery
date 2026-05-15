package com.example.nammashaleinventery.ui.repairs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.nammashaleinventery.data.local.entities.Asset
import com.example.nammashaleinventery.data.local.entities.AssetCondition
import com.example.nammashaleinventery.viewmodel.AssetViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepairRequestScreen(viewModel: AssetViewModel, onBack: () -> Unit) {
    val assets by viewModel.getAssetsByCondition(AssetCondition.NEEDS_REPAIR).observeAsState(initial = emptyList<Asset>())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Repair Requests") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (assets.isEmpty()) {
            Box(modifier = Modifier.padding(padding).fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text("No items need repair")
            }
        } else {
            LazyColumn(modifier = Modifier.padding(padding).fillMaxSize()) {
                items(assets) { asset ->
                    ListItem(
                        headlineContent = { Text(asset.name) },
                        supportingContent = { Text("SN: ${asset.serialNumber}") },
                        trailingContent = {
                            Button(onClick = {
                                viewModel.updateAsset(asset.copy(condition = AssetCondition.WORKING))
                            }) {
                                Text("Mark Repaired")
                            }
                        },
                        leadingContent = {
                            Icon(Icons.Default.Build, contentDescription = null, tint = Color.Yellow)
                        }
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}
