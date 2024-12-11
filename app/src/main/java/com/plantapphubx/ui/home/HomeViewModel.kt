package com.plantapphubx.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plantapphubx.data.remote.Questions
import com.plantapphubx.domain.usecase.FetchQuestionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchQuestionsUseCase: FetchQuestionsUseCase
) : ViewModel() {
    private val _questions = MutableLiveData<List<Questions>>()
    val questions: LiveData<List<Questions>> get() = _questions

    fun fetchQuestions() {
        viewModelScope.launch {
            try {
                val result = fetchQuestionsUseCase.execute()
                _questions.postValue(result)
            } catch (e: Exception) {
            }
        }
    }
}
