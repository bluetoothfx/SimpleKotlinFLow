package com.blueprint.simplekotlinflow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blueprint.simplekotlinflow.components.ResultWrapper
import com.blueprint.simplekotlinflow.models.Album
import com.blueprint.simplekotlinflow.repository.AlbumRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    private val albumRepository: AlbumRepository
) : ViewModel() {

    private val _myFlow = MutableStateFlow<ResultWrapper<List<Album>>>(ResultWrapper.Loading)
    var albumResult: StateFlow<ResultWrapper<List<Album>>> = _myFlow.asStateFlow()

    var job = viewModelScope.launch {
        albumResult = albumRepository.getAlbums().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = ResultWrapper.Loading
        )
    }
}
