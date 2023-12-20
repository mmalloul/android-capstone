package mohammed.capstone.data.api

import mohammed.capstone.data.models.Project
import retrofit2.http.GET

interface ApiService {

    @GET("/api/project")
    suspend fun getProject(): Project
}