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

class ViewModel(application: Application): AndroidViewModel(application) {
    private val repository = Repository()

    val getProjectResource: LiveData<Resource<Project>>
        get() = _apiHealthResource

    private val _apiHealthResource: MutableLiveData<Resource<Project>> = MutableLiveData(Resource.Empty())

    fun getProject() {
        _apiHealthResource.value = Resource.Loading()

        viewModelScope.launch {
            _apiHealthResource.value = repository.getProject()
        }
    }
}