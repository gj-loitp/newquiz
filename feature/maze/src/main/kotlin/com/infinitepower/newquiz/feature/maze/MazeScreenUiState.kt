package com.infinitepower.newquiz.feature.maze

import androidx.annotation.Keep
import com.infinitepower.newquiz.model.maze.MazeQuiz
import com.infinitepower.newquiz.model.maze.emptyMaze

@Keep
data class MazeScreenUiState(
    val maze: MazeQuiz = emptyMaze(),
    val autoScrollToCurrentItem: Boolean = true,
    val loading: Boolean = true,
    val generatingMaze: Boolean = false,
) {
    val isMazeEmpty: Boolean
        get() = maze.items.isEmpty()

    val mazeSeed: Int?
        get() = maze.items.firstOrNull()?.mazeSeed
}
