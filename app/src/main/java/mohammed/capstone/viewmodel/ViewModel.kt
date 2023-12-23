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

class ViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository()

    private val _listOfProjects: MutableLiveData<Resource<List<Project>>> = MutableLiveData()
    val getAllProjectsResource: LiveData<Resource<List<Project>>> = _listOfProjects

    private val _project: MutableLiveData<Resource<Project>> = MutableLiveData()
    val getProjectResource: LiveData<Resource<Project>> = _project

    init {
        getAllProjects()
    }

    fun getAllProjects() {
        viewModelScope.launch {
            _listOfProjects.value = Resource.Loading()
            _listOfProjects.value = repository.getAllProject()
        }
    }

    fun getProject(id: String) {
        viewModelScope.launch {
            _project.value = Resource.Loading()
            _project.value = repository.getProject(id)
        }
    }
}