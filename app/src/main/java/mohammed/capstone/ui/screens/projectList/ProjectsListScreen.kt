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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectListScreen(viewModel: ViewModel, navController: NavHostController) {
    LaunchedEffect(Unit) {
        viewModel.getAllProjects()
    }

    val projectsResource = viewModel.getAllProjectsResource.observeAsState()
    val state = rememberPullToRefreshState()

    if (state.isRefreshing) {
        LaunchedEffect(Unit) {
            viewModel.getAllProjects()
            delay(Utils.REFRESH_TIME)
            state.endRefresh()
        }
    }

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

        PullToRefreshContainer(
            modifier = Modifier.align(Alignment.TopCenter),
            state = state,
        )
    }
}

@Composable
fun ScreenContent(navController: NavHostController, viewModel: ViewModel, projects: List<Project>) {
    LazyColumn {
        projects.forEach { project ->
            item {
                ProjectListItem(project, navController, viewModel)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProjectListScreenPreview() {
    CapstoneTheme {
        ProjectListScreen(viewModel = viewModel(), navController = rememberNavController())
    }
}
