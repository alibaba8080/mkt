package com.enesgemci.mymovies.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import model.Review

@Parcelize
data class ReviewResponse(
    val id: Int,
    val results: List<Review>
) : Parcelable
