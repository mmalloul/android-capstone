package mohammed.capstone.data.models

import com.google.gson.annotations.SerializedName

data class Project(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("repositoryUrl") val repositoryUrl: String,
    @SerializedName("url") val url: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String,
)
