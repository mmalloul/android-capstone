package mohammed.capstone.ui.screens.about.partials

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import mohammed.capstone.R
import mohammed.capstone.data.models.AboutMeInfo

/**
 * Composable function to display a profile card with user information.
 * This includes a profile picture, name, occupation, and a short bio.
 *
 * @param aboutMeInfo Data model containing the information to be displayed.
 */
@Composable
fun ProfileCard(aboutMeInfo: AboutMeInfo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(

            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfilePicture(aboutMeInfo.profileImageRes)
            Spacer(modifier = Modifier.height(16.dp))
            NameAndOccupation(aboutMeInfo.name, aboutMeInfo.occupation)
            Spacer(modifier = Modifier.height(8.dp))
            Bio(aboutMeInfo.bio)
        }
    }
}

/**
 * Composable function to display the profile picture.
 *
 * @param profileImageRes Resource ID for the profile image.
 */
@Composable
fun ProfilePicture(profileImageRes: Int) {
    Image(
        painter = painterResource(id = profileImageRes),
        contentDescription = stringResource(id = R.string.profile_picture_content_description),
        modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    )
}

/**
 * Composable function to display the user's name and occupation.
 *
 * @param name The user's name.
 * @param occupation The user's occupation.
 */
@Composable
fun NameAndOccupation(name: String, occupation: String) {
    Text(
        text = name,
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.onSurface
    )
    Text(
        text = occupation,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

/**
 * Composable function to display a short bio of the user.
 *
 * @param bio The user's bio.
 */
@Composable
fun Bio(bio: String) {
    Text(
        text = bio,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurface,
        textAlign = TextAlign.Center
    )
}
