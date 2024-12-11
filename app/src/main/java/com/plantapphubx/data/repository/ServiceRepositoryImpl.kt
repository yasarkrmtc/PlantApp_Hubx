package com.plantapphubx.data.repository

import com.plantapphubx.data.remote.Questions
import com.plantapphubx.data.remote.ServiceInterface

class ServiceRepositoryImpl(private val apiService: ServiceInterface) {

    suspend fun getQuestions(): List<Questions> {
        return apiService.getQuestions()
    }
}