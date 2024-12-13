package com.plantapphubx.domain.repository

import com.plantapphubx.data.local.CategoryDataUIModel
import com.plantapphubx.data.local.QuestionsUIModel
import kotlinx.coroutines.flow.Flow

interface ServiceRepository {
    fun getQuestions(): Flow<List<QuestionsUIModel>>
    fun getCategories(): Flow<List<CategoryDataUIModel>>
}
