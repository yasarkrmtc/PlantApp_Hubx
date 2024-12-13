package com.plantapphubx.domain.repository

import com.plantapphubx.data.local.CategoryDataUIModel
import com.plantapphubx.data.local.QuestionsUIModel

interface ServiceRepository {
    suspend fun getQuestions() : List<QuestionsUIModel>
    suspend fun getCategories() :List<CategoryDataUIModel>
}