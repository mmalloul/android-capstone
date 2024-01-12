package mohammed.capstone.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import mohammed.capstone.data.enums.ThemeOption

@Entity(tableName = "app_settings")
data class AppSettings (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val isFirstLaunch: Boolean,
    val themePreference: ThemeOption
)
