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
import androidx.navigation.NavController
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectDetailScreen(viewModel: ViewModel, navController: NavController, projectId: String) {
    val state = rememberPullToRefreshState { true }
    val projectResource = viewModel.getProjectResource.observeAsState()
    val context = LocalContext.current

    viewModel.getProject(projectId)

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenContent(
    projectResource: State<Resource<Project>?>,
    navController: NavController,
    viewModel: ViewModel,
    state: PullToRefreshState,
    paddingValues: PaddingValues,
    context: Context,
    projectId: String
) {
    HandleRefresh(state, projectId, viewModel)

    Box(
        Modifier
            .fillMaxSize()
            .padding(paddingValues = paddingValues)
            .nestedScroll(state.nestedScrollConnection)
    ) {
        when (val project = projectResource.value) {
            is Resource.Loading -> LoadingIndicator()

            is Resource.Success -> project.data?.let { projectData ->
                if (!state.isRefreshing) {
                    ProjectDetail(projectData, context)
                }
            }

            is Resource.Error -> navController.navigate(Screen.Error.createRoute("${project.message}"))
            else -> navController.navigate(Screen.Error.createRoute(stringResource(id = R.string.unknown_error)))
        }

        PullToRefreshContainer(
            modifier = Modifier.align(Alignment.TopCenter),
            state = state,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectDetailTopBar(navController: NavController) {
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

@Preview(showBackground = true)
@Composable
fun ProjectDetailScreenPreview() {
    CapstoneTheme {
        ProjectDetailScreen(viewModel = viewModel(), navController = rememberNavController(), "1")
    }
}
