package mohammed.capstone.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import mohammed.capstone.data.models.AppSettings

@Dao
interface AppSettingsDao {
    @Query("SELECT * FROM app_settings LIMIT 1")
    suspend fun getAppSettings(): AppSettings?

    @Insert
    suspend fun insertAppSettings(settings: AppSettings)

    @Update
    suspend fun updateAppSettings(settings: AppSettings)
}
