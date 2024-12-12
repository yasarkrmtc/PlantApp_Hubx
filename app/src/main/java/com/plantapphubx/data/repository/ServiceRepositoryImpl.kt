package com.plantapphubx.data.repository

import com.plantapphubx.data.remote.ServiceInterface

class  ServiceRepositoryImpl(private val api: ServiceInterface) {

    //adfsdsfsdfsdf
    suspend fun getQuestions() = api.getQuestions()

    suspend fun getCategories() = api.getCategories().data // Extract `data` here
}
