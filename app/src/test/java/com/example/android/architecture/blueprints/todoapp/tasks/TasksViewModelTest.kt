package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import junit.framework.TestCase
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * The ViewModel logic code should not rely on the Android Framework or in the Operating System
 */
/**
 * The test runner is a component to run tests. This @RunWith annotation force to not to use the
 * default runner and to use that one defined by you.
 */
@RunWith(AndroidJUnit4::class)
class TasksViewModelTest : TestCase() {

    /**
     * JUnit rules are classes that allow you to define some code that runs before and after each
     * test runs.
     * This rule here, runs all architecture components related background jobs in the same thread.
     * This ensures that the test results happen synchronously and in
     * a repeatable order two things that are pretty important for tests.
     */
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun addNewTask_setsNewTaskEvent() {
        // Given... a fresh viewModel instance
        // The context here is provided by the androidx.test.core.app.ApplicationProvider
        val taskViewModel = TasksViewModel(ApplicationProvider.getApplicationContext())

        // When new task event is triggered
        taskViewModel.addNewTask()

        // Then
        val value = taskViewModel.newTaskEvent.getOrAwaitValue()
        assertThat(value.getContentIfNotHandled(), (not(nullValue())))
    }

    @Test
    fun setFilterAllTasks_tasksAddViewVisible() {
        // Given a fresh ViewModel
        val taskViewModel = TasksViewModel(ApplicationProvider.getApplicationContext())

        // When the filter type is ALL_TASKS
        taskViewModel.setFiltering(requestType = TasksFilterType.ALL_TASKS)

        // Then the "Add task" action is visible
        assertThat(taskViewModel.tasksAddViewVisible.getOrAwaitValue(), `is`(true))
    }
}