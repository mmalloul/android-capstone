package mohammed.capstone.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import mohammed.capstone.R
import mohammed.capstone.ui.theme.CapstoneTheme

data class AboutMeInfo(
    val name: String,
    val occupation: String,
    val bio: String,
    val profileImageRes: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen() {
    val aboutMeInfo = AboutMeInfo(
        name = "Mohammed Malloul",
        occupation = "Software Engineer",
        bio = "Passionate about coding and building cool apps!",
        profileImageRes = R.drawable.ic_launcher_foreground
    )
    val skills = listOf("Kotlin", "Java", "Flutter", "React Native", "UI/UX Design")
    val socialLinks = listOf(
        SocialLink("LinkedIn", "https://linkedin.com/in/johndoe", Icons.Rounded.Email),
        SocialLink("GitHub", "https://github.com/johndoe", Icons.Rounded.Email),
        SocialLink("Email", "mailto:johndoe@example.com", Icons.Rounded.Email)
    )
    val state = rememberPullToRefreshState()
    if (state.isRefreshing) {
        LaunchedEffect(true) {
            delay(1500)
            // TODO: Refresh state
            state.endRefresh()
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier
                .nestedScroll(state.nestedScrollConnection)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (!state.isRefreshing) {
                    item { ProfileCard(aboutMeInfo) }
                    item { Spacer(Modifier.height(32.dp)) }
                    item { SkillsSection(skills) }
                    item { Spacer(Modifier.height(32.dp)) }
                    item { SocialLinksSection(socialLinks) }
                }
            }
            PullToRefreshContainer(
                modifier = Modifier.align(Alignment.TopCenter),
                state = state,
            )
        }
    }
}

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

@Composable
fun ProfilePicture(profileImageRes: Int) {
    Image(
        painter = painterResource(id = profileImageRes),
        contentDescription = "Profile Picture",
        modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    )
}

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

@Composable
fun Bio(bio: String) {
    Text(
        text = bio,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurface,
        textAlign = TextAlign.Center
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SkillsSection(skills: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Skills",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(16.dp))

        FlowRow(
            horizontalArrangement = Arrangement.Start,
            verticalArrangement = Arrangement.Top,
            maxItemsInEachRow = 4,
            modifier = Modifier.fillMaxWidth()
        ) {
            skills.forEach { skill ->
                SkillChip(skill)
            }
        }
    }
}
@Composable
fun SkillChip(skill: String) {
    Chip(
        label = { Text(skill) },
        onClick = { /* Handle skill click */ },
        modifier = Modifier
    )
}


@Composable
fun Chip(
    label: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedButton(
        onClick = onClick,
        modifier = modifier
    ) {
        label()
    }
}

@Composable
fun SocialLinksSection(socialLinks: List<SocialLink>) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Find me on",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))
        socialLinks.forEach { socialLink ->
            SocialLinkButton(
                name = socialLink.name,
                icon = socialLink.icon,
                onClick = { openUrl(context, socialLink.url) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun SocialLinkButton(name: String, icon: ImageVector, onClick: () -> Unit) {
    ElevatedButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(icon, contentDescription = "$name icon")
        Spacer(Modifier.width(8.dp))
        Text(name)
    }
}

private fun openUrl(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}

data class SocialLink(val name: String, val url: String, val icon: ImageVector)

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    CapstoneTheme {
        AboutScreen()
    }
}
