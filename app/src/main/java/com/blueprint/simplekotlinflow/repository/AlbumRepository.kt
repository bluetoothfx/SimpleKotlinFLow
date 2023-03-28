package com.blueprint.simplekotlinflow.repository

import com.blueprint.simplekotlinflow.components.ResultWrapper
import com.blueprint.simplekotlinflow.models.Album
import com.blueprint.simplekotlinflow.models.Post
import com.blueprint.simplekotlinflow.network.IAlbumRepository
import com.blueprint.simplekotlinflow.network.RestService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

class AlbumRepository @Inject constructor(private val restAPIService: RestService) :
    IAlbumRepository {
    override fun getAlbums(): Flow<ResultWrapper<List<Album>>> = flow {
        emit(restAPIService.getAlbums())
    }.flowOn(Dispatchers.IO)
}