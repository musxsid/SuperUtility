package com.example.superutility.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColorPalette = lightColors(
    primary = PrimaryBlue,
    primaryVariant = PrimaryLight,
    secondary = Accent,
    background = SurfaceWhite,
    surface = SurfaceWhite,
    onPrimary = OnPrimary
)

@Composable
fun SuperUtilityTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = LightColorPalette,
        typography = AppTypography,
        shapes = MaterialTheme.shapes,
        content = content
    )
}
