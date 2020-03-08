package com.vtvhyundai.media2359demo.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieModel(
    @SerializedName("poster_path")
    @Expose
    var posterPath: String? = null,
    @SerializedName("overview")
    @Expose
    var overview: String,
    @SerializedName("release_date")
    @Expose
    var releaseDate: String,
    @SerializedName("id")
    @Expose
    var id: String,
    @SerializedName("original_title")
    @Expose
    var originalTitle: String,
    @SerializedName("title")
    @Expose
    var title: String,
    @SerializedName("backdrop_path")
    @Expose
    var backdropPath: String? = null,
    @SerializedName("vote_average")
    @Expose
    var voteAverage: String
)