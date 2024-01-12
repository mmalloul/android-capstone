package mohammed.capstone.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * ViewModel class specifically for managing the splash screen logic.
 * Extends the base ViewModel class, utilizing its functionalities and context awareness.
 *
 * @param application The application context passed to the AndroidViewModel.
 */
class SplashViewModel(application: Application) : ViewModel(application) {
    // MutableLiveData to handle the visibility state of the splash screen.
    private val _showSplashScreen = MutableLiveData(true)
    // LiveData to expose the splash screen visibility state for observation.
    val showSplashScreen: LiveData<Boolean> = _showSplashScreen

    init {
        // Launching a coroutine in the viewModelScope.
        viewModelScope.launch {
            // Delay to simulate the splash screen display time.
            delay(2000)
            // After the delay, update the value to hide the splash screen.
            _showSplashScreen.value = false
        }
    }
}