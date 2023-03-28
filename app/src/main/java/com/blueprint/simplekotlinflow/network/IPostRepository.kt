package com.blueprint.simplekotlinflow.network

import com.blueprint.simplekotlinflow.components.ResultWrapper
import com.blueprint.simplekotlinflow.models.Album
import com.blueprint.simplekotlinflow.models.Post
import kotlinx.coroutines.flow.Flow

interface IPostRepository {
    suspend fun getPosts(): Flow<ResultWrapper<List<Post>>>

    suspend fun getPost(): Flow<ResultWrapper<Post>>
}