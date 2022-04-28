package com.app.authenticationapp.ktx

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.getColorOrThrow
import androidx.core.content.res.getDrawableOrThrow
import com.app.authenticationapp.utils.AppConstants
import java.util.*

fun Context.getInteger(@IntegerRes resId: Int) =
    resources.getInteger(resId)

fun Context.getDimenSize(@DimenRes resId: Int) =
    resources.getDimensionPixelSize(resId)

fun Context.getCompatColor(@ColorRes resId: Int) =
    ContextCompat.getColor(this, resId)

fun Context.isActivityFinishing(): Boolean {
    return this is Activity && isFinishing
}

fun Context.isActivityDestroyed(): Boolean {
    return this is Activity && isDestroyed
}

fun Context.toast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, resId, duration).show()
}

fun Context.toast(text: String?, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text.orEmpty(), duration).show()
}

fun Context.getCurrentLocale(): Locale? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        resources.configuration.locales[0]
    } else {
        resources.configuration.locale
    }
}

fun Context.getProgressBarDrawable(): Drawable {
    val value = TypedValue()
    theme.resolveAttribute(android.R.attr.progressBarStyleSmall, value, false)
    val progressBarStyle = value.data
    val attributes = intArrayOf(android.R.attr.indeterminateDrawable)
    val array = obtainStyledAttributes(progressBarStyle, attributes)
    val drawable = array.getDrawableOrThrow(0)
    array.recycle()
    return drawable
}

fun Context.fetchPrimaryColor(): Int {
    val array = obtainStyledAttributes(intArrayOf(android.R.attr.colorPrimary))
    val color = array.getColorOrThrow(0)
    array.recycle()
    return color
}

val Context.appVersionName: String?
    get() = packageManager.getPackageInfo(packageName, 0).versionName

/**
 * Returns the name (label) of the application or the package name as a fallback.
 */
val Context.appName: String
get() = packageManager.getApplicationLabel(applicationInfo).toString()

@RequiresPermission(value = Manifest.permission.ACCESS_NETWORK_STATE)
fun Context.isNetworkConnected(): Boolean {
    val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
    connectivityManager?.let {
        val netInfo = it.activeNetworkInfo
        netInfo?.let {
            if (it.isConnected) return true
        }
    }
    return false
}

fun <T> Activity.startNewActivity(
    className: Class<T>,
    finish: Boolean = false,
    bundle: Bundle? = null,
    flag: Boolean = false
) {
    val intent = Intent(this, className)
    bundle?.let {
        intent.putExtras(it)
    }
    if (flag){
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
    }
    startActivity(intent)
    if (finish) {
        finish()
    }
}

fun Context.hideKeyboard() {
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    //Find the currently focused view, so we can grab the correct window token from it.
    val activity = this as Activity
    var view = activity.currentFocus
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(this)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun checkStringValue(value: String?): Boolean {
    if (value.isNullOrEmpty()) {
        return false
    }
    return true
}

//check mobile validation

fun String.isMobileNoValid(): Boolean {
    return this.length < AppConstants.MOBILE_NO_LIMIT
}

//loader dialog

fun Context.showLoaderDialog() {
    LoadingDialog.showLoadDialog(this)
}

fun Context.hideLoaderDialog() {
    LoadingDialog.hideLoadDialog();
}