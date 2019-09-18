package com.pst.mkt.model

import android.arch.persistence.room.Entity
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "movies", primaryKeys = ["id"])
data class Movie(
    val id: Int,
    val title: String = "",
    val overview: String = "",
    val adult: Boolean = false,
    val popularity: Float = 0f,
    val video: Boolean = false,
    var saved: Boolean = false,
    var keywords: List<Keyword>? = ArrayList(),
    var videos: List<Video>? = ArrayList(),
    var reviews: List<Review>? = ArrayList(),
    @SerializedName("poster_path") val posterPath: String? = null,
    @SerializedName("release_date") val releaseDate: String = "",
    @SerializedName("original_language") val originalLanguage: String = "",
    @SerializedName("original_title") val originalTitle: String = "",
    @SerializedName("backdrop_path") val backdropPath: String? = null,
    @SerializedName("vote_count") val voteCount: Int = 0,
    @SerializedName("vote_average") val voteAverage: Float = 0f
) : Parcelable
