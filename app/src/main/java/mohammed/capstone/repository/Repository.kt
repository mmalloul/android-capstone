package mohammed.capstone.repository

import android.util.Log
import kotlinx.coroutines.withTimeout
import mohammed.capstone.data.api.Api
import mohammed.capstone.data.api.ApiService
import mohammed.capstone.data.api.util.Resource
import mohammed.capstone.data.models.Project

class Repository {
    companion object {
        private const val TIMEOUT_DURATION = 5_000L
    }

    private val apiService: ApiService = Api.apiClient

    suspend fun getAllProject(): Resource<List<Project>> {
        return try {
            withTimeout(TIMEOUT_DURATION) {
                val response = apiService.getAllProject()
                Resource.Success(response)
            }
        } catch (e: Exception) {
            Log.e("Repository", "Error in getAllProject: ${e.localizedMessage}")
            Resource.Error("Error fetching projects: ${e.localizedMessage}")
        }
    }

    suspend fun getProject(id: String): Resource<Project> {
        return try {
            withTimeout(TIMEOUT_DURATION) {
                val response = apiService.getProject(id)
                Resource.Success(response)
            }
        } catch (e: Exception) {
            Log.e("Repository", "Error in getProject: ${e.localizedMessage}")
            Resource.Error("Error fetching project details: ${e.localizedMessage}")
        }
    }
}