package com.vtvhyundai.media2359demo.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vtvhyundai.media2359demo.core.ResultState
import com.vtvhyundai.media2359demo.models.MovieModel
import com.vtvhyundai.media2359demo.repositories.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainViewModel @Inject constructor(val movieRepository: MovieRepository) :ViewModel(){

    private val _movieList: MutableLiveData<List<MovieModel>> = MutableLiveData()
    val movieList: LiveData<List<MovieModel>> = _movieList
    val _errorLiveData: MutableLiveData<String> = MutableLiveData()
    val errorLiveData: LiveData<String> = _errorLiveData
    fun fetchMovie(page: Int){
        CoroutineScope(Dispatchers.IO).launch {
            val result = movieRepository.fetchMovie(page = page)
            withContext(Dispatchers.Main){
                when(result){
                    is ResultState.Success -> _movieList.postValue(result.data)
                    is ResultState.Error -> _errorLiveData.postValue(result.exception.message)
                }

            }
        }
    }
}