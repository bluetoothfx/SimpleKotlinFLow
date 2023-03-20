package com.blueprint.simplekotlinflow.repository

import com.blueprint.simplekotlinflow.components.ResultWrapper
import com.blueprint.simplekotlinflow.models.Album
import com.blueprint.simplekotlinflow.network.IAlbumRepository
import com.blueprint.simplekotlinflow.network.RestService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

class AlbumRepository @Inject constructor(private val restAPIService: RestService, )
    : IAlbumRepository {

    override suspend fun getAlbums(): Flow<ResultWrapper<List<Album>>> {
        return restAPIService.getAlbums()
    }

}