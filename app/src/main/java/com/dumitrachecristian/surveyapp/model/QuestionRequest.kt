package com.dumitrachecristian.surveyapp.model

import com.google.gson.annotations.SerializedName

data class QuestionRequest(
    @SerializedName("id") val id: Int,
    @SerializedName("answer") var answer: String
)