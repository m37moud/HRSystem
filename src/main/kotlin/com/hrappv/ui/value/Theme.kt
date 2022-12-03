package com.hrappv.ui.value

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Color set
val LightTheme = lightColors(
    primary = md_theme_light_primary,
    onPrimary =  Color.Black,
    secondary = md_theme_light_secondary,
    onSecondary = Color.White,
    error = md_theme_light_error,
    onError = md_theme_light_onError,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
) // TODO :

val DarkTheme = darkColors(
    primary = R.color.PictonBlue,
    onPrimary = Color.White,
    secondary = R.color.Elephant,
    onSecondary = Color.Black,
    surface = R.color.BigStone,
    error = R.color.WildWatermelon
)

@Composable
fun HrAppVTheme(
    isDark: Boolean = true, // TODO: If you want to support both light theme and dark theme, you'll need to implement it manually.
    content: @Composable ColumnScope.() -> Unit,
) {
    MaterialTheme(
        colors = if (isDark) DarkTheme else LightTheme,
        typography = HrAppVTypography
    ) {
        Surface {
            Column {
                content()
            }
        }
    }
}