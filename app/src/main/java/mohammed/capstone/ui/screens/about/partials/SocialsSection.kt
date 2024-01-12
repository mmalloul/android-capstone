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
import mohammed.capstone.data.models.SocialLink
import mohammed.capstone.R
import mohammed.capstone.utils.Utils

@Composable
fun SocialLinksSection(socialLinks: List<SocialLink>) {
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
        socialLinks.forEach { socialLink ->
            SocialLinkButton(
                name = socialLink.name,
                icon = socialLink.icon
            ) { Utils.openCustomTab(context, socialLink.url) }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

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