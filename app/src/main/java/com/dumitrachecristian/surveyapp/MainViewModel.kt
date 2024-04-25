package com.dumitrachecristian.surveyapp

import androidx.lifecycle.viewModelScope
import com.dumitrachecristian.surveyapp.model.QuestionRequest
import com.dumitrachecristian.surveyapp.network.Result
import com.dumitrachecristian.surveyapp.repository.SurveyRepository
import com.dumitrachecristian.surveyapp.ui.survey.QuestionsContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: SurveyRepository,
): BaseViewModel<QuestionsContract.Event, QuestionsContract.State, QuestionsContract.Effect>() {

    override fun setInitialState() = QuestionsContract.State(
        questions = emptyList(),
        isLoading = true,
    )

    override fun handleEvents(event: QuestionsContract.Event) {
        when (event) {
            is QuestionsContract.Event.GetQuestions -> getQuestions()
            is QuestionsContract.Event.PostAnswer -> {
                postAnswer(event.questionRequest)
            }
        }
    }

    private fun getQuestions() {
        viewModelScope.launch {
            when (val result = repository.getQuestion()) {
                is Result.Success -> {
                    result.data?.let { questionList ->
                        setState {
                            copy(
                                questions = questionList,
                                isLoading = false
                            )
                        }
                    }
                }

                is Result.Error -> {
                    setState {
                        copy(
                            isLoading = false,
                        )
                    }
                }

                is Result.Loading -> {
                    setState { copy(isLoading = true) }
                }
            }
        }
    }


    private fun postAnswer(questionRequest: QuestionRequest) {
        viewModelScope.launch {
            when (val result = repository.sendQuestionResponse(questionRequest)) {
                is Result.Success -> {
                        val questions = viewState.value.questions
                        questions.find { it.id == questionRequest.id }?.apply {
                            this.answer = questionRequest.answer
                        }
                        setState {
                            copy(
                                questions = questions,
                                isLoading = false,
                            )
                        }
                        setEffect { QuestionsContract.Effect.PostAnswerSuccess }
                }

                is Result.Error -> {
                    setState {
                        copy(
                            isLoading = false,
                        )
                    }
                    setEffect { QuestionsContract.Effect.PostAnswerError(questionRequest) }
                }

                is Result.Loading -> {
                    setState { copy(isLoading = true) }
                }
            }
        }

    }

}