package com.app.authenticationapp.ui.login

import android.app.Activity
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.authenticationapp.base.BaseViewModel
import com.app.authenticationapp.ktx.isMobileNoValid
import com.app.authenticationapp.model.LoginResponse
import com.app.authenticationapp.network.APIConstant
import com.app.authenticationapp.network.NetWorkUtils
import com.app.authenticationapp.network.NetworkService
import com.app.authenticationapp.network.Resource
import com.app.authenticationapp.utils.ValidationStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val networkService: NetworkService,
                                         var context: Activity
) : BaseViewModel<LoginNavigator>() {

    var countryCode = "+91"
    var mobileNo = ""

    var loginResponseObservable = MutableLiveData<Resource<LoginResponse>>()

    fun login() {
        Log.e("TAG","login");
        //getNavigator()?.login()

        if (NetWorkUtils.isNetworkConnected(context)) {

            if (isValidDetail()) {
                var response: Response<LoginResponse>
                val paramsLogin: HashMap<String, String> = HashMap()
                paramsLogin[APIConstant.WEB_CONTACT_NUMBER] = mobileNo
                paramsLogin[APIConstant.WEB_COUNTRY_CODE] = countryCode

                viewModelScope.launch {
                    loginResponseObservable.value = Resource.loading(null)
                    withContext(Dispatchers.IO) {
                        response = networkService.login(paramsLogin)
                    }
                    withContext(Dispatchers.Main) {
                        response.run {
                            loginResponseObservable.value = baseDataSource.getResult { this }
                        }
                    }
                }
            }
        } else {
            viewModelScope.launch {
                withContext(Dispatchers.Main) {
                    loginResponseObservable.value = Resource.noInternetConnection(null)
                }
            }
        }
    }

    private fun isValidDetail(): Boolean {
        when {
            mobileNo.isEmpty() -> {
                showMessage(ValidationStatus.EMPTY_MOBILENO)

            }
            mobileNo.isMobileNoValid() -> {
                showMessage(ValidationStatus.INVALID_MOBILENO)

            }

            else -> {
                return true
            }
        }
        return false
    }
}