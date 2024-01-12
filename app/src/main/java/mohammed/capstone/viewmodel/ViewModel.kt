package mohammed.capstone.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mohammed.capstone.data.api.util.Resource
import mohammed.capstone.data.models.Project
import mohammed.capstone.repository.Repository

/**
 * ViewModel class for managing UI-related data and handling business logic.
 * Extends AndroidViewModel to be aware of the Application context.
 *
 * @param application The application context passed to the AndroidViewModel.
 */
open class ViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository()

    // MutableLiveData to handle the list of projects.
    private val _listOfProjects: MutableLiveData<Resource<List<Project>>> = MutableLiveData()

    // LiveData to expose the list of projects for observation by the UI.
    val getAllProjectsResource: LiveData<Resource<List<Project>>> = _listOfProjects

    // MutableLiveData to handle a single project.
    private val _project: MutableLiveData<Resource<Project>> = MutableLiveData()
    // LiveData to expose the single project for observation by the UI.
    val getProjectResource: LiveData<Resource<Project>> = _project

    init {
        // Fetch all projects initially.
        getAllProjects()
    }

    /**
     * Fetches all projects and updates the list of projects LiveData.
     * Utilizes a coroutine to perform the operation asynchronously.
     */
    fun getAllProjects() {
        viewModelScope.launch {
            // Indicate loading state.
            _listOfProjects.value = Resource.Loading()
            // Fetch all projects from the repository.
            _listOfProjects.value = repository.getAllProject()
        }
    }

    /**
     * Fetches a single project by its ID and updates the project LiveData.
     * Utilizes a coroutine to perform the operation asynchronously.
     *
     * @param id The ID of the project to fetch.
     */
    fun getProject(id: String) {
        viewModelScope.launch {
            // Indicate loading state.
            _project.value = Resource.Loading()
            // Fetch the project from the repository.
            _project.value = repository.getProject(id)
        }
    }

    /**
     * Resets the single project LiveData to an empty state.
     */
    fun resetProject() {
        _project.value = Resource.Empty()
    }
}