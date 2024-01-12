package mohammed.capstone.repository

import android.content.Context
import mohammed.capstone.data.models.AppSettings
import mohammed.capstone.db.AppDatabase

class AppSettingsRepository(context: Context) {
    private val appSettingsDao = AppDatabase.getDatabase(context).appSettingsDao()

    suspend fun getAppSettings(): AppSettings? {
        return appSettingsDao.getAppSettings()
    }

    suspend fun insertAppSettings(settings: AppSettings) {
        appSettingsDao.insertAppSettings(settings)
    }

    suspend fun updateAppSettings(settings: AppSettings) {
        appSettingsDao.updateAppSettings(settings)
    }
}
