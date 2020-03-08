package com.vtvhyundai.media2359demo.repositories

import com.vtvhyundai.media2359demo.core.ResultState
import com.vtvhyundai.media2359demo.models.MovieModel
import com.vtvhyundai.media2359demo.network.api.AppApi


interface MovieRepository {
    suspend fun fetchMovie(param: Any): ResultState<List<MovieModel>>
}


class MovieRepositoryImp(private val appApi: AppApi) : MovieRepository, BaseRepository() {
    override suspend fun fetchMovie(param: Any): ResultState<List<MovieModel>> {
        when (val response = safeApiCall { appApi.fetchMovieNowPlaying() }) {
            is ResultState.Success -> {
                if (response.data.results != null) {
                    return ResultState.Success(response.data.results)
                }
                return ResultState.Success(arrayListOf())
            }
            is ResultState.Error -> return response
        }
    }

}