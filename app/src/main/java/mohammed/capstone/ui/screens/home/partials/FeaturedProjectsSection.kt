package mohammed.capstone.ui.screens.home.partials

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
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

/**
 * Composable function to display a section featuring selected projects.
 * It handles different states of project data like loading, success, or error.
 *
 * @param projectsResource State holder for the list of projects wrapped in a Resource.
 * @param navController Navigation controller for handling navigation events.
 * @param viewModel ViewModel associated with this screen for managing data.
 */
@Composable
fun FeaturedProjectsSection(
    projectsResource: State<Resource<List<Project>>?>,
    navController: NavHostController,
    viewModel: ViewModel
) {
    // Handling different states of project data (loading, success, error).
    when (val projects = projectsResource.value) {
        // Display loading indicator while data is being fetched.
        is Resource.Loading -> Utils.LoadingIndicator()

        // On successful data fetch, display the project cards.
        is Resource.Success -> {
            Text(
                text = stringResource(id = R.string.home_featured_projects_title),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )

            // Display the first two projects as featured.
            projects.data?.take(2)?.forEach { project ->
                FeaturedProjectCard(
                    project = project,
                    navController = navController,
                    viewModel = viewModel
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Navigate to error screen on data fetch error.
        is Resource.Error -> navController.navigate(Screen.Error.createRoute("${projects.message}"))
        else -> navController.navigate(Screen.Error.createRoute(stringResource(id = R.string.unknown_error)))
    }
}

/**
 * Composable function to display a card for a featured project.
 * The card is clickable and navigates to the project's detail screen on click.
 *
 * @param project Project data to be displayed in the card.
 * @param navController Navigation controller for handling navigation events.
 * @param viewModel ViewModel for managing project-related data.
 */
@Composable
fun FeaturedProjectCard(
    project: Project,
    navController: NavHostController,
    viewModel: ViewModel
) {
    // Card layout for a single project.
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
