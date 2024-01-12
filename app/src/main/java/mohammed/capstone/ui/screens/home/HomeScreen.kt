package mohammed.capstone.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import mohammed.capstone.R
import mohammed.capstone.ui.screens.home.partials.ContactMeSection
import mohammed.capstone.ui.screens.home.partials.FeaturedProjectsSection
import mohammed.capstone.ui.screens.home.partials.IntroductionSection
import mohammed.capstone.ui.theme.CapstoneTheme
import mohammed.capstone.utils.Utils
import mohammed.capstone.viewmodel.ViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: ViewModel) {
    val focusManager = LocalFocusManager.current
    val state = rememberPullToRefreshState { true }
    val projectsResource = viewModel.getAllProjectsResource.observeAsState()

    if (state.isRefreshing) {
        LaunchedEffect(true) {
            viewModel.getAllProjects()
            delay(Utils.REFRESH_TIME)
            state.endRefresh()
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        content = { innerPadding ->
            Box(
                Modifier
                    .fillMaxSize()
                    .nestedScroll(state.nestedScrollConnection)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(16.dp)
                ) {
                    item {
                        HeaderImage()
                        Spacer(modifier = Modifier.height(24.dp))
                        IntroductionSection()
                        Spacer(modifier = Modifier.height(24.dp))
                        if (!state.isRefreshing) {
                            FeaturedProjectsSection(projectsResource, navController, viewModel)
                            Spacer(modifier = Modifier.height(24.dp))
                            ContactMeSection(focusManager)
                            Spacer(modifier = Modifier.height(24.dp))
                        }
                    }
                }
                PullToRefreshContainer(
                    modifier = Modifier.align(Alignment.TopCenter),
                    state = state,
                )
            }
        }
    )
}

@Composable
fun HeaderImage() {
    Image(
        painter = painterResource(id = R.drawable.company_logo_transparant),
        contentDescription = stringResource(id = R.string.header_image_description),
        modifier = Modifier
            .fillMaxSize()
            .height(200.dp)
            .clip(shape = RoundedCornerShape(bottomEnd = 24.dp, bottomStart = 24.dp)),
        contentScale = ContentScale.Crop,
        alignment = Alignment.Center
    )
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    CapstoneTheme {
        HomeScreen(navController = rememberNavController(), viewModel())
    }
}