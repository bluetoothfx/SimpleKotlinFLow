package com.blueprint.simplekotlinflow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blueprint.simplekotlinflow.components.ResultWrapper
import com.blueprint.simplekotlinflow.models.Album
import com.blueprint.simplekotlinflow.models.Post
import com.blueprint.simplekotlinflow.repository.AlbumRepository
import com.blueprint.simplekotlinflow.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postRepository: PostRepository
) : ViewModel() {

    private val _postsFlow = MutableStateFlow<ResultWrapper<List<Post>>>(ResultWrapper.Loading)
    var postsResult: StateFlow<ResultWrapper<List<Post>>> = _postsFlow.asStateFlow()

    fun getPosts() {
        viewModelScope.launch {
            postsResult = postRepository.getPosts().stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = ResultWrapper.Loading
            )
        }
    }

    private val _postFlow = MutableStateFlow<ResultWrapper<Post>>(ResultWrapper.Loading)
    var postResult: StateFlow<ResultWrapper<Post>> = _postFlow.asStateFlow()

    fun getPost() {
        viewModelScope.launch {
            postResult = postRepository.getPost().stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = ResultWrapper.Loading
            )
        }
    }
}
