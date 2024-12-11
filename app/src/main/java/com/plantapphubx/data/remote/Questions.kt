package com.plantapphubx.data.remote

import com.google.gson.annotations.SerializedName

data class Questions(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("subtitle") val subtitle: String,
    @SerializedName("image_uri") val imageUri: String?,
    @SerializedName("uri") val uri: String?,
    @SerializedName("order") val order: Int
)