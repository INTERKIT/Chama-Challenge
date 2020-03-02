package com.chama.challenge.heritages.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Heritage(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("year")
    val year: Int,
    @SerializedName("target")
    val target: String,
    @SerializedName("region")
    val region: String,
    @SerializedName("regionLong")
    val regionLong: String,
    @SerializedName("coordinates")
    val coordinates: String,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double,
    @SerializedName("page")
    val page: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("shortInfo")
    val shortInfo: String,
    @SerializedName("longInfo")
    val longInfo: String
) : Parcelable