package com.arminmehran.little_lemmon_app_capstone.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.arminmehran.little_lemmon_app_capstone.MainViewModel
import com.arminmehran.little_lemmon_app_capstone.ui.composables.Home

import com.arminmehran.little_lemmon_app_capstone.ui.composables.GreetingProfile
import com.arminmehran.little_lemmon_app_capstone.ui.composables.Onboarding

@Composable
fun NavigationComposable(
    context: Context,
    navController: NavHostController,
    viewModel: MainViewModel
) {

    val sharedPreferences = context.getSharedPreferences("Little Lemon", Context.MODE_PRIVATE)
    var startDestination = Onboarding.route

    if (sharedPreferences.getBoolean("userRegistered", false)) {
        startDestination = Onboarding.route
    }

    NavHost(navController = navController, startDestination = Onboarding.route){

        composable( Onboarding.route.toString()){
//            val viewModel = hiltViewModel<MyViewModel>()
            Onboarding( context, navController, viewModel)
        }
        composable( Home.route.toString()){
//            val viewModel = hiltViewModel<MyViewModel>()
            Home( navController, viewModel)
        }
        composable( Profile.route.toString()){
//            val viewModel = hiltViewModel<MyViewModel>()
            GreetingProfile(context, navController, viewModel)
        }

    }
}