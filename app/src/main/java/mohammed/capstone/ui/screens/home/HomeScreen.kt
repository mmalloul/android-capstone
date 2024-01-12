package mohammed.capstone.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import mohammed.capstone.R
import mohammed.capstone.ui.screens.home.partials.ContactMeSection
import mohammed.capstone.ui.screens.home.partials.FeaturedProjectsSection
import mohammed.capstone.ui.screens.home.partials.IntroductionSection
import mohammed.capstone.ui.theme.CapstoneTheme
import mohammed.capstone.utils.Utils
import mohammed.capstone.viewmodel.ViewModel

/**
 * Composable function to display the home screen of the application.
 * This screen includes a header image, an introduction section,
 * a section for featured projects, and a contact me section.
 *
 * @param navController Navigation controller for handling navigation events.
 * @param viewModel ViewModel associated with this screen for managing data.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: ViewModel) {
    val focusManager = LocalFocusManager.current
    val state = rememberPullToRefreshState { true }
    val projectsResource = viewModel.getAllProjectsResource.observeAsState()

    // Handle the pull to refresh logic.
    if (state.isRefreshing) {
        LaunchedEffect(true) {
            viewModel.getAllProjects()
            delay(Utils.REFRESH_TIME)
            state.endRefresh()
        }
    }

    // Main layout of the home screen.
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        content = { innerPadding ->
            // Box layout allowing nested scroll for pull-to-refresh.
            Box(
                Modifier
                    .fillMaxSize()
                    .nestedScroll(state.nestedScrollConnection)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        HeaderImage()
                        IntroductionSection()
                    }

                    if (!state.isRefreshing) {
                        item {
                            FeaturedProjectsSection(projectsResource, navController, viewModel)
                        }

                        item {
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

/**
 * Composable function to display a header image on the home screen.
 */
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
}


/**
 * Preview of the HomeScreen composable function.
 * This preview is used for development purposes in Android Studio.
 */
@Preview
@Composable
fun HomeScreenPreview() {
    CapstoneTheme {
        HomeScreen(navController = rememberNavController(), viewModel())
    }
}