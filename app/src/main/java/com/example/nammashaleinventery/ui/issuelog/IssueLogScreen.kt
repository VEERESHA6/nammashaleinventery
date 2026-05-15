package com.example.nammashaleinventery.ui.issuelog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.nammashaleinventery.data.local.entities.IssueLog
import com.example.nammashaleinventery.viewmodel.AssetViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IssueLogScreen(viewModel: AssetViewModel, onBack: () -> Unit) {
    val issueLogs by viewModel.allIssueLogs.observeAsState(initial = emptyList<IssueLog>())
    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Issue Logs") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (issueLogs.isEmpty()) {
            Box(modifier = Modifier.padding(padding).fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text("No issues logged")
            }
        } else {
            LazyColumn(modifier = Modifier.padding(padding).fillMaxSize()) {
                items(issueLogs) { log ->
                    ListItem(
                        headlineContent = { Text(log.description) },
                        supportingContent = { Text("Reason: ${log.reason} | ${dateFormat.format(Date(log.date))}") },
                        leadingContent = {
                            Icon(Icons.Default.Warning, contentDescription = null, tint = Color.Red)
                        }
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}
