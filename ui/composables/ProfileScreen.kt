package com.arminmehran.little_lemmon_app_capstone.ui.composables

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.arminmehran.little_lemmon_app_capstone.MainViewModel
import com.arminmehran.little_lemmon_app_capstone.R
import com.arminmehran.little_lemmon_app_capstone.ui.theme.PrimaryGreen

@Composable
fun GreetingProfile(context: Context, navController: NavHostController, viewModel: MainViewModel) {
    val sharedPreferences = context.getSharedPreferences("Little Lemon", Context.MODE_PRIVATE)
    val firstName = remember {
        mutableStateOf(sharedPreferences.getString("firstName", ""))
    }

    val lastName = remember {
        mutableStateOf(sharedPreferences.getString("lastName", ""))
    }

    val email = remember {
        mutableStateOf(sharedPreferences.getString("email", ""))
    }

    val scrollState = rememberScrollState()


    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row(Modifier.fillMaxWidth(0.6f)) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Little Lemon Logo"
            )
        }

        Text(
            text = "Personal Information",
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleSmall
        )
        OutlinedTextField(
            enabled = false,
            readOnly = true,
            value = firstName.value!!,
            onValueChange = {},
            label = { Text(text = "First Name") },
            singleLine = true,
            placeholder = { Text(text = "John") },
            colors = TextFieldDefaults.colors( PrimaryGreen, PrimaryGreen),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            enabled = false,
            readOnly = true,
            value = lastName.value!!,
            onValueChange = {},
            label = { Text(text = "Last Name") },
            singleLine = true,
            placeholder = { Text(text = "Doe") },
            colors = TextFieldDefaults.colors(PrimaryGreen, PrimaryGreen),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            enabled = false,
            readOnly = true,
            value = email.value!!,
            onValueChange = {},
            label = { Text(text = "Email") },
            singleLine = true,
            placeholder = { Text(text = "johndoe@gmail.com") },
            colors = TextFieldDefaults.colors(PrimaryGreen, PrimaryGreen),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.size(40.dp))

        Button(
            onClick = {
                sharedPreferences.edit()
                    .clear()
                    .apply()

                navController.navigate(com.arminmehran.little_lemmon_app_capstone.navigation.Onboarding.route) {
                    popUpTo(com.arminmehran.little_lemmon_app_capstone.navigation.Home.route) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }

            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Log Out")
        }
    }
}
