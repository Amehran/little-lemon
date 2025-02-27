package com.arminmehran.little_lemmon_app_capstone.ui.theme
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColorScheme(
    primary = PrimaryGreen,
    onSurfaceVariant = Purple700,
    secondary = Secondary2,
    onPrimary = Color.White
)

private val LightColorPalette = lightColorScheme(
    primary = PrimaryYellow,
    onSurfaceVariant = Purple700,
    secondary = Secondary1,
    onPrimary = Color.Black
)


@Composable
fun LittleLemonTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes ,
        content = content
    )
}