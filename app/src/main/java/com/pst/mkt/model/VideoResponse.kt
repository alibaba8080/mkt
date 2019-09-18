package com.pst.mkt.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VideoResponse(
    val id: Int,
    val results: List<Video>
) : Parcelable
