package com.blueprint.simplekotlinflow.repository

import com.blueprint.simplekotlinflow.components.ResultWrapper
import com.blueprint.simplekotlinflow.models.Post
import com.blueprint.simplekotlinflow.network.IPostRepository
import com.blueprint.simplekotlinflow.network.RestService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PostRepository @Inject constructor(private val restAPIService: RestService) : IPostRepository {
    override fun getPosts(): Flow<ResultWrapper<List<Post>>> = flow {
        emit(restAPIService.getPosts())
    }.flowOn(Dispatchers.IO)
    override fun getPost():Flow<ResultWrapper<Post>> = flow {
        emit(restAPIService.getPost())
    }.flowOn(Dispatchers.IO)
}