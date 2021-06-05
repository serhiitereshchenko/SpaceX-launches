package com.serhii.launches.ui.launches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhii.repository.model.Launch
import com.serhii.repository.repository.LaunchesRepository
import com.serhii.repository.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchesViewModel @Inject constructor(
    private val launchesRepository: LaunchesRepository,
    private val dateFormatter: DateFormatter
) : ViewModel() {

    private val _launches = MutableLiveData<Resource<List<Launch>>>()
    val launches: LiveData<Resource<List<Launch>>> = _launches

    var filterCriteria: FilterCriteria? = null
        set(value) {
            field = value
            loadLaunches()
        }

    fun loadLaunches(forceUpdate: Boolean = false) {
        viewModelScope.launch {
            _launches.value = Resource.Loading

            launchesRepository.getLaunches(forceUpdate).let { result ->
                var resource: Resource<List<Launch>> = result
                if (result is Resource.Success) {
                    result.data.map { it.formattedDate = dateFormatter.formatDate(it.date) }
                    filterCriteria?.let { criteria ->
                        resource = Resource.Success(result.data.filter { criteria.filter(it) })
                    }
                }
                _launches.value = resource
            }
        }
    }
}

sealed class FilterCriteria(val filter: (launch: Launch) -> Boolean) {
    object SuccessFilter : FilterCriteria({ it.isSuccess })
}
