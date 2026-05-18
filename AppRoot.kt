package com.ksheerasagara.dairy.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import androidx.navigation.NavDestination.Companion.hierarchy
import com.ksheerasagara.dairy.ui.screens.*
import com.ksheerasagara.dairy.viewmodel.DairyViewModel

@Composable
fun AppRoot() {
    val vm: DairyViewModel = viewModel()
    val nav = rememberNavController()

    if (!vm.isLoggedIn.value) {
        LoginScreen(vm)
        return
    }

    val items = listOf(
        BottomItem("dashboard", "Home", Icons.Filled.Home),
        BottomItem("profit", "Profit", Icons.Filled.PieChart),
        BottomItem("cows", "Cows", Icons.Filled.Pets),
        BottomItem("monthly", "Monthly", Icons.Filled.BarChart),
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val current by nav.currentBackStackEntryAsState()
                val route = current?.destination?.route
                items.forEach { item ->
                    NavigationBarItem(
                        selected = route == item.route,
                        onClick = {
                            nav.navigate(item.route) {
                                popUpTo(nav.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(navController = nav, startDestination = "dashboard", modifier = Modifier.padding(padding)) {
            composable("dashboard") { DashboardScreen(vm) }
            composable("profit") { ProfitScreen(vm) }
            composable("cows") { CowAnalysisScreen(vm) }
            composable("monthly") { MonthlyScreen(vm) }
        }
    }
}

data class BottomItem(val route: String, val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)
