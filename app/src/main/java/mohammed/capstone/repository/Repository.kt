package mohammed.capstone.repository

import android.util.Log
import kotlinx.coroutines.withTimeout
import mohammed.capstone.data.api.Api
import mohammed.capstone.data.api.ApiService
import mohammed.capstone.data.api.util.Resource
import mohammed.capstone.data.models.Project

class Repository {
    private val apiService: ApiService = Api.apiClient

    suspend fun getProject(): Resource<Project> {
        val res = try {
            withTimeout(5_000) {
                apiService.getProject()
            }
        } catch (e: Exception) {
            Log.e("Repository", e.message ?: "No exception message available")
            return Resource.Error("An unknown error occurred")
        }

        return Resource.Success(res)
    }
}