package mohammed.capstone.data.models

import com.google.gson.annotations.SerializedName

/**
 * Data class representing a project entity.
 * This class is used for parsing JSON data fetched from the API.
 *
 * @property id Unique identifier of the project.
 * @property title Title of the project.
 * @property description Brief description of the project.
 * @property repositoryUrl URL to the project's source code repository.
 * @property url URL to the project's live site or additional information.
 * @property createdAt Timestamp of when the project was created.
 * @property updatedAt Timestamp of the last update to the project.
 */
data class Project(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("repositoryUrl") val repositoryUrl: String,
    @SerializedName("url") val url: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String,
)
