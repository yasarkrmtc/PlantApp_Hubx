package com.plantapphubx.data.remote

import retrofit2.http.GET

interface ServiceInterface {

    @GET("getQuestions")
    suspend fun getQuestions(): List<Questions>
}