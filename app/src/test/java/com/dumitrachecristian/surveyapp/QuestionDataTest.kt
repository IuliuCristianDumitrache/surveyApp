package com.dumitrachecristian.surveyapp

import com.dumitrachecristian.surveyapp.model.QuestionData
import org.junit.Test

import org.junit.Assert.*

class QuestionDataTest {

    @Test
    fun testIsAlreadyAnswered() {
        val questionDataWithAnswer = QuestionData(1, "What is your favorite color?", "Red")
        assertTrue(questionDataWithAnswer.isAlreadyAnswered())

        val questionDataWithoutAnswer = QuestionData(2, "What is your favorite color?")
        assertFalse(questionDataWithoutAnswer.isAlreadyAnswered())
    }
}