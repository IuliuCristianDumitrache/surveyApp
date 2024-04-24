package com.dumitrachecristian.surveyapp.navigation


import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.dumitrachecristian.surveyapp.MainViewModel
import com.dumitrachecristian.surveyapp.ui.initialscreen.InitialScreen
import com.dumitrachecristian.surveyapp.ui.survey.QuestionsScreen


fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
    viewModel: MainViewModel,
) {
    navigation(
        startDestination = Screen.MainScreen.route,
        route = MAIN_ROUTE,
    ) {
        composable(route = Screen.MainScreen.route) {
            InitialScreen(navController = navController)
        }

        composable(
            route = Screen.SurveyScreen.route
        ) {
            QuestionsScreen(
                state = viewModel.viewState.value,
                effectFlow = viewModel.effect,
                onEventSent = { event ->  viewModel.setEvent(event) },
                navController = navController
            )
        }
    }
}