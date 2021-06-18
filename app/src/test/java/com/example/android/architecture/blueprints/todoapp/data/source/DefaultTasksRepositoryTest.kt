package com.example.android.architecture.blueprints.todoapp.data.source

import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Test

class DefaultTasksRepositoryTest {
    private val task1 = Task(title = "Title 1", description = "Description 1")
    private val task2 = Task(title = "Title 2", description = "Description 2")
    private val task3 = Task(title = "Title 3", description = "Description 3")

    private val remoteTasks = listOf(task1, task2).sortedBy { it.id }
    private val localTasks = listOf(task3).sortedBy { it.id }
    private val newTasks = listOf(task3).sortedBy { it.id }

    private lateinit var taskLocalDataSource: FakeDataSource
    private lateinit var taskRemoteDataSource: FakeDataSource

    // The class under test
    private lateinit var taskRepository: DefaultTasksRepository

    @Before
    fun createRepository() {
        taskRemoteDataSource = FakeDataSource(tasks = remoteTasks.toMutableList())
        taskLocalDataSource = FakeDataSource(tasks = localTasks.toMutableList())

        taskRepository = DefaultTasksRepository(
            taskRemoteDataSource,
            taskLocalDataSource,
            Dispatchers.Unconfined
        )
    }

    /**
     * The function runBlockingTest {} runs that block of code in a special test coroutine context
     * which makes sure that the code is run synchronously and immediately.
     *
     * It also essentially makes your coroutines run like non co-routines.
     * So it's really only meant for testing code.
     *
     * Use runBlockingTest { } in your test classes when you are calling a suspend function.
     */
    @ExperimentalCoroutinesApi
    @Test
    fun getTasks_requestAllTasksFRomRemoteDatasource() = runBlockingTest {
        // When tasks are requested from the tasks repository
        val tasks = taskRepository.getTasks(forceUpdate = true) as Result.Success

        // Then tasks are loaded from the remote data source
        assertThat(tasks.data, IsEqual(remoteTasks))
    }

}