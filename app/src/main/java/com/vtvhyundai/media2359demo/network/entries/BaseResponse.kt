package com.vtvhyundai.media2359demo.network.entries

abstract class BaseResponse<T> {
    val message: String? = null
    val success: Boolean = false
    val results: T? = null
}