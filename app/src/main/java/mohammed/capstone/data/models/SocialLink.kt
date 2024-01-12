package mohammed.capstone.data.models

import androidx.compose.ui.graphics.painter.Painter

/**
 * Data class representing a social media link.
 * This class is used to hold information about social media profiles or links.
 *
 * @property name The name of the social media platform or link title.
 * @property url URL to the specific social media profile or page.
 * @property icon A painter object for the icon representing the social media platform.
 */
data class SocialLink(
    val name: String,
    val url: String,
    val icon: Painter
)