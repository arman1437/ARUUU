package com.aruuu.app.ui.screens.onboarding

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
fun OnboardingScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Welcome to ARUUU")
        Spacer(modifier = Modifier.height(16.dp))
        Text("Your Privacy-First App Vault")
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { navController.navigate(Route.HOME) }
        ) {
            Text("Get Started")
        }
    }
}
