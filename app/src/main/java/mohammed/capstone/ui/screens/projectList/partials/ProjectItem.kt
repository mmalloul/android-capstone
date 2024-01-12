package mohammed.capstone.ui.screens.projectList.partials

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import mohammed.capstone.R
import mohammed.capstone.data.models.Project
import mohammed.capstone.ui.screens.Screen
import mohammed.capstone.utils.Utils
import mohammed.capstone.viewmodel.ViewModel

/**
 * Composable function to display an individual project item in the project list.
 * Each item is represented as a card with an image, title, description, and buttons for external links.
 *
 * @param project The project data to be displayed.
 * @param navController NavHostController for handling navigation to the project detail screen.
 * @param viewModel ViewModel for managing project-related data.
 */
@Composable
fun ProjectListItem(project: Project, navController: NavHostController, viewModel: ViewModel) {
    // Context from the local environment, used for handling link actions.
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                viewModel.resetProject()
                // Navigating to the project detail screen on click.
                navController.navigate(Screen.ProjectDetail.createRoute(project.id))
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Image representing the project.
            Image(
                painter = painterResource(id = R.drawable.company_logo_transparant),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Text displaying the project title.
            Text(project.title, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))

            // Text displaying the project description.
            Text(
                text = project.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Row layout for the buttons to visit the project URL and view the repository.
            Row {
                TextButton(onClick = {
                    val url = project.url
                    if (url.isNotEmpty()) {
                        Utils.openCustomTab(navController, context, url)
                    }
                }) {
                    Text(stringResource(id = R.string.project_url_btn))
                }
                Spacer(modifier = Modifier.width(16.dp))
                TextButton(onClick = {
                    val repoUrl = project.repositoryUrl
                    if (repoUrl.isNotEmpty()) {
                        Utils.openCustomTab(navController, context, repoUrl)
                    }
                }) {
                    Text(stringResource(id = R.string.repository_url_btn))
                }
            }
        }
    }
}
