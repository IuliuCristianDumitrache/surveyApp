package com.dumitrachecristian.surveyapp.data.datasource


import com.dumitrachecristian.surveyapp.model.QuestionData
import com.dumitrachecristian.surveyapp.model.QuestionRequest
import com.dumitrachecristian.surveyapp.network.ApiService
import com.dumitrachecristian.surveyapp.network.Result
import javax.inject.Inject

class SurveyRemoteDataSource @Inject constructor(
    private val apiService: ApiService
): BaseRemoteDataSource {

    suspend fun getQuestions(): Result<List<QuestionData>> {
        return safeApiResult {
            apiService.getQuestions()
        }
    }

    suspend fun sendQuestionResponse(questionRequest: QuestionRequest): Result<Unit> {
        return safeApiResult {
            apiService.sendQuestionResponse(questionRequest)
        }
    }
}