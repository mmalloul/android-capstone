package mohammed.capstone.ui.screens.setup

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mohammed.capstone.data.enums.ThemeOption
import mohammed.capstone.viewmodel.AppSettingsViewModel
import mohammed.capstone.R
@Composable
fun ThemeSelectionScreen(
    onThemeSelected: (ThemeOption) -> Unit,
    appSettingsViewModel: AppSettingsViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(stringResource(id = R.string.theme_selection_title), fontSize = 24.sp, modifier = Modifier.padding(bottom = 32.dp))

        Button(
            onClick = {
                onThemeSelected(ThemeOption.LIGHT)
                appSettingsViewModel.setFirstLaunchCompleted()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(stringResource(id = R.string.theme_light_mode))
        }

        Button(
            onClick = {
                onThemeSelected(ThemeOption.DARK)
                appSettingsViewModel.setFirstLaunchCompleted()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(stringResource(id = R.string.theme_dark_mode))
        }

        Button(
            onClick = {
                onThemeSelected(ThemeOption.SYSTEM)
                appSettingsViewModel.setFirstLaunchCompleted()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(stringResource(id = R.string.theme_system_default))
        }
    }
}

