package mohammed.capstone.ui.screens.projectDetail.partials

import android.content.Context
import androidx.compose.foundation.Image
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
import mohammed.capstone.R
import mohammed.capstone.data.models.Project
import mohammed.capstone.utils.Utils

@Composable
fun ProjectDetail(
    projectData: Project,
    context: Context,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 0.dp, 16.dp, 0.dp)
    ) {
        item {
            ProjectHeaderImage()
            Spacer(modifier = Modifier.height(16.dp))
        }


        item {
            ProjectContent(projectData = projectData)
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            UrlButtons(projectData, context)
        }
    }
}

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

@Composable
fun UrlButtons(projectData: Project, context: Context) {
    Button(
        onClick = { Utils.openCustomTab(context, projectData.url) },
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
        onClick = { Utils.openCustomTab(context, projectData.repositoryUrl) },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)
    ) {
        Text(
            stringResource(id = R.string.view_repository_btn),
            color = MaterialTheme.colorScheme.onTertiary
        )
    }
}