package mohammed.capstone.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

@Composable
fun CapstoneTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}

private val DarkColorScheme = darkColorScheme(
    primary = ActiveBlue,
    onPrimary = TextWhite,
    primaryContainer = DeepBlueGray,
    onPrimaryContainer = TextWhite,
    secondary = LightBlueGray,
    onSecondary = TextWhite,
    secondaryContainer = DeepBlueGray,
    onSecondaryContainer = TextWhite,
    tertiary = ActiveBlue,
    onTertiary = TextWhite,
    tertiaryContainer = DeepBlueGray,
    onTertiaryContainer = TextWhite,
    error = ErrorColor,
    onError = TextWhite,
    background = DeepBlueGray,
    onBackground = TextWhite,
    surface = LightBlueGray,
    onSurface = TextWhite,
    surfaceVariant = DeepBlueGray,
    onSurfaceVariant = IconGray,
    outline = IconGray
)