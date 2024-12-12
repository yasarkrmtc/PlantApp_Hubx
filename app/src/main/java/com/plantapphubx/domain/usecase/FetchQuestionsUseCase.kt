package com.plantapphubx.domain.usecase

import com.plantapphubx.data.local.QuestionsUIModel
import com.plantapphubx.data.remote.Questions
import com.plantapphubx.data.repository.ServiceRepositoryImpl

class FetchQuestionsUseCase(private val repository: ServiceRepositoryImpl) {

    suspend fun execute(): List<QuestionsUIModel> {
        return repository.getQuestions()
    }
}