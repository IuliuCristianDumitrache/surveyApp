package com.dumitrachecristian.surveyapp.ui.initialscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.dumitrachecristian.surveyapp.MainViewModel
import com.dumitrachecristian.surveyapp.navigation.Screen

@Composable
fun InitialScreen(navController: NavHostController, viewModel: MainViewModel) {
    Column {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Welcome",
            fontSize = 25.sp
        )
        Button(
            modifier = Modifier,
            onClick = {
                navController.navigate(route = Screen.SurveyScreen.route)
            }
        ) {
            Text("Start Survey")
        }
    }
}