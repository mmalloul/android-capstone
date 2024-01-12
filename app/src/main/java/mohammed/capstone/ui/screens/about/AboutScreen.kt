package mohammed.capstone.ui.screens.about

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import mohammed.capstone.R
import mohammed.capstone.data.models.AboutMeInfo
import mohammed.capstone.data.models.SocialLink
import mohammed.capstone.ui.screens.about.partials.ProfileCard
import mohammed.capstone.ui.screens.about.partials.SkillsSection
import mohammed.capstone.ui.screens.about.partials.SocialLinksSection
import mohammed.capstone.ui.theme.CapstoneTheme

/**
 * Composable function to display the 'About' screen in the application.
 * This screen showcases personal and professional information such as
 * name, occupation, bio, and also includes skills and social links.
 *
 * @param navController Navigation controller for handling navigation events.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavController) {
    // Initialize dummy data for the 'About Me' section.
    // This includes name, occupation, a short bio, and a profile image.
    val aboutMeInfo = AboutMeInfo(
        name = stringResource(id = R.string.about_name),
        occupation = stringResource(id = R.string.about_occupation),
        bio = stringResource(id = R.string.about_bio),
        profileImageRes = R.drawable.company_logo_small
    )

    // List of skills to be displayed in the 'Skills' section.
    val skills = listOf("Kotlin", "Java", "Laravel", "Svelte", "UI/UX Design")

    // Social link details for LinkedIn, GitHub, and Email.
    // Each link includes a name, URL, and an icon.
    val socialLinks = listOf(
        SocialLink(
            name = "LinkedIn",
            url = "https://www.linkedin.com/in/mohammedmalloul",
            icon = painterResource(id = R.drawable.linkedin)
        ),
        SocialLink(
            name = "GitHub",
            url = "https://github.com/Mohmlll",
            icon = painterResource(id = R.drawable.github)
        ),
        SocialLink(
            name = "Email",
            url = "mailto:mohammed.malloul@hotmail.com",
            icon = painterResource(id = R.drawable.email)
        )
    )

    // State for the pull-to-refresh functionality.
    val state = rememberPullToRefreshState()

    // Surface container for the entire 'About' screen layout.
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        // Box layout allowing nested scroll for pull-to-refresh.
        Box(
            modifier = Modifier
                .nestedScroll(state.nestedScrollConnection)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Profile, Skills, and Social Links sections are only displayed if not refreshing.
                if (!state.isRefreshing) {
                    item { ProfileCard(aboutMeInfo) }
                    item { SkillsSection(skills) }
                    item { SocialLinksSection(navController, socialLinks) }
                }
            }

            // Pull to refresh container at the top of the screen.
            PullToRefreshContainer(
                modifier = Modifier.align(Alignment.TopCenter),
                state = state,
            )
        }
    }
}

/**
 * Preview of the AboutScreen composable function.
 * This preview is used for development purposes in Android Studio.
 */
@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    CapstoneTheme {
        AboutScreen(navController = rememberNavController())
    }
}
