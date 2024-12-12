package com.plantapphubx.data.repository

import com.plantapphubx.data.local.CategoriesMapper
import com.plantapphubx.data.local.CategoryDataUIModel
import com.plantapphubx.data.local.QuestionsMapper
import com.plantapphubx.data.local.QuestionsUIModel
import com.plantapphubx.data.remote.ServiceInterface
import com.plantapphubx.domain.repository.ServiceRepository


class  ServiceRepositoryImpl(private val api: ServiceInterface) :ServiceRepository {

    private val questionsMapper = QuestionsMapper()
    private val categoriesMapper = CategoriesMapper()

    override suspend fun getQuestions(): List<QuestionsUIModel> {
        return try {
            val questions = api.getQuestions()
            questionsMapper.mapToUIModel(questions)
        } catch (e: Exception) {
            emptyList()
        }
    }


    override suspend fun getCategories(): List<CategoryDataUIModel> {
        return try {
            val categories = api.getCategories()
            categoriesMapper.mapToUIModel(categories)
        } catch (e: Exception) {
            emptyList()
        }
    }
}


