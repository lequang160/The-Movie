package com.vtvhyundai.media2359demo.ui.main

import androidx.lifecycle.ViewModel
import com.vtvhyundai.media2359demo.di.viewModel.ViewModelKey
import com.vtvhyundai.media2359demo.network.api.AppApi
import com.vtvhyundai.media2359demo.repositories.MovieRepository
import com.vtvhyundai.media2359demo.repositories.MovieRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
abstract class MainModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel


    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideMovieReposytory(appApi: AppApi): MovieRepository = MovieRepositoryImp(appApi)
    }
}