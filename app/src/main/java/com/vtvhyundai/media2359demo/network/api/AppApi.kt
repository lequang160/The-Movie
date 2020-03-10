package com.vtvhyundai.media2359demo.network.api

import com.vtvhyundai.media2359demo.network.entries.BaseResponse
import com.vtvhyundai.media2359demo.network.entries.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface AppApi {

    @GET("movie/now_playing?language=en-US")
    suspend fun fetchMovieNowPlaying(@Query("page") page: Int): MovieResponse

    @GET("")
    suspend fun fetchMovieDetail(): BaseResponse<Any>
}