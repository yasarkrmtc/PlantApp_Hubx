package com.plantapphubx.data.repository

import com.plantapphubx.data.local.CategoriesMapper
import com.plantapphubx.data.local.CategoriesUIModel
import com.plantapphubx.data.local.CategoryDataUIModel
import com.plantapphubx.data.local.MetaUIModel
import com.plantapphubx.data.local.PaginationUIModel
import com.plantapphubx.data.local.QuestionsMapper
import com.plantapphubx.data.local.QuestionsUIModel
import com.plantapphubx.data.remote.ServiceInterface
import com.plantapphubx.domain.repository.ServiceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class  ServiceRepositoryImpl(private val api: ServiceInterface) :ServiceRepository {

    private val questionsMapper = QuestionsMapper()
    private val categoriesMapper = CategoriesMapper()

    override suspend fun getQuestions(): List<QuestionsUIModel> {
        return try {
            val questions = api.getQuestions()  // Fetch questions from the API
            questionsMapper.mapToUIModel(questions)  // Map to UIModel using the mapper
        } catch (e: Exception) {
            emptyList()  // Return an empty list in case of an error
        }
    }


    override suspend fun getCategories(): List<CategoryDataUIModel> {
        return try {
            val categories = api.getCategories()  // Fetch categories from the API
            categoriesMapper.mapToUIModel(categories)  // Use CategoriesMapper to map the data
        } catch (e: Exception) {
            emptyList()  // Return an empty list in case of an error
        }
    }
}


