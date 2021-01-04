package com.serhii.launches.ui.rocket_details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhii.launches.data.model.Rocket
import com.serhii.launches.data.repository.RocketsRepository
import com.serhii.launches.mvvm.Resource
import kotlinx.coroutines.launch

class RocketDetailsViewModel @ViewModelInject constructor(
    private val rocketsRepository: RocketsRepository
) : ViewModel() {

    private val _rocket = MutableLiveData<Resource<Rocket>>()
    val rocket: LiveData<Resource<Rocket>> = _rocket

    fun loadRocket(id: String, forceUpdate: Boolean = false) {
        viewModelScope.launch {
            _rocket.value = Resource.Loading
            rocketsRepository.getRocket(id, forceUpdate).let { result ->
                _rocket.value = result
            }
        }
    }
}
