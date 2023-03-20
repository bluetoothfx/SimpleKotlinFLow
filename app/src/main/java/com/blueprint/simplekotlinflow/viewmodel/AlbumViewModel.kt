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
    val albumResult: StateFlow<ResultWrapper<List<Album>>> = _myFlow.asStateFlow()

    /*val albumResult =  flow {
        val data = albumRepository.getAlbums()
        _myFlow.value = data
        //emit(data)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = ResultWrapper.Loading
    )*/

    /*suspend fun collectAlbums() {
        flow {
            emit(albumRepository.getAlbums())
        } .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = ResultWrapper.Loading
        ).onStart {
           // _myFlow.value = ResultWrapper.Loading
        }.catch { e ->
           // _myFlow.value = ResultWrapper.Failure(e)
        }.collect {
            _myFlow.value = it as Flow<ResultWrapper<List<Album>>>
        }
        //_myFlow.value = albumRepository.getAlbums()
    }*/
    init {
        collectAlbums()
    }

    private fun collectAlbums(){
        viewModelScope.launch {
            albumRepository.getAlbums().stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = ResultWrapper.Loading
            ).collect {
                _myFlow.value = it
            }
        }
    }
}