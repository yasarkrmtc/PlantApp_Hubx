package com.plantapphubx.data.repository

import com.plantapphubx.data.mapper.CategoriesMapper
import com.plantapphubx.data.local.CategoryDataUIModel
import com.plantapphubx.data.mapper.QuestionsMapper
import com.plantapphubx.data.local.QuestionsUIModel
import com.plantapphubx.data.remote.ServiceInterface
import com.plantapphubx.domain.repository.ServiceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class ServiceRepositoryImpl(private val api: ServiceInterface) : ServiceRepository {

    private val questionsMapper = QuestionsMapper()
    private val categoriesMapper = CategoriesMapper()

    override fun getQuestions(): Flow<List<QuestionsUIModel>> = flow {
        val questions = api.getQuestions()
        emit(questionsMapper.mapToUIModel(questions))
    }.catch { emit(emptyList()) }

    override fun getCategories(): Flow<List<CategoryDataUIModel>> = flow {
        val categories = api.getCategories()
        emit(categoriesMapper.mapToUIModel(categories))
    }.catch { emit(emptyList()) }
}
