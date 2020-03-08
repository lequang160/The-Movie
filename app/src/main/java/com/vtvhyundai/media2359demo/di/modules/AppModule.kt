package com.vtvhyundai.media2359demo.di.modules

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.vtvhyundai.media2359demo.TheMovieApplication
import com.vtvhyundai.media2359demo.di.viewModel.ViewModelFactory
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun provideApplication(app: TheMovieApplication): Context

    @Binds
    abstract fun provideViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}