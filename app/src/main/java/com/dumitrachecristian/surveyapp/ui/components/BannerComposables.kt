package com.dumitrachecristian.surveyapp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dumitrachecristian.surveyapp.R
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SuccessBanner(isVisible: Boolean, onClose: () -> Unit) {
    Banner(isVisible = isVisible, background = Color.Green, textColor = Color.Black, text = stringResource(
        R.string.success), buttonClick = null, onClose = onClose)
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ErrorBanner(isVisible: Boolean, onClose: () -> Unit, onButtonClick: () -> Unit) {
    Banner(isVisible = isVisible, background = Color.Red, textColor = Color.White, text = stringResource(R.string.failure), buttonText = stringResource(R.string.retry),
        buttonClick = {
            onButtonClick.invoke()
        }, onClose = {
            onClose.invoke()
        }
    )
}

@ExperimentalAnimationApi
@Composable
private fun Banner(isVisible: Boolean, background: Color, textColor: Color, text: String, buttonText: String? = null, buttonClick: (() -> Unit)? = null, onClose: () -> Unit) {
    if (isVisible) {
        LaunchedEffect(Unit) {
            delay(3000)
            onClose.invoke()
        }
        AnimatedVisibility(
            visible = isVisible,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(background)
            ) {
                Text(
                    modifier = Modifier.padding(12.dp),
                    text = text,
                    color = textColor
                )

                buttonText?.let {
                    Row(
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .align(Alignment.End)
                    ) {
                        TextButton(
                            modifier = Modifier.padding(end = 8.dp),
                            onClick = {
                                onClose.invoke()
                                buttonClick?.invoke()
                            }
                        ) {
                            Text(
                                text = buttonText,
                                color = textColor
                            )
                        }
                    }
                }
            }
        }
    }
}
