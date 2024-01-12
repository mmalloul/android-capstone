package mohammed.capstone.ui.screens.about.partials

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import mohammed.capstone.R

/**
 * Composable function to display a section showcasing a list of skills.
 * It uses a FlowRow layout to dynamically arrange skill chips.
 *
 * @param skills A list of strings representing various skills.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SkillsSection(skills: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(id = R.string.skills_title),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(16.dp))

        // FlowRow layout to dynamically arrange skill chips.
        FlowRow(
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.Top,
            maxItemsInEachRow = 4,
            modifier = Modifier.fillMaxWidth()
        ) {
            skills.forEach { skill ->
                // Display each skill in a separate chip.
                SkillChip(skill)
            }
        }
    }
}

/**
 * Composable function to display a single skill chip.
 * Currently, the chips are static but can be linked to relevant projects or information.
 *
 * @param skill The skill to be displayed in the chip.
 */
@Composable
fun SkillChip(skill: String) {
    Chip(
        label = { Text(skill) },
        onClick = {
                  // TODO:  link to project that has the skill used
             },
        modifier = Modifier
    )
}

/**
 * Generic composable function to create a chip with a label and an onClick action.
 *
 * @param label Composable lambda to provide the content inside the chip.
 * @param onClick Lambda function to handle click actions on the chip.
 * @param modifier Modifier for customizing the UI
 */
@Composable
fun Chip(
    label: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedButton(
        onClick = onClick,
        modifier = modifier
    ) {
        label()
    }
}