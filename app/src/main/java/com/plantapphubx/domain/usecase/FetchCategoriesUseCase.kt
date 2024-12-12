package com.plantapphubx.domain.usecase

import com.plantapphubx.data.remote.CategoryData
import com.plantapphubx.data.repository.ServiceRepositoryImpl

class FetchCategoriesUseCase(private val repository: ServiceRepositoryImpl) {
    suspend fun execute(): List<CategoryData> = repository.getCategories()
}
