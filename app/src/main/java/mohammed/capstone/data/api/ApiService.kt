package mohammed.capstone.data.api

import mohammed.capstone.data.models.Project
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/projects")
    suspend fun getAllProject(): List<Project>

    @GET("/projects/{id}")
    suspend fun getProject(@Path(value="id", encoded = true) id: String): Project
}