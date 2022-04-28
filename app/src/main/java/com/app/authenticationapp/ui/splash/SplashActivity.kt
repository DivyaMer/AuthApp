package com.app.authenticationapp.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.app.authenticationapp.BR
import com.app.authenticationapp.R
import com.app.authenticationapp.base.BaseActivity
import com.app.authenticationapp.databinding.ActivitySplashBinding
import com.app.authenticationapp.ktx.startNewActivity
import com.app.authenticationapp.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>(),SplashNavigator {

    override val layoutId = R.layout.activity_splash

    override val bindingVariable = BR.viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        moveToNext()
    }

    private fun moveToNext() {
        Handler(Looper.getMainLooper()).postDelayed({
            startNewActivity(LoginActivity::class.java, true)
        }, 3000)
    }

    override fun setUpObserver() {
    }

    override fun init() {
    }
}