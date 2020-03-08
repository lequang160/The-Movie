package com.vtvhyundai.media2359demo.extensions

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat


fun AppCompatActivity.goToActivity(
    bundle: Bundle?,
    clazz: Class<*>,
    options: ActivityOptionsCompat?
) {
    val intent = Intent(this, clazz)
    bundle?.let {
        intent.putExtras(it)
    }
    if (options != null) {
        this.startActivity(intent, options.toBundle())

    } else {
        this.startActivity(intent)
    }
}

fun AppCompatActivity.goToActivity(
    bundle: Bundle?,
    clazz: Class<*>
) {
    val intent = Intent(this, clazz)
    bundle?.let {
        intent.putExtras(it)
    }
    this.startActivity(intent)
}

fun AppCompatActivity.showToast(
    message: String
) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}


