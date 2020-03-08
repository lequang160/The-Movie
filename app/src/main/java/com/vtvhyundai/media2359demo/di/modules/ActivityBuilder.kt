package com.vtvhyundai.media2359demo.di.modules


import com.vtvhyundai.media2359demo.ui.main.MainActivity
import com.vtvhyundai.media2359demo.ui.main.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun bindMainActivity(): MainActivity


}