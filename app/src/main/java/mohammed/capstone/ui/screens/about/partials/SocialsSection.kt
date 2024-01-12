package mohammed.capstone.ui.screens.about.partials

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import mohammed.capstone.data.models.SocialLink
import mohammed.capstone.R
import mohammed.capstone.utils.Utils

/**
 * Composable function to display a section with social links.
 * Each link is represented as a button with an icon and name.
 *
 * @param navController Navigation controller for handling navigation events.
 * @param socialLinks A list of SocialLink objects containing information about each social link.
 */
@Composable
fun SocialLinksSection(navController: NavController, socialLinks: List<SocialLink>) {
    // Context from the local environment, used for handling link actions.
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(id = R.string.social_links_title),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Iterate through each social link and create a button.
        socialLinks.forEach { socialLink ->
            SocialLinkButton(
                name = socialLink.name,
                icon = socialLink.icon

            ) {
                // Action to perform on button click, opening the link in a custom tab.
                Utils.openCustomTab(navController, context, socialLink.url)
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

/**
 * Composable function to create a button for a social link.
 * The button includes an icon, the name of the link, and an action to be performed on click.
 *
 * @param name The name of the social link.
 * @param icon The icon representing the social link.
 * @param onClick Lambda function to handle click actions on the button.
 */
@Composable
fun SocialLinkButton(name: String, icon: Painter, onClick: () -> Unit) {
    ElevatedButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(icon, contentDescription = "$name ${stringResource(id = R.string.icon)}")
        Spacer(Modifier.width(8.dp))
        Text(name)
    }
}