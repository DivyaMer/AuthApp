package com.app.authenticationapp.network

import com.app.authenticationapp.model.LoginResponse
import com.app.authenticationapp.network.APIConstant.LOGIN_ENDPOINT
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface NetworkService {

    @POST(LOGIN_ENDPOINT)
    suspend fun login(@Body paramLogin: Map<String, String>): Response<LoginResponse>
}