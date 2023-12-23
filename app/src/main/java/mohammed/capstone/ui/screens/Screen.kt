package mohammed.capstone.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Filled.Home)
    object About : Screen("about", "About", Icons.Filled.Person)
    object Projects : Screen("projects", "Projects", Icons.Filled.Build)
    object Error : Screen("error/{errorMessage}", "Error", Icons.Filled.Warning) {
        fun createRoute(errorMessage: String) = "error/$errorMessage"
    }
    object ProjectDetail : Screen("projectDetail/{projectId}", "Project Detail", Icons.Filled.Warning) {
        fun createRoute(projectId: String) = "projectDetail/$projectId"
    }
}
