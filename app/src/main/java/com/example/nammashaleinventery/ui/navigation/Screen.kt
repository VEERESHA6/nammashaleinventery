package com.example.nammashaleinventery.ui.navigation

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object AssetList : Screen("asset_list")
    object AssetRegistration : Screen("asset_registration")
    object HealthCheck : Screen("health_check")
    object IssueLog : Screen("issue_log")
    object RepairRequest : Screen("repair_request")
    object SummaryReport : Screen("summary_report")
    object AssetDetail : Screen("asset_detail/{assetId}") {
        fun createRoute(assetId: Int) = "asset_detail/$assetId"
    }
}
