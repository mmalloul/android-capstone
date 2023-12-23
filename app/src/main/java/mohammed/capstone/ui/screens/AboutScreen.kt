package mohammed.capstone.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import mohammed.capstone.R
import mohammed.capstone.ui.theme.CapstoneTheme

data class AboutMeInfo(
    val name: String,
    val occupation: String,
    val bio: String,
    val profileImageRes: Int
)

@Composable
fun AboutScreen(navController: NavHostController) {
    val aboutMeInfo = AboutMeInfo(
        name = "John Doe",
        occupation = "Software Engineer",
        bio = "Passionate about coding and building cool apps!",
        profileImageRes = R.drawable.ic_launcher_foreground
    )

    CapstoneTheme(darkTheme = true) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                ProfileCard(aboutMeInfo)
            }
        }
    }
}

@Composable
fun ProfileCard(aboutMeInfo: AboutMeInfo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = aboutMeInfo.profileImageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = aboutMeInfo.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = aboutMeInfo.occupation,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = aboutMeInfo.bio,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    CapstoneTheme(darkTheme = true) {
        AboutScreen(navController = rememberNavController())
    }
}
