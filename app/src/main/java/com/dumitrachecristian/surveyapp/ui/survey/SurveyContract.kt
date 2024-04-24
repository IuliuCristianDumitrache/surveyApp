package com.dumitrachecristian.surveyapp.ui.survey

import com.dumitrachecristian.surveyapp.ViewEvent
import com.dumitrachecristian.surveyapp.ViewSideEffect
import com.dumitrachecristian.surveyapp.ViewState
import com.dumitrachecristian.surveyapp.model.QuestionData
import com.dumitrachecristian.surveyapp.model.QuestionRequest

class SurveyContract {

    sealed class Event : ViewEvent {
        object GetQuestions : Event()
        data class PostAnswer(val questionRequest: QuestionRequest) : Event()
    }

    data class State(
        val questions: List<QuestionData>,
        val isLoading: Boolean,
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        object DataWasLoaded : Effect()

        object PostAnswerSuccess: Effect()

        data class PostAnswerError(val questionRequest: QuestionRequest): Effect()

        sealed class Navigation : Effect() {
            data class ToRepos(val userId: String): Navigation()
        }
    }

}