package com.app.authenticationapp.ui.otp

import android.os.Bundle
import com.app.authenticationapp.BR
import com.app.authenticationapp.R
import com.app.authenticationapp.base.BaseActivity
import com.app.authenticationapp.databinding.ActivitySplashBinding
import com.app.authenticationapp.ui.splash.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtpActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>(),OtpNavigator {

    override val layoutId = R.layout.activity_otp

    override val bindingVariable = BR.viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
    }

    override fun setUpObserver() {

    }

    override fun init() {

    }
}