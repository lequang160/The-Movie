package com.vtvhyundai.media2359demo.network.api

import com.vtvhyundai.media2359demo.core.BaseApi
import retrofit2.Retrofit

class AppRestApi(
    retrofit: Retrofit
) :
    BaseApi<AppApi>(retrofit) {

    override fun registerApiClassType(): Class<AppApi> {
        return AppApi::class.java
    }

}