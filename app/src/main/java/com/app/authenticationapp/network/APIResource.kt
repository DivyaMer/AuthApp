package com.app.authenticationapp.network

data class APIResource<out T>(val status: APIStatus, val data: T?, val message: String?, val type: Int) {

    companion object {
        fun <T> success(data: T?): APIResource<T> {
            return APIResource(APIStatus.SUCCESS, data, null,0)
        }

        fun <T> error(msg: String, data: T?,type : Int = 1): APIResource<T> {
            return APIResource(APIStatus.ERROR, data, msg,type)
        }

        fun <T> loading(data: T?): APIResource<T> {
            return APIResource(APIStatus.LOADING, data, null,0)
        }
    }
}
