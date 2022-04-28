package com.app.authenticationapp.utils

import android.app.Activity
import android.widget.Toast
import com.app.authenticationapp.R
import com.app.authenticationapp.ktx.checkStringValue

enum class ValidationStatus {

    EMPTY_MOBILENO,
    INVALID_MOBILENO,

}

object Validation {

    fun showError(activity: Activity, validationStatus: ValidationStatus) {
        val message = getMessage(activity, validationStatus)
        if (message.isNotEmpty()) {
            if (checkStringValue(message)) {
               Toast.makeText(activity,message,Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun getMessage(activity: Activity, it: ValidationStatus): String {
        return when (it) {

            ValidationStatus.EMPTY_MOBILENO -> activity.getString(R.string.please_enter_mobile_no)
            ValidationStatus.INVALID_MOBILENO -> activity.getString(R.string.please_enter_valid_mobile_no)

            else -> ""
        }
    }
}
