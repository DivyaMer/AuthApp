package com.app.authenticationapp.ktx

import android.util.Log

object Logger {

    const val tag = "Tatvasoft_Tag"

    fun v(tag: String?, msg: String?) {
//        if (BuildConfig.DEBUG)
            Log.v(tag, msg ?: "")
    }

    fun v(msg: String?) {
//        if (BuildConfig.DEBUG)
            Log.v(tag, msg ?: "")
    }

    fun d(tag: String?, msg: String?) {
//        if (BuildConfig.DEBUG)
            Log.d(tag, msg ?: "")
    }

    fun d(msg: String?) {
//        if (BuildConfig.DEBUG)
            Log.d(tag, msg ?: "")
    }

    fun i(tag: String?, msg: String?) {
//        if (BuildConfig.DEBUG)
            Log.i(tag, msg ?: "")
    }

    fun i(msg: String?) {
//        if (BuildConfig.DEBUG)
            Log.i(tag, msg ?: "")
    }

    fun w(tag: String?, msg: String?) {
//        if (BuildConfig.DEBUG)
            Log.w(tag, msg ?: "")
    }

    fun w(msg: String?) {
//        if (BuildConfig.DEBUG)
            Log.w(tag, msg ?: "")
    }

    fun e(tag: String?, msg: String?) {
//        if (BuildConfig.DEBUG)
            Log.e(tag, msg ?: "")
    }

    fun e(msg: String?) {
//        if (BuildConfig.DEBUG)
            Log.e(tag, msg ?: "")
    }

    fun e(message: String?, e: Exception) {
        Log.e(tag, "$message :", e)
    }

    fun e(message: String?, e: Throwable) {
        Log.e(tag, "$message :", e)
    }

    fun e(e: Exception) {
        Log.e(tag, e.message, e)
    }
}