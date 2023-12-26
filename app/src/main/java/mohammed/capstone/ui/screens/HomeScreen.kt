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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import mohammed.capstone.R
import mohammed.capstone.ui.theme.CapstoneTheme
import mohammed.capstone.viewmodel.ViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: ViewModel) {
    val focusManager = LocalFocusManager.current
    val state = rememberPullToRefreshState { true }

    if (state.isRefreshing) {
        LaunchedEffect(true) {
            viewModel.getAllProjects()
            delay(1500)
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
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(16.dp)
                ) {
                    item {
                        HeaderImage()
                        IntroductionSection()
                        if (!state.isRefreshing) {
                            FeaturedProjectsSection(navController, viewModel)
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
        painter = painterResource(id = R.drawable.company_logo_transparant),
        contentDescription = stringResource(id = R.string.header_image_description),
        modifier = Modifier
            .fillMaxSize()
            .height(200.dp)
            .clip(shape = RoundedCornerShape(bottomEnd = 24.dp, bottomStart = 24.dp)),
        contentScale = ContentScale.Crop,
        alignment = Alignment.Center
    )
    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
private fun IntroductionSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.home_introduction_greeting),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = stringResource(id = R.string.home_introduction_occupation),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun FeaturedProjectsSection(navController: NavHostController, viewModel: ViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.home_featured_projects_title),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        FeaturedProjectCard(
            title = stringResource(
                id = R.string.home_project_card_title_project_1
            ),
            description = stringResource(
                id = R.string.home_project_card_description_project_1
            ),
            navController = navController,
            viewModel = viewModel
        )
        Spacer(modifier = Modifier.height(8.dp))
        FeaturedProjectCard(
            title = stringResource(
                id = R.string.home_project_card_title_project_2
            ),
            description = stringResource(
                id = R.string.home_project_card_description_project_2
            ),
            navController = navController,
            viewModel = viewModel
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun ContactMeSection(focusManager: FocusManager) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.home_contact_me_title),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))

        var message by remember { mutableStateOf("") }

        OutlinedTextField(
            value = message,
            onValueChange = { message = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(stringResource(id = R.string.home_contact_me_placeholder)) },
            textStyle = MaterialTheme.typography.bodyLarge,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions(onSend = {
                focusManager.clearFocus()
            }),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
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
            Icon(
                imageVector = Icons.Filled.Email,
                contentDescription = stringResource(id = R.string.home_send_message_button)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = stringResource(id = R.string.home_send_message_button))
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun FeaturedProjectCard(
    title: String,
    description: String,
    navController: NavHostController,
    viewModel: ViewModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                viewModel.setProjectNull()
                navController.navigate(Screen.ProjectDetail.createRoute("1"))
            },
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
        HomeScreen(navController = rememberNavController(), viewModel())
    }
}