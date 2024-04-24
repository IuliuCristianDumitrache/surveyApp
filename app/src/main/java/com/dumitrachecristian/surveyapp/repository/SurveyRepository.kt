package com.dumitrachecristian.surveyapp.repository

import com.dumitrachecristian.surveyapp.data.datasource.SurveyRemoteDataSource
import com.dumitrachecristian.surveyapp.model.QuestionData
import com.dumitrachecristian.surveyapp.model.QuestionRequest
import com.dumitrachecristian.surveyapp.network.Result
import javax.inject.Inject

class SurveyRepository @Inject constructor(
    private val surveyRemoteDataSource: SurveyRemoteDataSource
) {

    suspend fun sendQuestionResponse(questionRequest: QuestionRequest): Result<Unit> {
        return surveyRemoteDataSource.sendQuestionResponse(questionRequest)
    }

    suspend fun getQuestion(): Result<List<QuestionData>> {
        return surveyRemoteDataSource.getQuestions()
    }
}