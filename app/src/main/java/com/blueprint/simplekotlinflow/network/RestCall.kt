package com.blueprint.simplekotlinflow.network

import com.blueprint.simplekotlinflow.models.Album
import retrofit2.Response
import retrofit2.http.GET

interface RestCall {

    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }

    @GET("photos")
    suspend fun getAlbums(): Response<List<Album>>
}