package mohammed.capstone

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import mohammed.capstone.ui.screens.AboutScreen
import mohammed.capstone.ui.screens.ErrorScreen

import mohammed.capstone.ui.screens.HomeScreen
import mohammed.capstone.ui.screens.ProjectsScreen
import mohammed.capstone.ui.screens.Screen
import mohammed.capstone.ui.theme.CapstoneTheme
import mohammed.capstone.viewmodel.ViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CapstoneTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    CapstoneApp()
                }
            }
        }
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CapstoneApp() {
    val viewModel: ViewModel = viewModel()
    val navController = rememberNavController()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {
            BottomNav(navController = navController)
        }
    ) { innerPadding ->
        CapstoneNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CapstoneTheme {
        CapstoneApp()
    }
}

@Composable
fun BottomNav(navController: NavHostController) {
    val screens = listOf(Screen.Home, Screen.Projects, Screen.About, Screen.Error)

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        screens.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.label) },
                label = { Text(screen.label) },
                selected = currentDestination?.route == screen.route,
                onClick = {
                    if (navController.currentDestination?.route != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id)
                            launchSingleTop = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CapstoneNavHost(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) { HomeScreen(navController ) }
        composable(Screen.Projects.route) { ProjectsScreen(navController ) }
        composable(Screen.About.route) { AboutScreen(navController ) }
        composable(Screen.Error.route) { ErrorScreen(navController ) }
    }
}