package com.dumitrachecristian.surveyapp.navigation

const val ROOT_ROUTE = "root"
const val MAIN_ROUTE = "main"

sealed class Screen(
    val route: String,
) {
    object MainScreen: Screen("main_screen")
    object QuestionsScreen: Screen("questions_screen")
}