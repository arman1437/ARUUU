package com.aruuu.app.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aruuu.app.ui.screens.auth.AuthScreen
import com.aruuu.app.ui.screens.home.HomeScreen
import com.aruuu.app.ui.screens.onboarding.OnboardingScreen
import com.aruuu.app.ui.screens.apps.ManageAppsScreen
import com.aruuu.app.ui.screens.settings.SettingsScreen

object Route {
    const val ONBOARDING = "onboarding"
    const val HOME = "home"
    const val AUTH = "auth"
    const val APPS = "apps"
    const val SETTINGS = "settings"
}

@Composable
fun NavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Route.HOME) {
        composable(Route.HOME) {
            HomeScreen(navController)
        }
        composable(Route.AUTH) {
            AuthScreen(navController)
        }
        composable(Route.APPS) {
            ManageAppsScreen(navController)
        }
        composable(Route.SETTINGS) {
            SettingsScreen(navController)
        }
        composable(Route.ONBOARDING) {
            OnboardingScreen(navController)
        }
    }
}
