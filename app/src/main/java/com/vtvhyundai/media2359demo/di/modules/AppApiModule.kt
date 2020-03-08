package com.vtvhyundai.media2359demo.di.modules

import com.vtvhyundai.media2359demo.network.api.AppApi
import com.vtvhyundai.media2359demo.network.api.AppRestApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class AppApiModule {
    @Provides
    @Singleton
    fun provideAppApi(
        retrofit: Retrofit
    ): AppApi = AppRestApi(retrofit).getApi()
}