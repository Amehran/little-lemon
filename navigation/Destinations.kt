package com.arminmehran.little_lemmon_app_capstone.navigation
//
//sealed class Screen(val route: String) {
//    object Home : Screen("home")
//    object Profile : Screen("profile")
//    object Onboarding : Screen("onboarding")
//}

interface Destinations{
    val route: String
}

object Onboarding: Destinations{
    override val route = "Onboarding"
}

object Home: Destinations{
    override val route = "Home"
}

object Profile: Destinations{
    override val route = "Profile"
}