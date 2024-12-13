package com.plantapphubx.data.local


data class CategoriesUIModel(
    val data: List<CategoryDataUIModel>,
    val meta: MetaUIModel
)

data class CategoryDataUIModel(
    val id: Int,
    val name: String,
    val createdAt: String,
    val updatedAt: String,
    val publishedAt: String,
    val title: String,
    val rank: Int,
    val image: ImageUIModel
)

data class ImageUIModel(
    val id: Int,
    val name: String,
    val alternativeText: String?,
    val caption: String?,
    val width: Int,
    val height: Int,
    val formats: String?,
    val hash: String,
    val ext: String,
    val mime: String,
    val size: Double,
    val url: String,
    val previewUrl: String?,
    val provider: String,
    val providerMetadata: String?,
    val createdAt: String,
    val updatedAt: String
)

data class MetaUIModel(
    val pagination: PaginationUIModel
)

data class PaginationUIModel(
    val page: Int,
    val pageSize: Int,
    val pageCount: Int,
    val total: Int
)