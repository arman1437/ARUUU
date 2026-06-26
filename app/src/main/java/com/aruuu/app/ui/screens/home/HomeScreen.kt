package com.aruuu.app.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.aruuu.app.ui.Route

@Composable
fun HomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text("ARUUU App Vault", modifier = Modifier.padding(top = 32.dp))
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { navController.navigate(Route.APPS) }
        ) {
            Text("Manage Apps")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate(Route.SETTINGS) }
        ) {
            Text("Settings")
        }
    }
}
