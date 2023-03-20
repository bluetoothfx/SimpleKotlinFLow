package com.blueprint.simplekotlinflow.network

import com.blueprint.simplekotlinflow.components.ResultWrapper
import com.blueprint.simplekotlinflow.models.Album
import kotlinx.coroutines.flow.Flow

interface IAlbumRepository {
    suspend fun getAlbums(): Flow<ResultWrapper<List<Album>>>
}