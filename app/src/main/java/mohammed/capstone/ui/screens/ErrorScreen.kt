package mohammed.capstone.ui.screens

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
import mohammed.capstone.ui.theme.ErrorColor
import mohammed.capstone.R

@Composable
fun ErrorScreen(
    navController: NavHostController,
    errorMessage: String
) {
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
                tint = ErrorColor,
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
            colors = ButtonDefaults.buttonColors(containerColor = ErrorColor),
        ) {
            Text(text = stringResource(id = R.string.go_back_btn))
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ErrorScreenPreview() {
    CapstoneTheme {
        ErrorScreen(navController = rememberNavController(), errorMessage = "error")
    }
}
