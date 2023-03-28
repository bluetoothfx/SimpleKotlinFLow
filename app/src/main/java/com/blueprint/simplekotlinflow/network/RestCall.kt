package com.blueprint.simplekotlinflow.network

import com.blueprint.simplekotlinflow.models.Album
import com.blueprint.simplekotlinflow.models.Post
import retrofit2.Response
import retrofit2.http.GET

interface RestCall {

    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }

    @GET("photos")
    suspend fun getAlbums(): Response<List<Album>>

    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>

    @GET("posts/1")
    suspend fun getPost(): Response<Post>
}