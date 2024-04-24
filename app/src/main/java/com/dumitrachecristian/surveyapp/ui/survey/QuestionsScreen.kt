package com.dumitrachecristian.surveyapp.ui.survey

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dumitrachecristian.surveyapp.R
import com.dumitrachecristian.surveyapp.SIDE_EFFECTS_KEY
import com.dumitrachecristian.surveyapp.model.QuestionData
import com.dumitrachecristian.surveyapp.model.QuestionRequest
import com.dumitrachecristian.surveyapp.ui.components.ErrorBanner
import com.dumitrachecristian.surveyapp.ui.components.Progress
import com.dumitrachecristian.surveyapp.ui.components.SuccessBanner
import com.dumitrachecristian.surveyapp.ui.theme.Typography
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Composable
fun QuestionsScreen(
    state: SurveyContract.State,
    effectFlow: Flow<SurveyContract.Effect>?,
    onEventSent: (event: SurveyContract.Event) -> Unit,
    navController: NavHostController
) {
    var showErrorBanner by remember { mutableStateOf(false) }
    var failedRequest: QuestionRequest? = null

    var showSuccessBanner by remember { mutableStateOf(false) }

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is SurveyContract.Effect.DataWasLoaded -> {

                }

                is SurveyContract.Effect.Navigation.ToRepos -> {

                }

                is SurveyContract.Effect.PostAnswerError -> {
                    showErrorBanner = true
                    failedRequest = effect.questionRequest
                }

                is SurveyContract.Effect.PostAnswerSuccess -> {
                    showSuccessBanner = true
                }
            }
        }?.collect()
    }

    Column {
        SuccessBanner(showSuccessBanner) {
            showSuccessBanner = false
        }

        ErrorBanner(showErrorBanner, onButtonClick = {
            failedRequest?.let {
                onEventSent(SurveyContract.Event.PostAnswer(it))
            }
        }, onClose = {
            showErrorBanner = false
            failedRequest = null
        })

        QuestionsViewPager(
            state = state,
            postAnswer = { questionRequest ->
                onEventSent(SurveyContract.Event.PostAnswer(questionRequest))
            },
            navController
        )
    }
    if (state.isLoading) {
        Progress()
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuestionsViewPager(
    state: SurveyContract.State,
    postAnswer: (QuestionRequest) -> Unit,
    navController: NavHostController
) {

    val pagerState = rememberPagerState(
        initialPage = 0
    )
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)) {

            IconButton(onClick = {
                navController.popBackStack()
            }) {

            }

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.questions_number, pagerState.currentPage + 1, state.questions.size)
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.questions_submitted, state.questions.filter { it.alreadyAnswered }.size)
        )

        HorizontalPager(
            pageCount = state.questions.size,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            state = pagerState
        ) { position ->
            QuestionPage(state.questions[position], position, state.questions.size, pagerState, scope, postAnswer)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuestionPage(questionData: QuestionData, position: Int, size: Int, state: PagerState, scope: CoroutineScope, postAnswer: (QuestionRequest) -> Unit) {
    var answerText by rememberSaveable { mutableStateOf(questionData.answer ?: "") }

    Column {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = questionData.question,
            style = Typography.headlineSmall
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            maxLines = 6,
            value = answerText ?: "",
            onValueChange = {
                answerText = it
            },
            enabled = questionData.alreadyAnswered.not()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                modifier = Modifier,
                onClick = {
                    scope.launch {
                        state.scrollToPage(state.currentPage - 1)
                    }
                },
                enabled = position != 0
            ) {
                Text(stringResource(R.string.previous))
            }

            Button(
                modifier = Modifier,
                onClick = {
                    postAnswer.invoke(QuestionRequest(questionData.id, answerText))
                },
                enabled = questionData.alreadyAnswered.not() && answerText.isNotEmpty()
            ) {
                val text = if (questionData.alreadyAnswered) {
                    stringResource(R.string.already_submitted)
                } else {
                    stringResource(R.string.submit)
                }
                Text(text)
            }

            Button(
                modifier = Modifier,
                onClick = {
                    scope.launch {
                        state.scrollToPage(state.currentPage + 1)
                    }
                },
                enabled = position != size - 1
            ) {
                Text(stringResource(R.string.next))
            }
        }
    }
}
