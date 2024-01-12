package mohammed.capstone.ui.screens.projectDetail.partials

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import mohammed.capstone.R
import mohammed.capstone.data.models.Project
import mohammed.capstone.utils.Utils

/**
 * Composable function to display the details of a specific project.
 * This includes a header image, project content, and buttons for external links.
 *
 * @param projectData Data model containing the details of the project.
 * @param context Android Context used for handling external URL navigation.
 * @param navController Navigation controller for handling navigation events.
 */
@Composable
fun ProjectDetail(
    projectData: Project,
    context: Context,
    navController: NavController
) {
    // LazyColumn to display project details in a scrollable column.
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 0.dp, 16.dp, 0.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            ProjectHeaderImage()
        }

        item {
            ProjectContent(projectData = projectData)
        }

        item {
            UrlButtons(projectData, context, navController)
        }
    }
}

/**
 * Composable function to display the header image of the project.
 */
@Composable
fun ProjectHeaderImage() {
    Image(
        painter = painterResource(id = R.drawable.company_logo_transparant),
        contentDescription = stringResource(id = R.string.project_image_description),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(8.dp))
    )
}

/**
 * Composable function to display the title and description of the project.
 *
 * @param projectData Data model containing the title and description of the project.
 */
@Composable
fun ProjectContent(projectData: Project) {
    Text(
        text = projectData.title,
        style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.onSurface)
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = projectData.description,
        style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
    )
}

/**
 * Composable function to display buttons for external project links.
 * Includes a button to visit the project website and another to view the repository.
 *
 * @param projectData Data model containing the URLs for the project and repository.
 * @param context Android Context used for handling external URL navigation.
 * @param navController Navigation controller for handling navigation events.
 */
@Composable
fun UrlButtons(projectData: Project, context: Context, navController: NavController) {
    Button(
        onClick = { Utils.openCustomTab(navController, context, projectData.url) },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
    ) {
        Text(
            stringResource(id = R.string.visit_project_btn),
            color = MaterialTheme.colorScheme.onPrimary
        )
    }

    Spacer(modifier = Modifier.height(8.dp))

    Button(
        onClick = { Utils.openCustomTab(navController, context, projectData.repositoryUrl) },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)
    ) {
        Text(
            stringResource(id = R.string.view_repository_btn),
            color = MaterialTheme.colorScheme.onTertiary
        )
    }
}