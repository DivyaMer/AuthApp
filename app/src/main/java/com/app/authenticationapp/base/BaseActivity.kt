package com.app.authenticationapp.base

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.app.authenticationapp.ktx.hideKeyboard
import com.app.authenticationapp.prefs.UserPreference
import com.google.gson.Gson
import javax.inject.Inject

abstract class BaseActivity<T : ViewDataBinding, V : ViewModel> : AppCompatActivity() {

    lateinit var activity: Activity
    abstract val layoutId: Int
    abstract val bindingVariable: Int

    abstract fun setUpObserver()

    abstract fun init()

    @Inject
    lateinit var mViewModel: V
    lateinit var binding: T

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
    }

    private fun performDataBinding() {
        activity = this

        binding = DataBindingUtil.setContentView(activity, layoutId)
        binding.setVariable(bindingVariable, mViewModel)
        binding.executePendingBindings()
        setUpObserver()

        init()

    }
    override fun onResume() {
        super.onResume()

        hideKeyboard()
    }

    override fun onBackPressed() {
        super.onBackPressed()

        hideKeyboard()
    }
}