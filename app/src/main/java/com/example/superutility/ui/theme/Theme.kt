package com.example.superutility.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColors = lightColors(
    primary = androidx.compose.ui.graphics.Color(0xFF0D47A1),
    primaryVariant = androidx.compose.ui.graphics.Color(0xFF1565C0),
    secondary = androidx.compose.ui.graphics.Color(0xFF90CAF9)
)

private val DarkColors = darkColors(
    primary = androidx.compose.ui.graphics.Color(0xFF90CAF9)
)

@Composable
fun SuperUtilityTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = LightColors,
        typography = androidx.compose.material.Typography(),
        shapes = androidx.compose.material.Shapes(),
        content = content
    )
}
