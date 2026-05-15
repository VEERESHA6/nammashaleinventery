package com.example.nammashaleinventery.ui.healthcheck

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nammashaleinventery.data.local.entities.Asset
import com.example.nammashaleinventery.data.local.entities.AssetCondition
import com.example.nammashaleinventery.ui.theme.*
import com.example.nammashaleinventery.viewmodel.AssetViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthCheckScreen(viewModel: AssetViewModel, onBack: () -> Unit) {
    val assets by viewModel.allAssets.observeAsState(initial = emptyList<Asset>())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Monthly Health Check", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = SchoolPrimary)
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize().background(BackgroundLight)) {
            Box(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Select current status for each item below.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
            
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(assets) { asset ->
                    HealthCheckCard(
                        asset = asset,
                        onConditionChange = { newCondition ->
                            viewModel.updateAsset(asset.copy(condition = newCondition))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun HealthCheckCard(asset: Asset, onConditionChange: (AssetCondition) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(asset.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text("SN: ${asset.serialNumber} • ${asset.category}", fontSize = 12.sp, color = Color.Gray)
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                HealthButton("Working", SuccessGreen, asset.condition == AssetCondition.WORKING, Modifier.weight(1f)) {
                    onConditionChange(AssetCondition.WORKING)
                }
                HealthButton("Repair", WarningOrange, asset.condition == AssetCondition.NEEDS_REPAIR, Modifier.weight(1f)) {
                    onConditionChange(AssetCondition.NEEDS_REPAIR)
                }
                HealthButton("Broken", ErrorRed, asset.condition == AssetCondition.BROKEN, Modifier.weight(1f)) {
                    onConditionChange(AssetCondition.BROKEN)
                }
            }
        }
    }
}

@Composable
fun HealthButton(label: String, color: Color, isSelected: Boolean, modifier: Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) color else color.copy(alpha = 0.1f),
            contentColor = if (isSelected) Color.White else color
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.height(36.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        if (isSelected) {
            Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(4.dp))
        }
        Text(label, fontSize = 11.sp, fontWeight = FontWeight.Bold)
    }
}
