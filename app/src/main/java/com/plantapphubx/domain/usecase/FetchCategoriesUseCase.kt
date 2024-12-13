package com.plantapphubx.domain.usecase

import com.plantapphubx.data.local.CategoryDataUIModel
import com.plantapphubx.domain.repository.ServiceRepository
import kotlinx.coroutines.flow.Flow

class FetchCategoriesUseCase(private val repository: ServiceRepository) {
    fun execute(): Flow<List<CategoryDataUIModel>> = repository.getCategories()
}
