package com.abhishekchakraborty.androidpaging.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsList(
    @SerializedName("nextPage")
    val nextPage: Int,
    @SerializedName("results")
    val results: List<ResultData>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
): Parcelable