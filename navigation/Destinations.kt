package com.arminmehran.little_lemmon_app_capstone.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Profile : Screen("profile")
    object Onboarding : Screen("onboarding")
}
