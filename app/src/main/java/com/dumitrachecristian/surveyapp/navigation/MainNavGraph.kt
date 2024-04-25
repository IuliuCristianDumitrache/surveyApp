package com.dumitrachecristian.surveyapp.navigation


import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.dumitrachecristian.surveyapp.MainViewModel
import com.dumitrachecristian.surveyapp.ui.initialscreen.InitialScreen
import com.dumitrachecristian.surveyapp.ui.initialscreen.InitialScreenContract
import com.dumitrachecristian.surveyapp.ui.survey.QuestionsScreen
import com.dumitrachecristian.surveyapp.ui.survey.QuestionsContract


fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
    viewModel: MainViewModel,
) {
    navigation(
        startDestination = Screen.MainScreen.route,
        route = MAIN_ROUTE,
    ) {
        composable(route = Screen.MainScreen.route) {
            InitialScreen(
                onNavigationRequested = { navigationEffect ->
                    if (navigationEffect is InitialScreenContract.Effect.Navigation.ToQuestionsScreen) {
                        navController.navigate(Screen.QuestionsScreen.route)
                    }
                }
            )
        }

        composable(
            route = Screen.QuestionsScreen.route
        ) {
            QuestionsScreen(
                state = viewModel.viewState.value,
                effectFlow = viewModel.effect,
                onEventSent = { event ->  viewModel.setEvent(event) },
                onNavigationRequested = { navigationEffect ->
                    if (navigationEffect is QuestionsContract.Effect.Navigation.Back) {
                        navController.popBackStack()
                    }
                }
            )
        }
    }
}