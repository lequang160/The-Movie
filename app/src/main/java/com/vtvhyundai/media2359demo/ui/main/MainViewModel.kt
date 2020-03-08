package com.vtvhyundai.media2359demo.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vtvhyundai.media2359demo.repositories.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainViewModel @Inject constructor(val movieRepository: MovieRepository) :ViewModel(){

    val liveData: MutableLiveData<Any> = MutableLiveData()
    fun fetchMovie(){
        CoroutineScope(Dispatchers.IO).launch {
            val result = movieRepository.fetchMovie("")
            withContext(Dispatchers.Main){
                liveData.postValue(result)
            }
        }
    }
}