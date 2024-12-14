package com.plantapphubx.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plantapphubx.data.local.CategoryDataUIModel
import com.plantapphubx.data.local.QuestionsUIModel
import com.plantapphubx.domain.usecase.FetchCategoriesUseCase
import com.plantapphubx.domain.usecase.FetchQuestionsUseCase
import com.plantapphubx.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchQuestionsUseCase: FetchQuestionsUseCase,
    private val fetchCategoriesUseCase: FetchCategoriesUseCase
) : ViewModel() {

    private val _questions = MutableStateFlow<List<QuestionsUIModel>>(emptyList())
    var questions = _questions.asStateFlow()

    private val _categories = MutableStateFlow<List<CategoryDataUIModel>>(emptyList())
    val categories = _categories.asStateFlow()

    private val _loadingState = MutableStateFlow(false)
    val loadingState = _loadingState.asStateFlow()

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState  = _errorState.asStateFlow()

    fun clearErrorState() {
        _errorState.value = null
    }

    fun fetchQuestions() {
        executeWithState(
            useCase = { fetchQuestionsUseCase.execute() },
            onSuccess = { result -> _questions.value = result },
            errorMessage = Constants.ERROR_FETCHING_QUESTIONS
        )
    }

    fun fetchCategories() {
        executeWithState(
            useCase = { fetchCategoriesUseCase.execute() },
            onSuccess = { result -> _categories.value = result },
            errorMessage = Constants.ERROR_FETCHING_CATEGORIES
        )
    }

    fun filterCategories(query: String): List<CategoryDataUIModel> {
        return _categories.value.filter { category ->
            category.title.contains(query, ignoreCase = true)
        }
    }

    private fun <T> executeWithState(
        useCase: suspend () -> kotlinx.coroutines.flow.Flow<T>,
        onSuccess: (T) -> Unit,
        errorMessage: String
    ) {
        viewModelScope.launch {
            useCase()
                .onStart { _loadingState.value = true }
                .catch { exception ->
                    _errorState.value = errorMessage + (exception.localizedMessage ?: Constants.GENERIC_ERROR)
                    _loadingState.value = false
                }
                .collect { result ->
                    _loadingState.value = false
                    onSuccess(result)
                }
        }
    }
}
