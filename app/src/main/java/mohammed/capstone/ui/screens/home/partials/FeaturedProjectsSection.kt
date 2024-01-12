package mohammed.capstone.ui.screens.home.partials

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import mohammed.capstone.R
import mohammed.capstone.data.api.util.Resource
import mohammed.capstone.data.models.Project
import mohammed.capstone.ui.screens.Screen
import mohammed.capstone.utils.Utils
import mohammed.capstone.viewmodel.ViewModel

@Composable
fun FeaturedProjectsSection(
    projectsResource: State<Resource<List<Project>>?>,
    navController: NavHostController,
    viewModel: ViewModel
) {

    when (val projects = projectsResource.value) {
        is Resource.Loading -> Utils.LoadingIndicator()

        is Resource.Success -> {
            Text(
                text = stringResource(id = R.string.home_featured_projects_title),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )

            projects.data?.take(2)?.forEach { project ->
                FeaturedProjectCard(
                    project = project,
                    navController = navController,
                    viewModel = viewModel
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        is Resource.Error -> navController.navigate(Screen.Error.createRoute("${projects.message}"))
        else -> navController.navigate(Screen.Error.createRoute(stringResource(id = R.string.unknown_error)))
    }
}

@Composable
fun FeaturedProjectCard(
    project: Project,
    navController: NavHostController,
    viewModel: ViewModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                viewModel.resetProject()
                navController.navigate(Screen.ProjectDetail.createRoute(projectId = project.id))
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = project.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = project.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
