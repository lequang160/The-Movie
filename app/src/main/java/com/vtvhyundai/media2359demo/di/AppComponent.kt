package com.vtvhyundai.media2359demo.di

import com.vtvhyundai.media2359demo.TheMovieApplication
import com.vtvhyundai.media2359demo.di.modules.ActivityBuilder
import com.vtvhyundai.media2359demo.di.modules.AppApiModule
import com.vtvhyundai.media2359demo.di.modules.AppModule
import com.vtvhyundai.media2359demo.di.modules.NetworkModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityBuilder::class,
        NetworkModule::class,
        AppApiModule::class,
        NetworkModule::class]
)
interface AppComponent : AndroidInjector<TheMovieApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<TheMovieApplication>()
}