package mohammed.capstone.ui.screens.setup

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mohammed.capstone.data.enums.ThemeOption
import mohammed.capstone.viewmodel.AppSettingsViewModel

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
        Text("Choose Your Theme", fontSize = 24.sp, modifier = Modifier.padding(bottom = 32.dp))

        Button(
            onClick = {
                onThemeSelected(ThemeOption.LIGHT)
                appSettingsViewModel.setFirstLaunchCompleted()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Light Mode")
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
            Text("Dark Mode")
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
            Text("System Default")
        }
    }
}

