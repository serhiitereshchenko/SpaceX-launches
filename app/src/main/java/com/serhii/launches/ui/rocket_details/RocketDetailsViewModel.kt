package com.serhii.launches.ui.rocket_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhii.repository.Resource
import com.serhii.repository.model.Rocket
import com.serhii.repository.repository.RocketsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch

@HiltViewModel
class RocketDetailsViewModel @Inject constructor(
    private val rocketsRepository: RocketsRepository
) : ViewModel() {

    private val _rocket = MutableLiveData<Resource<Rocket>>()
    val rocket: LiveData<Resource<Rocket>> = _rocket

    fun loadRocket(id: String, forceUpdate: Boolean = false) {
        viewModelScope.launch {
            _rocket.value = Resource.Loading
            ensureActive()

            val result = rocketsRepository.getRocket(id, forceUpdate)
            _rocket.value = result
            ensureActive()

            if (forceUpdate && result is Resource.Error) {
                _rocket.value = rocketsRepository.getRocket(id, forceUpdate = false)
            }
        }
    }
}
