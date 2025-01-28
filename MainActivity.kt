package com.arminmehran.little_lemmon_app_capstone

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.arminmehran.little_lemmon_app_capstone.navigation.NavigationComposable
import com.arminmehran.little_lemmon_app_capstone.ui.theme.LittleLemonTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            LittleLemon(
                applicationContext,
                navController = navController,
                viewModel
            )
        }
    }
}

@Composable
fun LittleLemon(
    applicationContext: Context,
    navController: NavHostController,
    viewModel: MainViewModel
) {
    LittleLemonTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NavigationComposable(
                context = applicationContext,
                navController = navController,
                viewModel
            )
        }
    }
}
