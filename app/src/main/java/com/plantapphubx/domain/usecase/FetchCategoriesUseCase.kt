package com.plantapphubx.domain.usecase

import com.plantapphubx.data.local.CategoryDataUIModel
import com.plantapphubx.data.repository.ServiceRepositoryImpl

class FetchCategoriesUseCase(private val repository: ServiceRepositoryImpl) {
    suspend fun execute(): List<CategoryDataUIModel> = repository.getCategories()
}
