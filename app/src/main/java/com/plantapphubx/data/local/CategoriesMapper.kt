package com.plantapphubx.data.local

import com.plantapphubx.data.remote.Categories
import com.plantapphubx.data.remote.CategoryData
import com.plantapphubx.data.remote.Image
import com.plantapphubx.data.remote.Meta
import com.plantapphubx.data.remote.Pagination
class CategoriesMapper {

    fun mapToUIModel(categories: Categories): List<CategoryDataUIModel> {
        return categories.data.map { it.toUIModel() }  // Mapping CategoryData to CategoryDataUIModel
    }

    fun CategoryData.toUIModel(): CategoryDataUIModel {
        return CategoryDataUIModel(
            id = this.id,
            name = this.name,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
            publishedAt = this.publishedAt,
            title = this.title,
            rank = this.rank,
            image = this.image.toUIModel()
        )
    }

    private fun Image.toUIModel(): ImageUIModel {
        return ImageUIModel(
            id = this.id,
            name = this.name,
            alternativeText = this.alternativeText,
            caption = this.caption,
            width = this.width,
            height = this.height,
            formats = this.formats,
            hash = this.hash,
            ext = this.ext,
            mime = this.mime,
            size = this.size,
            url = this.url,
            previewUrl = this.previewUrl,
            provider = this.provider,
            providerMetadata = this.providerMetadata,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt
        )
    }

    private fun Meta.toUIModel(): MetaUIModel {
        return MetaUIModel(
            pagination = this.pagination.toUIModel()
        )
    }

    private fun Pagination.toUIModel(): PaginationUIModel {
        return PaginationUIModel(
            page = this.page,
            pageSize = this.pageSize,
            pageCount = this.pageCount,
            total = this.total
        )
    }
}

