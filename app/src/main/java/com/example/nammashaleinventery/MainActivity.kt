package com.example.nammashaleinventery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nammashaleinventery.ui.assetlist.AssetListScreen
import com.example.nammashaleinventery.ui.dashboard.DashboardScreen
import com.example.nammashaleinventery.ui.healthcheck.HealthCheckScreen
import com.example.nammashaleinventery.ui.issuelog.IssueLogScreen
import com.example.nammashaleinventery.ui.navigation.Screen
import com.example.nammashaleinventery.ui.registration.AssetRegistrationScreen
import com.example.nammashaleinventery.ui.repairs.RepairRequestScreen
import com.example.nammashaleinventery.ui.summary.SummaryReportScreen
import com.example.nammashaleinventery.ui.theme.NammashaleinventeryTheme
import com.example.nammashaleinventery.viewmodel.AssetViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NammashaleinventeryTheme {
                InventoryApp()
            }
        }
    }
}

@Composable
fun InventoryApp() {
    val navController = rememberNavController()
    val viewModel: AssetViewModel = viewModel()

    NavHost(navController = navController, startDestination = Screen.Dashboard.route) {
        composable(Screen.Dashboard.route) {
            DashboardScreen(viewModel) { route ->
                navController.navigate(route)
            }
        }
        composable(Screen.AssetList.route) {
            AssetListScreen(
                viewModel = viewModel,
                onAssetClick = { /* assetId -> navController.navigate(Screen.AssetDetail.createRoute(assetId)) */ },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.AssetRegistration.route) {
            AssetRegistrationScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.HealthCheck.route) {
            HealthCheckScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.RepairRequest.route) {
            RepairRequestScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.IssueLog.route) {
            IssueLogScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.SummaryReport.route) {
            SummaryReportScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
