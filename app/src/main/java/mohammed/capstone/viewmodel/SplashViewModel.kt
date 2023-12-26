package mohammed.capstone.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(application: Application) : ViewModel(application) {
    private val _showSplashScreen = MutableLiveData(true)
    val showSplashScreen: LiveData<Boolean> = _showSplashScreen

    init {
        viewModelScope.launch {
            delay(2000)
            _showSplashScreen.value = false
        }
    }
}