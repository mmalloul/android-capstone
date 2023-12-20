package mohammed.capstone.ui.screens

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import mohammed.capstone.ui.theme.CapstoneTheme

@Composable
fun HomeScreen(navController: NavHostController) {
    CapstoneTheme(darkTheme = true) {

        Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text(
                text = "Welcome to the Home Screen!",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    CapstoneTheme(darkTheme = true) {
        HomeScreen(navController = rememberNavController())
    }
}
