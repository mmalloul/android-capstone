package mohammed.capstone

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import mohammed.capstone.config.Config
import mohammed.capstone.ui.screens.about.AboutScreen
import mohammed.capstone.ui.screens.error.ErrorScreen
import mohammed.capstone.ui.screens.home.HomeScreen
import mohammed.capstone.ui.screens.projectDetail.ProjectDetailScreen
import mohammed.capstone.ui.screens.projectList.ProjectListScreen
import mohammed.capstone.ui.screens.Screen
import mohammed.capstone.ui.theme.CapstoneTheme
import mohammed.capstone.viewmodel.SplashViewModel
import mohammed.capstone.viewmodel.ViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemIsInDarkTheme = isSystemInDarkTheme()

            val splashViewModel = viewModel<SplashViewModel>()
            val showSplashScreen by splashViewModel.showSplashScreen.observeAsState(initial = true)

            CapstoneTheme(darkTheme = systemIsInDarkTheme) {
                Crossfade(
                    targetState = showSplashScreen,
                    label = stringResource(id = R.string.splash_screen_fade_label)
                ) { showSplash ->
                    if (showSplash) {
                        SplashScreen()
                    } else {
                        MainAppContent()
                    }
                }
            }
        }
    }
}

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.company_logo_transparant),
            contentDescription = stringResource(id = R.string.splash_screen_image_description)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainAppContent() {
    Surface(modifier = Modifier.fillMaxSize()) {
        CapstoneApp()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CapstoneApp() {
    val viewModel: ViewModel = viewModel()
    val navController = rememberNavController()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {
            BottomNav(navController = navController)
        }
    ) { innerPadding ->
        CapstoneNavHost(
            viewModel = viewModel,
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
    val screens = Config.getNavScreens()

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
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
fun CapstoneNavHost(viewModel: ViewModel, navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) { HomeScreen(navController, viewModel) }
        composable(Screen.Projects.route) { ProjectListScreen(viewModel, navController) }
        composable(Screen.About.route) { AboutScreen() }
        composable(
            route = Screen.Error.route,
            arguments = listOf(navArgument("errorMessage") { type = NavType.StringType })
        ) { backStackEntry ->
            val errorMessage =
                backStackEntry.arguments?.getString("errorMessage") ?: stringResource(
                    id = R.string.unknown_error
                )
            ErrorScreen(navController, errorMessage)
        }
        composable(
            route = Screen.ProjectDetail.route,
            arguments = listOf(navArgument("projectId") { type = NavType.StringType })
        ) { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("projectId") ?: return@composable
            ProjectDetailScreen(viewModel, navController, projectId)
        }
    }
}