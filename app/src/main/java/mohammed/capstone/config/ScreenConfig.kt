package mohammed.capstone.config

import mohammed.capstone.ui.screens.Screen

/**
 * Enum class to define different configurations of screens in the application.
 *
 * @param screens A list of screens included in this configuration.
 */
enum class ScreenConfig(val screens: List<Screen>) {
    /**
     * Enum value representing the set of screens to be shown in the navigation bar.
     * Currently includes 'Projects', 'Home', and 'About' screens.
     */
    NAV_SCREENS(listOf(Screen.Projects, Screen.Home, Screen.About));


    companion object {
        /**
         * Function to get the list of navigation screens.
         * Returns the screens defined in the NAV_SCREENS enum value.
         *
         * @return List of screens for navigation.
         */
        fun getNavScreens(): List<Screen> = NAV_SCREENS.screens
    }
}
