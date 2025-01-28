package com.arminmehran.little_lemmon_app_capstone.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.arminmehran.little_lemmon_app_capstone.MainViewModel
import com.arminmehran.little_lemmon_app_capstone.ui.composables.GreetingProfile
import com.arminmehran.little_lemmon_app_capstone.ui.composables.Home
import com.arminmehran.little_lemmon_app_capstone.ui.composables.Onboarding

@Composable
fun NavigationComposable(
    context: Context,
    navController: NavHostController,
    viewModel: MainViewModel
) {

    val sharedPreferences = context.getSharedPreferences("Little Lemon", Context.MODE_PRIVATE)
    var startDestination = Screen.Onboarding.route

    if (sharedPreferences.getBoolean("userRegistered", false)) {
        startDestination = Screen.Home.route
    }

    NavHost(navController = navController, startDestination = startDestination) {

        composable(Screen.Onboarding.route) {
            Onboarding(context, navController, viewModel)
        }
        composable(Screen.Home.route) {
            Home(navController, viewModel)
        }
        composable(Screen.Profile.route) {
            GreetingProfile(context, navController, viewModel)
        }

    }
}