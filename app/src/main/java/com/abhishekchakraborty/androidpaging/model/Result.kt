package com.abhishekchakraborty.androidpaging.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResultData(
    @SerializedName("country")
    val country: List<String>,
    @SerializedName("category")
    val category: List<String>,
    @SerializedName("content")
    val content: String?,
    @SerializedName("description")
    val description: String,
    @SerializedName("image_url")
    val image_url: String?,
    @SerializedName("language")
    val language: String,
    @SerializedName("link")
    val link: String,
    @SerializedName("pubDate")
    val pubDate: String,
    @SerializedName("source_id")
    val source_id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("video_url")
    val video_url: String?
): Parcelable