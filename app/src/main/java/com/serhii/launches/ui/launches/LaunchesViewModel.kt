package com.serhii.launches.ui.launches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhii.repository.Resource
import com.serhii.repository.hasData
import com.serhii.repository.model.Launch
import com.serhii.repository.repository.LaunchesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch

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
            ensureActive()

            val result = getLaunchesFromRepository(forceUpdate)
            _launches.value = result
            ensureActive()

            if (forceUpdate && result is Resource.Error) {
                _launches.value = getLaunchesFromRepository(forceUpdate = false)
            }
        }
    }

    private suspend fun getLaunchesFromRepository(forceUpdate: Boolean): Resource<List<Launch>> {
        val result: Resource<List<Launch>> = launchesRepository.getLaunches(forceUpdate)
        if (result.hasData) {
            return Resource.Success(prepareItems((result as Resource.Success).data.orEmpty()))
        }
        return result
    }

    private fun prepareItems(items: List<Launch>): List<Launch> {
        var launches = items
        filterCriteria?.let { criteria ->
            launches = items.filter { launch: Launch -> criteria.filter(launch) }
        }
        launches.forEach { launch ->
            launch.formattedDate = dateFormatter.formatDate(launch.rawDate)
        }
        return launches
    }
}

sealed class FilterCriteria(val filter: (launch: Launch) -> Boolean) {
    object SuccessLaunch : FilterCriteria({ launch -> launch.isSuccess })
}
