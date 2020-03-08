package com.vtvhyundai.media2359demo.extensions

import java.util.regex.Pattern

fun String?.checkIsNullOrEmpty() : Boolean{
    return !this.isNullOrEmpty() && !this.equals("null",ignoreCase = true)
}

fun  String.removeFirstLastChar(): String = this.substring(1, (this.length - 1))

val EMAIL_ADDRESS_PATTERN: Pattern = Pattern
    .compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
            + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\."
            + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+")
fun String.isValidEmail(): Boolean {
    return EMAIL_ADDRESS_PATTERN.matcher(this).matches()
}