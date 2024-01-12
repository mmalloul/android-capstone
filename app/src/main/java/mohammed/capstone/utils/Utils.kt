package mohammed.capstone.utils

import android.content.Context
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.net.toUri
import androidx.navigation.NavController
import mohammed.capstone.ui.screens.Screen

/**
 * Utility object containing common helper functions and constants used across the application.
 */
object Utils {
    // Constant value representing the refresh time delay in milliseconds.
    const val REFRESH_TIME: Long = 1500

    /**
     * Opens a given URL in a custom tab (a browser tab with a customized UI).
     *
     * @param navController Navigation controller for handling navigation events.
     * @param context The context used to start the custom tab activity.
     * @param url The URL to be opened in the custom tab.
     */
    fun openCustomTab(navController: NavController, context: Context, url: String) {
        try {
            // Building and launching a custom tab intent.
            val customTabsIntent = CustomTabsIntent.Builder().build()
            customTabsIntent.launchUrl(context, url.toUri())
        } catch (e: Exception) {
            navController.navigate(Screen.Error.createRoute("${e.message}"))
        }
    }

    /**
     * Composable function to display a loading indicator (progress circle).
     */
    @Composable
    fun LoadingIndicator() {
        // Box layout to center the progress indicator.
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
    }
}
