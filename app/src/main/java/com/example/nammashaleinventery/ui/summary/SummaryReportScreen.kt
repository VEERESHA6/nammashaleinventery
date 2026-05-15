package com.example.nammashaleinventery.ui.summary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
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
fun SummaryReportScreen(viewModel: AssetViewModel, onBack: () -> Unit) {
    val assets by viewModel.allAssets.observeAsState(initial = emptyList<Asset>())

    val total = assets.size
    val working = assets.count { it.condition == AssetCondition.WORKING }
    val repair = assets.count { it.condition == AssetCondition.NEEDS_REPAIR }
    val broken = assets.count { it.condition == AssetCondition.BROKEN || it.condition == AssetCondition.LOST }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Inventory Summary", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = { /* Share logic */ }) {
                        Icon(Icons.Default.Share, contentDescription = "Share", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = SchoolPrimary)
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize().background(BackgroundLight).padding(16.dp)) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Overall Asset Health", style = MaterialTheme.typography.titleLarge, color = SchoolPrimary, fontWeight = FontWeight.Bold)
                    
                    val healthPercentage = if (total > 0) (working.toFloat() / total) else 0f
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Box(contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(
                            progress = { healthPercentage },
                            modifier = Modifier.size(150.dp),
                            color = SuccessGreen,
                            strokeWidth = 12.dp,
                            trackColor = Color.LightGray.copy(alpha = 0.3f)
                        )
                        Text(
                            "${(healthPercentage * 100).toInt()}%",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = SchoolPrimary
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        ReportStatRow("Total Assets", total.toString(), SchoolSecondary)
                        ReportStatRow("Functional", working.toString(), SuccessGreen)
                        ReportStatRow("Maintenance", repair.toString(), WarningOrange)
                        ReportStatRow("Defective/Lost", broken.toString(), ErrorRed)
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = { /* Export to PDF */ },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SchoolPrimary)
            ) {
                Icon(Icons.Default.Share, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Export PDF Report", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun ReportStatRow(label: String, value: String, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(modifier = Modifier.size(12.dp), shape = RoundedCornerShape(3.dp), color = color) {}
            Spacer(modifier = Modifier.width(8.dp))
            Text(label, color = Color.Gray, fontWeight = FontWeight.Medium)
        }
        Text(value, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.DarkGray)
    }
}
