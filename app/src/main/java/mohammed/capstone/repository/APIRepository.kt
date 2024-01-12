package mohammed.capstone.repository

import android.util.Log
import kotlinx.coroutines.withTimeout
import mohammed.capstone.data.api.Api
import mohammed.capstone.data.api.ApiService
import mohammed.capstone.data.api.util.Resource
import mohammed.capstone.data.models.Project

/**
 * Repository class for handling data operations, particularly network calls using the ApiService.
 * Provides methods to fetch all projects and a single project by its ID.
 */
class APIRepository {
    companion object {
        // Constant defining the network timeout duration.
        private const val TIMEOUT_DURATION = 5_000L
    }

    // ApiService instance for making API calls.
    private val apiService: ApiService = Api.apiClient

    /**
     * Fetches all projects from the API.
     * Returns a Resource object wrapping the list of projects or an error message.
     *
     * @return Resource<List<Project>> representing the success or error state.
     */
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

    /**
     * Fetches a single project by its ID from the API.
     * Returns a Resource object wrapping the project or an error message.
     *
     * @param id The ID of the project to fetch.
     * @return Resource<Project> representing the success or error state.
     */
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