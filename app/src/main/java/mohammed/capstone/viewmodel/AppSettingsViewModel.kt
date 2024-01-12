package mohammed.capstone.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mohammed.capstone.data.enums.ThemeOption
import mohammed.capstone.data.models.AppSettings
import mohammed.capstone.repository.AppSettingsRepository

class AppSettingsViewModel(application: Application) : ViewModel(application) {
    private val appSettingsRepository = AppSettingsRepository(application.applicationContext)
    val appSettings = MutableLiveData<AppSettings?>()
    var themePreference by mutableStateOf(ThemeOption.SYSTEM)
        private set

    init {
        viewModelScope.launch {
            val settings = appSettingsRepository.getAppSettings()
            themePreference = if (settings == null) {
                val newSettings =
                    AppSettings(isFirstLaunch = true, themePreference = ThemeOption.SYSTEM)
                appSettingsRepository.insertAppSettings(newSettings)
                appSettings.postValue(newSettings)
                ThemeOption.SYSTEM
            } else {
                appSettings.postValue(settings)
                settings.themePreference
            }
        }
    }

    fun saveThemePreference(themeOption: ThemeOption) {
        viewModelScope.launch {
            val currentSettings = appSettings.value ?: AppSettings(
                isFirstLaunch = true,
                themePreference = ThemeOption.SYSTEM
            )
            val updatedSettings = currentSettings.copy(themePreference = themeOption)
            appSettingsRepository.updateAppSettings(updatedSettings)
            appSettings.postValue(updatedSettings)
            themePreference = themeOption
        }
    }

    fun getAppSettings(onResult: (AppSettings?) -> Unit) {
        viewModelScope.launch {
            val settings = appSettingsRepository.getAppSettings()
            onResult(settings)
        }
    }

    fun updateAppSettings(settings: AppSettings) {
        viewModelScope.launch {
            appSettingsRepository.updateAppSettings(settings)
            appSettings.postValue(settings)
        }
    }

    fun setFirstLaunchCompleted() {
        viewModelScope.launch {
            val currentSettings = appSettings.value ?: return@launch
            val updatedSettings = currentSettings.copy(isFirstLaunch = false)
            appSettingsRepository.updateAppSettings(updatedSettings)
            appSettings.postValue(updatedSettings)
        }
    }
}
