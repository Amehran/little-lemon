package com.arminmehran.little_lemmon_app_capstone.ui.composables

import android.content.Context
import android.content.SharedPreferences
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavHostController
import com.arminmehran.little_lemmon_app_capstone.MainViewModel
import com.arminmehran.little_lemmon_app_capstone.R
import com.arminmehran.little_lemmon_app_capstone.helpers.validateRegData
import com.arminmehran.little_lemmon_app_capstone.navigation.Screen.Home
import com.arminmehran.little_lemmon_app_capstone.navigation.Screen.Onboarding
import com.arminmehran.little_lemmon_app_capstone.ui.theme.PrimaryGreen

@Composable
fun Onboarding(context: Context, navController: NavHostController, viewModel: MainViewModel) {

    val sharedPreferences = context.getSharedPreferences(stringResource(R.string.little_lemon), Context.MODE_PRIVATE)
    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val imeState = rememberImeState()
    val scrollState = rememberScrollState()


    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value) { scrollState.scrollTo(scrollState.maxValue) }
    }
    Scaffold(
        topBar = {
            Header2(navController = navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(40.dp)
        ) {
            UpperPannel(firstName, lastName, email)
            LowerPannel(firstName, lastName, email, sharedPreferences, context, navController)
        }
    }
}

@Composable
private fun LowerPannel(
    firstName: MutableState<String>,
    lastName: MutableState<String>,
    email: MutableState<String>,
    sharedPreferences: SharedPreferences,
    context: Context,
    navController: NavHostController
) {
    Button(
        onClick = {
            if (validateRegData(
                    firstName.value,
                    lastName.value,
                    email.value
                )
            ) {
                sharedPreferences.edit()
                    .putString("firstName", firstName.value)
                    .putString("lastName", lastName.value)
                    .putString("email", email.value)
                    .putBoolean("userRegistered", true)
                    .apply()

                Toast.makeText(
                    context,
                    context.getString(R.string.registration_successful),
                    Toast.LENGTH_SHORT
                )
                    .show()

                navController.navigate(Home.route) {
                    popUpTo(Onboarding.route) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }

            } else {
                Toast.makeText(
                    context,
                    context.getString(R.string.invalid_details_please_try_again),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

        },
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Text(text = stringResource(R.string.register))
    }
}

@Composable
private fun UpperPannel(
    firstName: MutableState<String>,
    lastName: MutableState<String>,
    email: MutableState<String>
) {
    Row(
        modifier = Modifier
            .height(150.dp)
            .background(PrimaryGreen)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.let_s_get_to_know_you),
            style = MaterialTheme.typography.headlineMedium,
            color = Color.LightGray,

            )
    }

    Text(
        text = stringResource(R.string.personal_information),
        textAlign = TextAlign.Start,
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.titleMedium
    )
    OutlinedTextField(
        value = firstName.value,
        onValueChange = {
            firstName.value = it
        },
        label = { Text(text = stringResource(R.string.first_name)) },
        singleLine = true,
        placeholder = { Text(text = stringResource(R.string.tilly)) },
        modifier = Modifier.fillMaxWidth()
    )

    OutlinedTextField(
        value = lastName.value,
        onValueChange = {
            lastName.value = it
        },
        label = { Text(text = stringResource(R.string.last_name)) },
        singleLine = true,
        placeholder = { Text(text = stringResource(R.string.doe)) },
        modifier = Modifier.fillMaxWidth()
    )

    OutlinedTextField(
        value = email.value,
        onValueChange = {
            email.value = it
        },
        label = { Text(text = stringResource(R.string.email)) },
        singleLine = true,
        placeholder = { Text(text = stringResource(R.string.johndoe_gmail_com)) },
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.size(40.dp))
}


@Composable
fun rememberImeState(): MutableState<Boolean> {
    val imeState = remember {
        mutableStateOf(false)
    }

    val view = LocalView.current
    DisposableEffect(view) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            val isKeyboardOpen = ViewCompat.getRootWindowInsets(view)
                ?.isVisible(WindowInsetsCompat.Type.ime()) ?: true
            imeState.value = isKeyboardOpen
        }

        view.viewTreeObserver.addOnGlobalLayoutListener(listener)
        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }
    return imeState
}