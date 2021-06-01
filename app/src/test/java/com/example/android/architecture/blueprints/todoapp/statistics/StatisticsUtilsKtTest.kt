package com.example.android.architecture.blueprints.todoapp.statistics

import com.example.android.architecture.blueprints.todoapp.data.Task
import org.junit.Assert.assertEquals
import org.junit.Test

class StatisticsUtilsKtTest {

    @Test
    fun getActiveAndCompletedStats_noCompleted_returnZeroHundred() {
        // Given
        val tasks = listOf(Task(title = "name", description = "description", isCompleted = false))

        // When
        val result = getActiveAndCompletedStats(tasks)

        // Then
        assertEquals(0f, result.completedTasksPercent)
        assertEquals(100f, result.activeTasksPercent)
    }

    @Test
    fun getActiveAndCompletedStats_both_returnFortySix() {
        // Given
        val tasks = listOf(
            Task(title = "name", description = "description", isCompleted = true),
            Task(title = "name", description = "description", isCompleted = true),
            Task(title = "name", description = "description", isCompleted = false),
            Task(title = "name", description = "description", isCompleted = false),
            Task(title = "name", description = "description", isCompleted = false),
        )

        // When
        val result = getActiveAndCompletedStats(tasks)

        // Then
        assertEquals(40f, result.completedTasksPercent)
        assertEquals(60f, result.activeTasksPercent)
    }

}