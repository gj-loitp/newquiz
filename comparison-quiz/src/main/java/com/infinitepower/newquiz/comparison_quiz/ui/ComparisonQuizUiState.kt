package com.infinitepower.newquiz.comparison_quiz.ui

import androidx.annotation.Keep
import com.infinitepower.newquiz.model.comparison_quiz.ComparisonQuizCategory
import com.infinitepower.newquiz.model.comparison_quiz.ComparisonQuizCurrentQuestion

@Keep
data class ComparisonQuizUiState(
    val currentQuestion: ComparisonQuizCurrentQuestion? = null,
    val gameCategory: ComparisonQuizCategory? = null,
    val gameDescription: String? = null,
    val currentPosition: Int = 0,
    val highestPosition: Int = 0,
    val isGameOver: Boolean = false
)