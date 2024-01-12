package mohammed.capstone.config

import mohammed.capstone.ui.screens.Screen

enum class ScreenConfig(val screens: List<Screen>) {
    NAV_SCREENS(listOf(Screen.Projects, Screen.Home, Screen.About));

    companion object {
        fun getNavScreens(): List<Screen> = NAV_SCREENS.screens
    }
}
