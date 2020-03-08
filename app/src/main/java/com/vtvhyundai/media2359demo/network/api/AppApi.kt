package com.vtvhyundai.media2359demo.network.api

import com.vtvhyundai.media2359demo.network.entries.BaseResponse
import com.vtvhyundai.media2359demo.network.entries.MovieResponse
import retrofit2.http.GET


interface AppApi {

    @GET("movie/now_playing")
    suspend fun fetchMovieNowPlaying(): MovieResponse

    @GET("")
    suspend fun fetchMovieDetail(): BaseResponse<Any>
}