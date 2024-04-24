package com.dumitrachecristian.surveyapp.ui.initialscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dumitrachecristian.surveyapp.R
import com.dumitrachecristian.surveyapp.navigation.Screen
import com.dumitrachecristian.surveyapp.ui.theme.Typography

@Composable
fun InitialScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(top = 20.dp),
            text = stringResource(R.string.welcome),
            textAlign = TextAlign.Center,
            style = Typography.headlineSmall
        )
        Button(
            modifier = Modifier
                .padding(40.dp)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            onClick = {
                navController.navigate(route = Screen.SurveyScreen.route)
            }
        ) {
            Text(
                stringResource(R.string.start_survey),
                color = Color.Blue
            )
        }
    }
}