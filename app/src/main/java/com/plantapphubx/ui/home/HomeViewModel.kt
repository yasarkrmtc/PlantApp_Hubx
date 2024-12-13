package com.plantapphubx.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plantapphubx.data.local.CategoryDataUIModel
import com.plantapphubx.data.local.QuestionsUIModel
import com.plantapphubx.domain.usecase.FetchCategoriesUseCase
import com.plantapphubx.domain.usecase.FetchQuestionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchQuestionsUseCase: FetchQuestionsUseCase,
    private val fetchCategoriesUseCase: FetchCategoriesUseCase
) : ViewModel() {

    private val _questions = MutableStateFlow<List<QuestionsUIModel>>(emptyList())
    val questions: StateFlow<List<QuestionsUIModel>> get() = _questions

    private val _categories = MutableStateFlow<List<CategoryDataUIModel>>(emptyList())
    val categories: StateFlow<List<CategoryDataUIModel>> get() = _categories

    fun fetchQuestions() {
        viewModelScope.launch {
            fetchQuestionsUseCase.execute().collect { result ->
                _questions.value = result
            }
        }
    }

    fun fetchCategories() {
        viewModelScope.launch {
            fetchCategoriesUseCase.execute().collect { result ->
                _categories.value = result
            }
        }
    }

    fun filterCategories(query: String): List<CategoryDataUIModel> {
        return _categories.value.filter { category ->
            category.title.contains(query, ignoreCase = true)
        }
    }
}
