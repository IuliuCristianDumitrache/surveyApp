package com.dumitrachecristian.surveyapp.model

import com.google.gson.annotations.SerializedName

data class QuestionData(
    @SerializedName("id")
    val id: Int,
    @SerializedName("question")
    val question: String,
    var answer: String? = null,
) {
    fun isAlreadyAnswered(): Boolean {
        return this.answer.isNullOrEmpty().not()
    }
}