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
import mohammed.capstone.R
import mohammed.capstone.data.models.AboutMeInfo
import mohammed.capstone.data.models.SocialLink
import mohammed.capstone.ui.screens.about.partials.ProfileCard
import mohammed.capstone.ui.screens.about.partials.SkillsSection
import mohammed.capstone.ui.screens.about.partials.SocialLinksSection
import mohammed.capstone.ui.theme.CapstoneTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen() {
    // Temporary dummy data
    val aboutMeInfo = AboutMeInfo(
        name = stringResource(id = R.string.about_name),
        occupation = stringResource(id = R.string.about_occupation),
        bio = stringResource(id = R.string.about_bio),
        profileImageRes = R.drawable.company_logo_small
    )

    // Skills list
    val skills = listOf("Kotlin", "Java", "Laravel", "Svelte", "UI/UX Design")

    // SocialLinks objects
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

    val state = rememberPullToRefreshState()

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

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    CapstoneTheme {
        AboutScreen()
    }
}
