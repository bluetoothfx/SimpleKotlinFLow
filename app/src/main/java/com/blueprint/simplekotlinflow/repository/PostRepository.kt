package com.blueprint.simplekotlinflow.repository

import com.blueprint.simplekotlinflow.components.ResultWrapper
import com.blueprint.simplekotlinflow.models.Post
import com.blueprint.simplekotlinflow.network.IPostRepository
import com.blueprint.simplekotlinflow.network.RestService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRepository @Inject constructor(private val restAPIService: RestService, )
    : IPostRepository {

    override suspend fun getPosts(): Flow<ResultWrapper<List<Post>>> {
        return restAPIService.getPosts()
    }

    override suspend fun getPost(): Flow<ResultWrapper<Post>> {
        return restAPIService.getPost()
    }

}