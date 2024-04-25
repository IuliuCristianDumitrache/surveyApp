package com.dumitrachecristian.surveyapp.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.coroutines.delay

@Composable
fun SurveyIconButton(
    tint: Color,
    contentDescription: String,
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    var enableAgain by remember { mutableStateOf(true) }

    LaunchedEffect(enableAgain, block = {
        if (enableAgain) return@LaunchedEffect
        delay(timeMillis = 1000L)
        enableAgain = true
    })

    IconButton(
        modifier = modifier,
        onClick = {
            if (enableAgain) {
                enableAgain = false
                onClick()
            }
        }) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = tint
        )
    }
}

@Composable
fun SurveyButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    var enableAgain by remember { mutableStateOf(true) }

    LaunchedEffect(enableAgain, block = {
        if (enableAgain) return@LaunchedEffect
        delay(timeMillis = 500L)
        enableAgain = true
    })

    Button(
        modifier = modifier,
        enabled = enabled,
        onClick = {
            if (enableAgain) {
                enableAgain = false
                onClick()
            }
        }) {
        Text(text)
    }
}