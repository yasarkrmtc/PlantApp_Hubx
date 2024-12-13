package com.plantapphubx.domain.usecase

import com.plantapphubx.data.local.QuestionsUIModel
import com.plantapphubx.domain.repository.ServiceRepository
import kotlinx.coroutines.flow.Flow

class FetchQuestionsUseCase(private val repository: ServiceRepository) {
    fun execute(): Flow<List<QuestionsUIModel>> = repository.getQuestions()
}
