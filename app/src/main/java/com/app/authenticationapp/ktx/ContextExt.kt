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
import android.widget.Toast
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.getColorOrThrow
import androidx.core.content.res.getDrawableOrThrow
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