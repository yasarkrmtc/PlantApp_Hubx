package com.plantapphubx.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plantapphubx.data.local.CategoryDataUIModel
import com.plantapphubx.data.local.QuestionsUIModel
import com.plantapphubx.data.remote.CategoryData
import com.plantapphubx.data.remote.Questions
import com.plantapphubx.domain.usecase.FetchCategoriesUseCase
import com.plantapphubx.domain.usecase.FetchQuestionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchQuestionsUseCase: FetchQuestionsUseCase,
    private val fetchCategoriesUseCase: FetchCategoriesUseCase
) : ViewModel() {

    private val _questions = MutableLiveData<List<QuestionsUIModel>>()
    val questions: LiveData<List<QuestionsUIModel>> get() = _questions

    private val _categories = MutableLiveData<List<CategoryDataUIModel>>()
    val categories: LiveData<List<CategoryDataUIModel>> get() = _categories

    fun fetchQuestions() {
        viewModelScope.launch {
            try {
                _questions.postValue(fetchQuestionsUseCase.execute())
            } catch (e: Exception) { }
        }
    }

    fun fetchCategories() {
        viewModelScope.launch {
            try {
                val result = fetchCategoriesUseCase.execute()
                Log.d("HomeViewModel", "Fetched Categories: $result")
                _categories.postValue(result)
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error fetching categories", e)
            }
        }
    }
}

