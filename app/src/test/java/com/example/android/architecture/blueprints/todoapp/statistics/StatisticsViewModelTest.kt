package com.example.android.architecture.blueprints.todoapp.statistics

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android.architecture.blueprints.todoapp.MainCoroutineRule
import com.example.android.architecture.blueprints.todoapp.data.source.FakeTaskRepository
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class StatisticsViewModelTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    // Set the main coroutines dispatcher for unit testing.
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // The subject in test
    private lateinit var statisticsViewModel: StatisticsViewModel

    // Use a fake repository tobe injected into tha ViewModel
    private lateinit var tasksRepository: FakeTaskRepository

    @Before
    fun setupStatisticsViewModel() {
        // Initialize the repository with no tasks
        tasksRepository = FakeTaskRepository()
        // Create the ViewModel instance with the Fake Repository
        statisticsViewModel = StatisticsViewModel(tasksRepository = tasksRepository)
    }


    @Test
    fun loadTask_Loading() {

        mainCoroutineRule.pauseDispatcher()

        statisticsViewModel.refresh()

        assertThat(statisticsViewModel.dataLoading.getOrAwaitValue(), `is`(true))

        mainCoroutineRule.resumeDispatcher()

        assertThat(statisticsViewModel.dataLoading.getOrAwaitValue(), `is`(false))
    }
}