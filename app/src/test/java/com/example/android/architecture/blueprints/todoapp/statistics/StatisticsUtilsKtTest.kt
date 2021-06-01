package com.example.android.architecture.blueprints.todoapp.statistics

import com.example.android.architecture.blueprints.todoapp.data.Task
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
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

    @Test
    fun getActiveAndCompletedStats_empty_returnZeros() {
        // Given
        val tasks = emptyList<Task>()

        // When
        val result = getActiveAndCompletedStats(tasks)

        // Then
        assertEquals(0f, result.completedTasksPercent)
        assertEquals(0f, result.activeTasksPercent)
    }

    @Test
    fun getActiveAndCompletedStats_error_returnZeros() {
        // Given
        val tasks = null

        // When
        val result = getActiveAndCompletedStats(tasks)

        // Then
        assertEquals(0f, result.completedTasksPercent)
        assertEquals(0f, result.activeTasksPercent)
    }

    /**
     * Using Hamcrest assertions
     */

    @Test
    fun getActiveAndCompletedStats_empty_returnZeros_withhamCrest() {
        // Given
        val tasks = emptyList<Task>()

        // When
        val result = getActiveAndCompletedStats(tasks)

        // Then
        assertThat(result.completedTasksPercent, `is`(0f))
        assertThat(result.activeTasksPercent, `is`(0f))
    }

    @Test
    fun getActiveAndCompletedStats_error_returnZeros_withHamcrestAssertions() {
        // Given
        val tasks = null

        // When
        val result = getActiveAndCompletedStats(tasks)

        // Then
        assertThat(result.completedTasksPercent, `is`(0f))
        assertThat(result.activeTasksPercent, `is`(0f))
    }

}