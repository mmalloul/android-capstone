package mohammed.capstone.utils

import android.content.Context
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri

object Utils {
    fun openCustomTab(context: Context, url: String) {
        try {
            val customTabsIntent = CustomTabsIntent.Builder().build()
            customTabsIntent.launchUrl(context, url.toUri())
        } catch (e: Exception) {
            // Handle exceptions such as malformed URLs
        }
    }
}
