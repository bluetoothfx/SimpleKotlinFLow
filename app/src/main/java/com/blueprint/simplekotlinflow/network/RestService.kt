package com.blueprint.simplekotlinflow.network

import com.blueprint.simplekotlinflow.components.smartApiCall
import javax.inject.Inject

class RestService @Inject constructor(private val restCaller: RestCall){

    //get Albums
    suspend fun getAlbums() = smartApiCall { restCaller.getAlbums() }

    //get Posts
    suspend fun getPosts() = smartApiCall { restCaller.getPosts() }

    suspend fun getPost() = smartApiCall { restCaller.getPost() }

}