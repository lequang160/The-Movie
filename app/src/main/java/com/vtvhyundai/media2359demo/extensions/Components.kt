package com.vtvhyundai.media2359demo.extensions

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*


fun Fragment.showToast(message: String) {
    Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.goToActivity(
    bundle: Bundle?,
    clazz: Class<*>
) {
    val intent = Intent(this.activity, clazz)
    bundle?.let {
        intent.putExtras(it)
    }
    this.startActivity(intent)
}

fun FloatingActionButton.rotateFab(rotate: Boolean) {
    this.animate().setDuration(200)
        .rotation(if (rotate) 135f else 0f)
}

fun Long.convertTimeStampToString(format: String = "yyyy-MM-dd, HH:mm aa"): String{
    val sdf = SimpleDateFormat(format)
    val date = Date(this)
    return sdf.format(date)
}

