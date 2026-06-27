package com.weatherai.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF87CEEB),
    secondary = Color(0xFFFFA500),
    tertiary = Color(0xFF4A90E2),
    background = Color(0xFF1A1A2E),
    surface = Color(0xFF16213E),
    onBackground = Color(0xFFECF0F1),
    onSurface = Color(0xFFECF0F1)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF87CEEB),
    secondary = Color(0xFFFFA500),
    tertiary = Color(0xFF4A90E2),
    background = Color(0xFFF5F5F5),
    surface = Color(0xFFFFFFFF),
    onBackground = Color(0xFF1A1A2E),
    onSurface = Color(0xFF1A1A2E)
)

@Composable
fun WeatherAITheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view)?.isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
