package com.dumitrachecristian.surveyapp.ui.survey

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dumitrachecristian.surveyapp.R
import com.dumitrachecristian.surveyapp.SIDE_EFFECTS_KEY
import com.dumitrachecristian.surveyapp.model.QuestionData
import com.dumitrachecristian.surveyapp.model.QuestionRequest
import com.dumitrachecristian.surveyapp.ui.components.ErrorBanner
import com.dumitrachecristian.surveyapp.ui.components.Progress
import com.dumitrachecristian.surveyapp.ui.components.SuccessBanner
import com.dumitrachecristian.surveyapp.ui.components.SurveyButton
import com.dumitrachecristian.surveyapp.ui.components.SurveyIconButton
import com.dumitrachecristian.surveyapp.ui.theme.Typography
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Composable
fun QuestionsScreen(
    state: QuestionsContract.State,
    effectFlow: Flow<QuestionsContract.Effect>?,
    onEventSent: (event: QuestionsContract.Event) -> Unit,
    onNavigationRequested: (navigationEffect: QuestionsContract.Effect.Navigation) -> Unit
) {
    LaunchedEffect(Unit) {
        onEventSent.invoke(QuestionsContract.Event.GetQuestions)
    }
    var showErrorBanner by remember { mutableStateOf(false) }
    var failedRequest: QuestionRequest? = null

    var showSuccessBanner by remember { mutableStateOf(false) }

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is QuestionsContract.Effect.Navigation.Back -> {
                    onNavigationRequested(QuestionsContract.Effect.Navigation.Back)
                }

                is QuestionsContract.Effect.PostAnswerError -> {
                    showErrorBanner = true
                    failedRequest = effect.questionRequest
                }

                is QuestionsContract.Effect.PostAnswerSuccess -> {
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
                onEventSent(QuestionsContract.Event.PostAnswer(it))
            }
        }, onClose = {
            showErrorBanner = false
            failedRequest = null
        })

        QuestionsViewPager(
            state = state,
            postAnswer = { questionRequest ->
                onEventSent(QuestionsContract.Event.PostAnswer(questionRequest))
            },
            onNavigationRequested
        )
    }
    if (state.isLoading) {
        Progress()
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuestionsViewPager(
    state: QuestionsContract.State,
    postAnswer: (QuestionRequest) -> Unit,
    onNavigationRequested: (navigationEffect: QuestionsContract.Effect.Navigation) -> Unit
) {

    val pagerState = rememberPagerState(
        initialPage = 0
    )
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            SurveyIconButton(
                tint = Color.Black,
                contentDescription = stringResource(R.string.back),
                imageVector = Icons.Default.ArrowBack,
                modifier = Modifier
                    .padding(8.dp)
            ) {
                onNavigationRequested(QuestionsContract.Effect.Navigation.Back)
            }


            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                textAlign = TextAlign.Center,
                text = stringResource(
                    R.string.questions_number,
                    pagerState.currentPage + 1,
                    state.questions.size
                )
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            textAlign = TextAlign.Center,
            text = stringResource(
                R.string.questions_submitted,
                state.questions.filter { it.isAlreadyAnswered() }.size
            )
        )

        HorizontalPager(
            pageCount = state.questions.size,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            state = pagerState
        ) { position ->
            QuestionPage(
                state.questions[position],
                position,
                state.questions.size,
                pagerState,
                scope,
                postAnswer
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuestionPage(
    questionData: QuestionData,
    position: Int,
    size: Int,
    state: PagerState,
    scope: CoroutineScope,
    postAnswer: (QuestionRequest) -> Unit
) {
    var answerText by remember { mutableStateOf("") }

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
            value = answerText.ifEmpty { questionData.answer ?: "" },
            onValueChange = {
                answerText = it
            },
            enabled = questionData.isAlreadyAnswered().not()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SurveyButton(
                text = stringResource(R.string.previous),
                enabled = position != 0
            ) {
                scope.launch {
                    state.scrollToPage(state.currentPage - 1)
                }
            }

            SurveyButton(
                text = if (questionData.isAlreadyAnswered()) {
                    stringResource(R.string.already_submitted)
                } else {
                    stringResource(R.string.submit)
                },
                enabled = questionData.isAlreadyAnswered().not() && answerText.isNotEmpty()
            ) {
                postAnswer.invoke(QuestionRequest(questionData.id, answerText))
            }

            SurveyButton(
                text = stringResource(R.string.next),
                enabled = position != size - 1
            ) {
                scope.launch {
                    state.scrollToPage(state.currentPage + 1)
                }
            }
        }
    }
}
