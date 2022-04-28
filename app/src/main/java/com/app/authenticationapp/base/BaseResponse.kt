package com.app.authenticationapp.base

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class BaseResponse(
    @SerializedName("message")
    @Expose
    val message: String = "",
    @SerializedName("status")
    @Expose
    val status: Boolean = false,
)