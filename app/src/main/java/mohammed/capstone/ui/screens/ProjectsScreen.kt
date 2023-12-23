package mohammed.capstone.ui.screens

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import mohammed.capstone.R
import mohammed.capstone.data.api.util.Resource
import mohammed.capstone.data.models.Project
import mohammed.capstone.ui.theme.CapstoneTheme
import mohammed.capstone.viewmodel.ViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectListScreen(viewModel: ViewModel, navController: NavHostController) {
    val projectsResource = viewModel.getAllProjectsResource.observeAsState()
    val pullRefreshState = rememberPullToRefreshState()

    if (pullRefreshState.isRefreshing) {
        LaunchedEffect(Unit) {
            delay(500)
            viewModel.getAllProjects()
            pullRefreshState.endRefresh()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(pullRefreshState.nestedScrollConnection)
    ) {
        Column(modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp)) {
            Text("Project List", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))

            when (val projects = projectsResource.value) {
                is Resource.Loading -> {
                    CircularProgressIndicator()
                }

                is Resource.Success -> {
                    LazyColumn {
                        if (!pullRefreshState.isRefreshing) {
                            items(projects.data!!) { project ->
                                ProjectListItem(project, navController)
                            }
                        }
                    }
                }

                is Resource.Error -> {
                    Log.d("Project List Screen", "${projects.message}")
                    navController.navigate(Screen.Error.createRoute("${projects.message}"))
                }

                else -> {
                    navController.navigate(Screen.Error.createRoute("Unknown Error"))
                }
            }
        }

        PullToRefreshContainer(
            modifier = Modifier.align(Alignment.TopCenter),
            state = pullRefreshState,
        )
    }
}

@Composable
fun ProjectListItem(project: Project, navController: NavHostController) {

    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(Screen.ProjectDetail.createRoute(project.id))
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(shape = RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(project.title, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = project.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                TextButton(onClick = {
                    val url = project.url
                    if (url.isNotEmpty()) {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                    }
                }) {
                    Text("Project URL")
                }
                Spacer(modifier = Modifier.width(16.dp))
                TextButton(onClick = {
                    val repoUrl = project.repositoryUrl
                    if (repoUrl.isNotEmpty()) {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(repoUrl))
                        context.startActivity(intent)
                    }
                }) {
                    Text("Repository URL")
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Preview(showBackground = true)
@Composable
fun ProjectListScreenPreview() {
    CapstoneTheme {
        ProjectListScreen(viewModel = viewModel(), navController = rememberNavController())
    }
}
