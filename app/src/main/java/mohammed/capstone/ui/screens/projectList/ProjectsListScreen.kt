package mohammed.capstone.ui.screens.projectList

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import mohammed.capstone.R
import mohammed.capstone.data.api.util.Resource
import mohammed.capstone.data.models.Project
import mohammed.capstone.ui.screens.Screen
import mohammed.capstone.ui.screens.projectList.partials.ProjectListItem
import mohammed.capstone.ui.theme.CapstoneTheme
import mohammed.capstone.utils.Utils
import mohammed.capstone.viewmodel.ViewModel

/**
 * Composable function to display the project list screen.
 * This screen lists all projects and allows users to refresh the list.
 *
 * @param viewModel ViewModel associated with this screen for managing project data.
 * @param navController NavController for handling navigation events.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectListScreen(viewModel: ViewModel, navController: NavHostController) {
    // Fetch all projects initially.
    LaunchedEffect(Unit) {
        viewModel.getAllProjects()
    }

    // Observing the state of project list resource.
    val projectsResource = viewModel.getAllProjectsResource.observeAsState()
    // State for pull-to-refresh functionality.
    val state = rememberPullToRefreshState()

    // Refreshing the project list.
    if (state.isRefreshing) {
        LaunchedEffect(Unit) {
            viewModel.getAllProjects()
            delay(Utils.REFRESH_TIME)
            state.endRefresh()
        }
    }

    // Main layout of the project list screen and allowing nested scroll for pull-to-refresh.
    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(state.nestedScrollConnection)
    ) {
        Column(modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp)) {
            Text(
                stringResource(id = R.string.project_list_title),
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Handling different states of project list resource.
            when (val projects = projectsResource.value) {
                is Resource.Loading -> Utils.LoadingIndicator()

                is Resource.Success -> {

                    if (!state.isRefreshing) {
                        projects.data?.let { ScreenContent(navController, viewModel, it) }

                    }
                }

                is Resource.Error -> navController.navigate(Screen.Error.createRoute("${projects.message}"))
                else -> navController.navigate(Screen.Error.createRoute(stringResource(id = R.string.unknown_error)))
            }
        }

        // Pull to refresh container at the top of the screen.
        PullToRefreshContainer(
            modifier = Modifier.align(Alignment.TopCenter),
            state = state,
        )
    }
}

/**
 * Composable function to display the content of the project list screen.
 * Lists each project as an item in a LazyColumn.
 *
 * @param navController NavController for handling navigation events.
 * @param viewModel ViewModel for managing project-related data.
 * @param projects List of projects to be displayed.
 */
@Composable
fun ScreenContent(navController: NavHostController, viewModel: ViewModel, projects: List<Project>) {
    // Lazy column for efficiently displaying a list of project items.
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        projects.forEach { project ->
            item {
                ProjectListItem(project, navController, viewModel)
            }
        }
    }
}

/**
 * Preview of the ProjectListScreen composable function.
 * This preview is used for development purposes in Android Studio.
 */
@Preview
@Composable
fun ProjectListScreenPreview() {
    CapstoneTheme {
        ProjectListScreen(viewModel = viewModel(), navController = rememberNavController())
    }
}
