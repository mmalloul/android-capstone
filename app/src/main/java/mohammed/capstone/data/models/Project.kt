package mohammed.capstone.data.models

import com.google.gson.annotations.SerializedName

data class Project(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("repo_url") val repoUrl: String,
    @SerializedName("url") val url: String
)
