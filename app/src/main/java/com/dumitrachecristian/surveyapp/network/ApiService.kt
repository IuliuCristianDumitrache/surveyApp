package com.dumitrachecristian.surveyapp.network


import com.dumitrachecristian.surveyapp.model.QuestionData
import com.dumitrachecristian.surveyapp.model.QuestionRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("questions")
    suspend fun getQuestions(
    ): Response<List<QuestionData>>

    @POST("question/submit")
    suspend fun sendQuestionResponse(
        @Body questionRequest: QuestionRequest
    ): Response<Unit>
}