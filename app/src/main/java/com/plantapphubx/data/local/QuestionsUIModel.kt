package com.plantapphubx.data.local


data class QuestionsUIModel(
    val id: Int,
    val title: String,
    val subtitle: String,
    val imageUri: String?,
    val uri: String?,
    val order: Int
)