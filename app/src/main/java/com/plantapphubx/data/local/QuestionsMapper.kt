package com.plantapphubx.data.local

import com.plantapphubx.data.remote.Questions

class QuestionsMapper {

    fun mapToUIModel(questions: List<Questions>): List<QuestionsUIModel> {
        return questions.map { it.toUIModel() }
    }

    private fun Questions.toUIModel(): QuestionsUIModel {
        return QuestionsUIModel(
            id = this.id,
            title = this.title,
            subtitle = this.subtitle,
            imageUri = this.imageUri,
            uri = this.uri,
            order = this.order
        )
    }
}
