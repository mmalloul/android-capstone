package mohammed.capstone.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import mohammed.capstone.R
import mohammed.capstone.ui.theme.CapstoneTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val focusManager = LocalFocusManager.current
    val state = rememberPullToRefreshState { true }

    if (state.isRefreshing) {
        LaunchedEffect(true) {
            delay(1500)
            // TODO: Refresh state
            state.endRefresh()
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        content = { innerPadding ->
            Box(
                Modifier
                    .fillMaxSize()
                    .nestedScroll(state.nestedScrollConnection)
            ) {
                LazyColumn(
                    modifier = Modifier.padding(innerPadding)
                ) {
                    item {
                        HeaderImage()
                        IntroductionSection()
                        if (!state.isRefreshing) {
                            FeaturedProjectsSection(navController)
                            ContactMeSection(focusManager)
                        }
                    }
                }
                PullToRefreshContainer(
                    modifier = Modifier.align(Alignment.TopCenter),
                    state = state,
                )
            }
        }
    )
}

@Composable
fun HeaderImage() {
    Image(
        painter = painterResource(id = R.drawable.ic_launcher_background),
        contentDescription = "Header Image",
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(shape = RoundedCornerShape(bottomEnd = 24.dp, bottomStart = 24.dp)),
        contentScale = ContentScale.Crop
    )
    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
private fun IntroductionSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hello, I'm Mohammed Malloul",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "Software Engineer | Mobile App Developer",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun FeaturedProjectsSection(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Featured Projects",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Repeat this for each project you want to feature
        FeaturedProjectCard(
            title = "Project 1",
            description = "Lorem ipsum dolor sit amet.",
            navController = navController
        )
        Spacer(modifier = Modifier.height(8.dp))
        FeaturedProjectCard(
            title = "Project 2",
            description = "Suspendisse non dui eget arcu.",
            navController = navController
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun ContactMeSection(focusManager: FocusManager) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Contact Me",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = "",
            onValueChange = { /* Handle text change */ },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp, max = 56.dp),
            textStyle = MaterialTheme.typography.bodyLarge,
            placeholder = { Text("Type your message") },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions(onSend = {
                focusManager.clearFocus()
            }),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* Handle send action */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Icon(imageVector = Icons.Filled.Email, contentDescription = "Send Email")
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Send Message")
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun FeaturedProjectCard(title: String, description: String, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate(Screen.ProjectDetail.createRoute("1")) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    CapstoneTheme {
        HomeScreen(navController = rememberNavController())
    }
}
