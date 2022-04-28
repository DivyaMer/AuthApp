package com.app.authenticationapp.network

import com.app.authenticationapp.model.NewsModel
import com.app.authenticationapp.network.APIConstant.API_USERS
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(API_USERS)
    suspend fun getNewsList(@Query("sources") source : String, @Query("apiKey") apiKey : String): Response<NewsModel>

}