package com.app.authenticationapp.model


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
) {
    data class Data(
        @SerializedName("contact_number")
        val contactNumber: String,
        @SerializedName("country_code")
        val countryCode: String,
        @SerializedName("otp")
        val otp: String
    )
}