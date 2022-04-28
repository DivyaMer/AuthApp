package com.app.authenticationapp.base

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

abstract class BaseActivity<T : ViewDataBinding, V : ViewModel> : AppCompatActivity(), CoroutineScope {

    lateinit var activity: Activity
    abstract val layoutId: Int
    abstract val bindingVariable: Int

    override val coroutineContext = Dispatchers.IO

    abstract fun setUpObserver()

    abstract fun init()

    @Inject
    lateinit var mViewModel: V
    lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
    }

    private fun performDataBinding() {
        activity = this

        binding = DataBindingUtil.setContentView(activity, layoutId)
        binding.setVariable(bindingVariable, mViewModel)
        binding.executePendingBindings()

        init()

        setUpObserver()
    }

    fun showSnackbar(msg: String) {
        //binding.root.showSnackbar(msg)
    }

    fun delay(delay: Long, function: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed(function, delay)
    }

    fun getRandomId(length: Int): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
}