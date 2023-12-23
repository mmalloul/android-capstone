package mohammed.capstone.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import mohammed.capstone.R
import mohammed.capstone.data.api.util.Resource
import mohammed.capstone.data.models.Project
import mohammed.capstone.ui.theme.CapstoneTheme
import mohammed.capstone.viewmodel.ViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectDetailScreen(viewModel: ViewModel, navController: NavController, projectId: String) {
    LaunchedEffect(projectId) {
        viewModel.getProject(projectId)
    }

    val context = LocalContext.current
    val state = rememberPullToRefreshState()

    if (state.isRefreshing) {
        LaunchedEffect(true) {
            delay(1500)
            // TODO: Refresh state
            state.endRefresh()
        }
    }

    // Observe project details LiveData
    val projectResource = viewModel.getProjectResource.observeAsState()

    Scaffold(
        topBar = { ProjectDetailTopBar(navController) },
        containerColor = MaterialTheme.colorScheme.background,
        content = { paddingValues ->
            when (val project = projectResource.value) {
                is Resource.Loading -> {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }
                }

                is Resource.Success -> {
                    project.data?.let { projectData ->
                        Box(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxSize()
                                .nestedScroll(state.nestedScrollConnection)
                        ) {
                            if (!state.isRefreshing) {
                                ProjectDetailContent(paddingValues, projectData, context)
                            }
                            PullToRefreshContainer(
                                modifier = Modifier.align(Alignment.TopCenter),
                                state = state,
                            )
                        }
                    }
                }

                is Resource.Error -> {
                    navController.navigate(Screen.Error.createRoute("${project.message}"))
                }

                else -> {
                    navController.navigate(Screen.Error.createRoute("Unknown Error"))
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectDetailTopBar(navController: NavController) {
    TopAppBar(title = { Text("Project Details") },
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        }
    )
}

@Composable
fun ProjectDetailContent(paddingValues: PaddingValues, projectData: Project, context: Context) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        item {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Project Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = projectData.title,
                style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.onSurface)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = projectData.description,
                style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { openUrl(context, projectData.url) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
            ) {
                Text("Visit Project", color = MaterialTheme.colorScheme.onPrimary)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { openUrl(context, projectData.repositoryUrl) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)
            ) {
                Text("View Repository", color = MaterialTheme.colorScheme.onTertiary)
            }
        }
    }
}

private fun openUrl(context: Context, url: String) {
    if (url.isNotEmpty()) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }
}

@Preview(showBackground = true)
@Composable
fun ProjectDetailScreenPreview() {
    CapstoneTheme {
        ProjectDetailScreen(viewModel = viewModel(), navController = rememberNavController(), "1")
    }
}
