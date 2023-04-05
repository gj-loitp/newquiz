package com.infinitepower.newquiz.model.comparison_quiz

import android.net.Uri
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.jupiter.api.Test

internal class ComparisonQuizDataTest {
    @Test
    fun `nextQuestion should return a new ComparisonQuizData object with a new current question`() {
        mockkStatic(Uri::class)
        val uriMock = mockk<Uri>()
        every { Uri.parse("test/path") } returns uriMock

        val quizItem1 = ComparisonQuizItem(
            title = "A",
            imgUri = uriMock,
            value = 10.0
        )

        val quizItem2 = ComparisonQuizItem(
            title = "B",
            imgUri = uriMock,
            value = 5.0
        )

        val quizData = ComparisonQuizData(
            questions = listOf(quizItem1, quizItem2),
            currentQuestion = null,
            gameDescription = "Which country has more population?",
            comparisonMode = ComparisonModeByFirst.GREATER
        )

        assertThat(quizData.questions).containsExactly(quizItem1, quizItem2)
        assertThat(quizData.currentQuestion).isNull()

        val nextQuizData = quizData.nextQuestion()

        assertThat(nextQuizData.questions).containsNoneOf(quizItem1, quizItem2)
        assertThat(nextQuizData.questions).isEmpty()
        assertThat(nextQuizData.currentQuestion).isNotNull()
    }

    @Test
    fun `nextQuestion should return null currentQuestion when questions list is empty`() {
        val quizData = ComparisonQuizData(
            questions = emptyList(),
            currentQuestion = null,
            gameDescription = "Which country has more population?",
            comparisonMode = ComparisonModeByFirst.GREATER
        )

        assertThat(quizData.questions).isEmpty()
        assertThat(quizData.currentQuestion).isNull()

        val nextQuizData = quizData.nextQuestion()

        assertThat(nextQuizData.questions).isEmpty()
        assertThat(nextQuizData.currentQuestion).isNull()
    }

    @Test
    fun `test nextQuestion when questions has size of 3`() {
        mockkStatic(Uri::class)
        val uriMock = mockk<Uri>()
        every { Uri.parse("test/path") } returns uriMock

        val quizItem1 = ComparisonQuizItem(
            title = "A",
            imgUri = uriMock,
            value = 10.0
        )

        val quizItem2 = ComparisonQuizItem(
            title = "B",
            imgUri = uriMock,
            value = 5.0
        )

        val quizItem3 = ComparisonQuizItem(
            title = "C",
            imgUri = uriMock,
            value = 8.0
        )

        val quizData = ComparisonQuizData(
            questions = listOf(quizItem1, quizItem2, quizItem3),
            currentQuestion = null,
            gameDescription = "Which country has more population?",
            comparisonMode = ComparisonModeByFirst.GREATER
        )

        assertThat(quizData.questions).containsExactly(quizItem1, quizItem2, quizItem3)
        assertThat(quizData.currentQuestion).isNull()

        val nextQuizData = quizData.nextQuestion()

        assertThat(nextQuizData.questions).containsExactly(quizItem3)
        assertThat(nextQuizData.questions).containsNoneOf(quizItem1, quizItem2)
        assertThat(nextQuizData.currentQuestion).isNotNull()
    }

    @Test
    fun `test nextQuestion when questions has size of 1 and current question is not null`() {
        mockkStatic(Uri::class)
        val uriMock = mockk<Uri>()
        every { Uri.parse("test/path") } returns uriMock

        val quizItem1 = ComparisonQuizItem(
            title = "A",
            imgUri = uriMock,
            value = 10.0
        )

        val quizItem2 = ComparisonQuizItem(
            title = "B",
            imgUri = uriMock,
            value = 5.0
        )

        val quizItem3 = ComparisonQuizItem(
            title = "C",
            imgUri = uriMock,
            value = 8.0
        )

        val quizData = ComparisonQuizData(
            questions = listOf(quizItem3),
            currentQuestion = ComparisonQuizCurrentQuestion(quizItem1 to quizItem2),
            gameDescription = "Which country has more population?",
            comparisonMode = ComparisonModeByFirst.GREATER
        )

        assertThat(quizData.questions).containsExactly(quizItem3)
        assertThat(quizData.currentQuestion).isNotNull()
        assertThat(quizData.currentQuestion?.questions?.first).isEqualTo(quizItem1)
        assertThat(quizData.currentQuestion?.questions?.second).isEqualTo(quizItem2)

        val nextQuizData = quizData.nextQuestion()

        assertThat(nextQuizData.questions).isEmpty()
        assertThat(nextQuizData.questions).containsNoneOf(quizItem1, quizItem2, quizItem3)
        assertThat(nextQuizData.currentQuestion).isNotNull()
        assertThat(nextQuizData.currentQuestion?.questions?.first).isEqualTo(quizItem2)
        assertThat(nextQuizData.currentQuestion?.questions?.second).isEqualTo(quizItem3)
    }
}