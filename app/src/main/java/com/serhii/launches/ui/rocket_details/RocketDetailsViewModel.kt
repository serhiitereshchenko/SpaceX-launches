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
            rocketsRepository.getRocket(id, forceUpdate).let { _rocket.value = it }
        }
    }
}
