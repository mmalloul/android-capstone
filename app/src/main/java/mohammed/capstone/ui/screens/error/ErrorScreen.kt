package mohammed.capstone.ui.screens.error

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import mohammed.capstone.ui.theme.CapstoneTheme
import mohammed.capstone.R
import mohammed.capstone.ui.screens.Screen

@Composable
fun ErrorScreen(
    navController: NavHostController,
    errorMessage: String
) {
    CapstoneTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = 44.dp,
                alignment = Alignment.CenterVertically
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_error),
                    contentDescription = stringResource(id = R.string.error_icon),
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(100.dp)
                )
                Text(
                    text = stringResource(id = R.string.error_message),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = errorMessage,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
            Button(
                onClick = { navController.navigate(Screen.Home.route) },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
            ) {
                Text(text = stringResource(id = R.string.go_back_btn))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    CapstoneTheme(darkTheme = true) {
        ErrorScreen(
            navController = rememberNavController(),
            errorMessage = stringResource(id = R.string.unknown_error)
        )
    }
}