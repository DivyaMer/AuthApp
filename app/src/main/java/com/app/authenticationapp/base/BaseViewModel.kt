package com.app.authenticationapp.base

import androidx.lifecycle.ViewModel
import com.app.authenticationapp.network.ApiService
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {
    @Inject
    lateinit var provideApiService: ApiService
}