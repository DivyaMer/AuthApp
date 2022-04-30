package com.app.authenticationapp.ui.login

import alertDialog
import android.os.Bundle
import android.util.Log
import com.app.authenticationapp.BR
import com.app.authenticationapp.R
import com.app.authenticationapp.base.BaseActivity
import com.app.authenticationapp.databinding.ActivityLoginBinding
import com.app.authenticationapp.ktx.hideLoaderDialog
import com.app.authenticationapp.ktx.showLoaderDialog
import com.app.authenticationapp.ktx.startNewActivity
import com.app.authenticationapp.network.Resource
import com.app.authenticationapp.ui.otp.OtpActivity
import dagger.hilt.android.AndroidEntryPoint
import showInternetDialog

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(),LoginNavigator {

    override val layoutId = R.layout.activity_login

    override val bindingVariable = BR.viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel.setNavigator(this)
        Log.e("TAG","login activity")

    }

    override fun setUpObserver() {
        Log.e("TAG","login activity")

        mViewModel.loginResponseObservable.observe(this) {
            Log.e("TAG","login activity")
            when (it.status) {

                Resource.Status.SUCCESS -> {
                    hideLoaderDialog()
                    it.let {
                        login()
                    }
                }

                Resource.Status.ERROR -> {
                    hideLoaderDialog()
                    it.message?.let { message ->
                        alertDialog(message = message) {

                        }
                    }
                }

                Resource.Status.LOADING -> {
                    showLoaderDialog()
                }

                Resource.Status.NO_INTERNET_CONNECTION -> {
                    hideLoaderDialog()
                    showInternetDialog(false)
                }
                else -> {}
            }
        }
    }

    override fun init() {

    }

    override fun login() {
        val bundle = Bundle()
        bundle.putString("phone", mViewModel.mobileNo)
        startNewActivity(OtpActivity::class.java, bundle = bundle, finish = true)
    }
}