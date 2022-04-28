package com.app.authenticationapp.network

import com.app.authenticationapp.base.BaseResponse
import retrofit2.Response
import java.lang.Exception

open class BaseDataSource {

    open suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            when {
                response.code() == 403 -> {
                    return Resource.error("", code = response.code())
                }
                response.code() == 502 -> {
                    return Resource.error("", code = response.code())
                }
                response.body() != null -> {
                    val baseResponse = (response.body() as BaseResponse)
                    if (response.isSuccessful && response.code() == 200 && baseResponse.status) {
                        response.body()?.let {
                            return Resource.success(it, baseResponse.message);
                        }
                    } else {
                        val body = response.body()
                        if (body != null) {
                            return Resource.error(baseResponse.message)
                        }
                    }
                    return error("Success => ${response.code()} ${response.message()}")
                }
            }
        } catch (e: Exception) {
            return error("Error => ${e.message} ?: ${e.toString()}")
        }
        return Resource.error("Something went wrong.")
    }

    private fun <T> error(message: String): Resource<T> {
        return Resource.error(message)
    }
}