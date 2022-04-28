package com.app.authenticationapp.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.authenticationapp.network.BaseDataSource
import com.app.authenticationapp.utils.ValidationStatus
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.ref.WeakReference
import javax.inject.Inject

abstract class BaseViewModel<N> : ViewModel() {

    lateinit var mNavigator : WeakReference<N>

    private var gson : Gson? = null

    @Inject
    lateinit var baseDataSource: BaseDataSource

    fun getNavigator() : N? {
        return mNavigator.get()
    }

    fun setNavigator(navigator : N){
        mNavigator = WeakReference(navigator)
    }

    //set common validator
    val validationObserver = MutableLiveData<ValidationStatus>()

    fun showMessage(status: ValidationStatus) {
        validationObserver.value = status
    }

    protected open fun <T> getStringFromObject(requestObject: T): Map<String, String> {
        gson = Gson()
        val jsonString: String = gson!!.toJson(requestObject)
        val mapType = object : TypeToken<Map<String?, String?>?>() {}.type
        return gson!!.fromJson(jsonString, mapType)
    }
}