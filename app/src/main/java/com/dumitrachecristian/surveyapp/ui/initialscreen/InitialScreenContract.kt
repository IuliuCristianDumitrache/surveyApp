package com.dumitrachecristian.surveyapp.ui.initialscreen

import com.dumitrachecristian.surveyapp.ViewSideEffect

class InitialScreenContract {
    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object ToQuestionsScreen: Navigation()
        }
    }
}