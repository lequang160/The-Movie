package com.vtvhyundai.media2359demo.extensions

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.ColorRes
import java.lang.ref.WeakReference

fun Context.hasNetwork(): Boolean? {
    var isConnected: Boolean? = false // Initial Value
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    if (activeNetwork != null && activeNetwork.isConnected)
        isConnected = true
    return isConnected
}

val Context.screenWidth: Int
    get() = resources.displayMetrics.widthPixels

val Context.screenHeight: Int
    get() = resources.displayMetrics.heightPixels

fun Context.setStatusBarColor(context: WeakReference<Activity>, @ColorRes colorResId: Int) {

    if (Build.VERSION.SDK_INT >= 21) {
        val window = context.get()?.window
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            window?.statusBarColor = context.get()!!.resources.getColor(colorResId)
        } else {
            window?.statusBarColor = resources.getColor(colorResId, null)
        }
    }

}

fun Context.showToast(msg: String) {
    Toast.makeText(
        this, msg,
        Toast.LENGTH_SHORT
    ).show()
}