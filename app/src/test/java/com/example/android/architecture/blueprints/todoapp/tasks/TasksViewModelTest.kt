package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.FakeTaskRepository
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import junit.framework.TestCase
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * The ViewModel logic code should not rely on the Android Framework or in the Operating System
 */
/**
 * The test runner is a component to run tests. This @RunWith annotation force to not to use the
 * default runner and to use that one defined by you.
 *
 * We need @RunWith(AndroidJUnit4::class) only when using AndroidTest code. By now the ViewModel to
 * receive a Application instance on it's constructor
 */
@RunWith(JUnit4::class)
class TasksViewModelTest : TestCase() {

    private lateinit var taskRepository: FakeTaskRepository
    private lateinit var taskViewModel: TasksViewModel

    @Before
    fun setupViewModel() {
        taskRepository = FakeTaskRepository().apply {
            addTasks(Task("Title 1", "Description1"))
            addTasks(Task("Title 1", "Description1", true))
            addTasks(Task("Title 1", "Description1", true))
        }
        taskViewModel = TasksViewModel(tasksRepository = taskRepository)
    }

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
        // When new task event is triggered
        taskViewModel.addNewTask()

        // Then
        val value = taskViewModel.newTaskEvent.getOrAwaitValue()
        assertThat(value.getContentIfNotHandled(), (not(nullValue())))
    }

    @Test
    fun setFilterAllTasks_tasksAddViewVisible() {
        // When the filter type is ALL_TASKS
        taskViewModel.setFiltering(requestType = TasksFilterType.ALL_TASKS)

        // Then the "Add task" action is visible
        assertThat(taskViewModel.tasksAddViewVisible.getOrAwaitValue(), `is`(true))
    }
}