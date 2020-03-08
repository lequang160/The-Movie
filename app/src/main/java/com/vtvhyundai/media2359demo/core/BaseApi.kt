package com.vtvhyundai.media2359demo.core

import retrofit2.Retrofit

abstract class BaseApi<T>(
    retrofit: Retrofit
) {
    private var mApi: T

    init {
        mApi = retrofit.create(this.registerApiClassType())
    }

    fun getApi(): T {
        return mApi
    }

    abstract fun registerApiClassType(): Class<T>
}