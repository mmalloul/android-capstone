package mohammed.capstone.ui.screens.projectDetail

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import mohammed.capstone.R
import mohammed.capstone.data.api.util.Resource
import mohammed.capstone.data.models.Project
import mohammed.capstone.ui.screens.Screen
import mohammed.capstone.ui.screens.projectDetail.partials.ProjectDetail
import mohammed.capstone.ui.theme.CapstoneTheme
import mohammed.capstone.utils.Utils
import mohammed.capstone.utils.Utils.LoadingIndicator
import mohammed.capstone.viewmodel.ViewModel

/**
 * Composable function to display the project detail screen.
 * This screen shows detailed information about a specific project.
 *
 * @param viewModel ViewModel for managing project-related data.
 * @param navController NavHostController for navigation actions.
 * @param projectId The ID of the project to display.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectDetailScreen(viewModel: ViewModel, navController: NavHostController, projectId: String) {
    // State for pull-to-refresh functionality.
    val state = rememberPullToRefreshState { true }
    // Observing the project resource state.
    val projectResource = viewModel.getProjectResource.observeAsState()
    // Local context from the Compose environment.
    val context = LocalContext.current

    // Fetch the project details.
    viewModel.getProject(projectId)

    // Scaffold layout for the screen structure.
    Scaffold(
        topBar = { ProjectDetailTopBar(navController) },
        containerColor = MaterialTheme.colorScheme.background,
        content = { paddingValues ->
            ScreenContent(
                projectResource = projectResource,
                navController = navController,
                viewModel = viewModel,
                state = state,
                paddingValues = paddingValues,
                context = context,
                projectId = projectId
            )
        }
    )
}

/**
 * Composable function to display the main content of the project detail screen.
 * Handles different states of the project resource (loading, success, error).
 *
 * @param projectResource State holder for project data wrapped in a Resource.
 * @param navController NavHostController for navigation actions.
 * @param viewModel ViewModel for managing project-related data.
 * @param state PullToRefreshState for the pull-to-refresh functionality.
 * @param paddingValues PaddingValues for layout padding.
 * @param context Android Context for certain operations.
 * @param projectId The ID of the project.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenContent(
    projectResource: State<Resource<Project>?>,
    navController: NavHostController,
    viewModel: ViewModel,
    state: PullToRefreshState,
    paddingValues: PaddingValues,
    context: Context,
    projectId: String
) {
    // Handling the pull-to-refresh logic.
    HandleRefresh(state, projectId, viewModel)
    // Box layout allowing nested scroll for pull-to-refresh.
    Box(
        Modifier
            .fillMaxSize()
            .padding(paddingValues = paddingValues)
            .nestedScroll(state.nestedScrollConnection)
    ) {
        // Handling different states of the project resource.
        when (val project = projectResource.value) {
            is Resource.Loading -> LoadingIndicator()

            is Resource.Success -> project.data?.let { projectData ->
                if (!state.isRefreshing) {
                    ProjectDetail(projectData, context, navController)
                }
            }

            is Resource.Error -> navController.navigate(Screen.Error.createRoute("${project.message}"))
            else -> navController.navigate(Screen.Error.createRoute(stringResource(id = R.string.unknown_error)))
        }

        // Pull to refresh container at the top of the screen.
        PullToRefreshContainer(
            modifier = Modifier.align(Alignment.TopCenter),
            state = state,
        )
    }
}

/**
 * Composable function for the top bar of the project detail screen.
 *
 * @param navController NavController for navigation actions.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectDetailTopBar(navController: NavHostController) {
    TopAppBar(
        title = { Text(stringResource(id = R.string.project_detail_title)) },
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.back_btn)
                )
            }
        }
    )
}

/**
 * Composable function to handle the refresh action for the project details.
 *
 * @param state PullToRefreshState for the pull-to-refresh functionality.
 * @param projectId The ID of the project to fetch.
 * @param viewModel ViewModel for managing project-related data.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HandleRefresh(
    state: PullToRefreshState,
    projectId: String,
    viewModel: ViewModel
) {
    if (state.isRefreshing) {
        LaunchedEffect(true) {
            viewModel.getProject(projectId)
            delay(Utils.REFRESH_TIME)
            state.endRefresh()
        }
    }
}

/**
 * Preview of the ProjectDetailScreen composable function.
 * This preview is used for development purposes in Android Studio.
 */
@Preview
@Composable
fun ProjectDetailScreenPreview() {
    CapstoneTheme {
        ProjectDetailScreen(viewModel = viewModel(), navController = rememberNavController(), "1")
    }
}
