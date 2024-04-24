package com.dumitrachecristian.surveyapp.model

import com.google.gson.annotations.SerializedName

data class QuestionData(
    @SerializedName("id")
    val id: Int,
    @SerializedName("question")
    val question: String,
    @Transient
    var answer: String = "",
    @Transient
    var alreadyAnswered: Boolean = false,
)