package com.plantapphubx.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Categories(
    @SerialName("data") val data: List<CategoryData>,
    @SerialName("meta") val meta: Meta
)

@Serializable
data class CategoryData(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("createdAt") val createdAt: String,
    @SerialName("updatedAt") val updatedAt: String,
    @SerialName("publishedAt") val publishedAt: String,
    @SerialName("title") val title: String,
    @SerialName("rank") val rank: Int,
    @SerialName("image") val image: Image
)

@Serializable
data class Image(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("alternativeText") val alternativeText: String?,
    @SerialName("caption") val caption: String?,
    @SerialName("width") val width: Int,
    @SerialName("height") val height: Int,
    @SerialName("formats") val formats: String?,
    @SerialName("hash") val hash: String,
    @SerialName("ext") val ext: String,
    @SerialName("mime") val mime: String,
    @SerialName("size") val size: Double,
    @SerialName("url") val url: String,
    @SerialName("previewUrl") val previewUrl: String?,
    @SerialName("provider") val provider: String,
    @SerialName("provider_metadata") val providerMetadata: String?,
    @SerialName("createdAt") val createdAt: String,
    @SerialName("updatedAt") val updatedAt: String
)

@Serializable
data class Meta(
    @SerialName("pagination") val pagination: Pagination
)

@Serializable
data class Pagination(
    @SerialName("page") val page: Int,
    @SerialName("pageSize") val pageSize: Int,
    @SerialName("pageCount") val pageCount: Int,
    @SerialName("total") val total: Int
)