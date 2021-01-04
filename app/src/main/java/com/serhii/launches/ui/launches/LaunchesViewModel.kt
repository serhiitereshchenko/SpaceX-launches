package com.serhii.launches.ui.launches

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhii.launches.data.model.Launch
import com.serhii.launches.data.repository.LaunchesRepository
import com.serhii.launches.mvvm.Resource
import kotlinx.coroutines.launch

class LaunchesViewModel @ViewModelInject constructor(
    private val launchesRepository: LaunchesRepository,
    private val dateFormatter: DateFormatter
) : ViewModel() {

    private val _launches = MutableLiveData<Resource<List<Launch>>>()
    val launches: LiveData<Resource<List<Launch>>> = _launches

    var filterCriteria: ((launch: Launch) -> Boolean)? = null
        set(value) {
            field = value
            loadLaunches()
        }

    fun loadLaunches(forceUpdate: Boolean = false) {
        viewModelScope.launch {
            _launches.value = Resource.Loading

            launchesRepository.getLaunches(forceUpdate).let { result ->
                var resource: Resource<List<Launch>>? = result
                if (result is Resource.Success) {
                    result.data.map { it.formattedDate = dateFormatter.formatDate(it.date) }
                    filterCriteria?.let { filter: (launch: Launch) -> Boolean ->
                        resource = Resource.Success(result.data.filter { filter.invoke(it) })
                    }
                }
                _launches.value = resource
            }
        }
    }
}
