package com.example.android.architecture.blueprints.todoapp.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.isSucceeded
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Medium "integration" test (as seen by the @MediumTest annotation), because the
 * TasksLocalDataSourceTest will test both the code in TasksLocalDataSource and how it integrates
 * with the DAO code.
 */
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class TasksLocalDataSourceTest {

    private lateinit var localDataSource: TasksLocalDataSource
    private lateinit var database: ToDoDatabase

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initDatabase() {
        /**
         * Disables the main thread query check for Room.
         * <p>
         * Room ensures that Database is never accessed on the main thread because it may lock the
         * main thread and trigger an ANR. If you need to access the database from the main thread,
         * you should always use async alternatives or manually move the call to a background
         * thread.
         * <p>
         * You may want to turn this check off for testing.
         *
         * @return This {@link Builder} instance.
         */
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ToDoDatabase::class.java
        )
        .allowMainThreadQueries()
        .build()

        localDataSource = TasksLocalDataSource(
            tasksDao = database.taskDao(),
            dispatcher = Dispatchers.Main
        )
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun saveTest_retrievesTak() = runBlocking {
        // GIVEN - A new task save in the database
        val newTask = Task("NewTaskTitle", "NewTaskDescription", false)
        localDataSource.saveTask(task = newTask)

        // WHEN
        val result: Result<Task> = localDataSource.getTask(newTask.id)

        // THEN
        assertThat(result.isSucceeded, `is`(true))

        result as Result.Success
        assertThat(result.data.title, `is`("NewTaskTitle"))
        assertThat(result.data.description, `is`("NewTaskDescription"))
        assertThat(result.data.isCompleted, `is`(false))
    }

    @Test
    fun completeTask_retrievedTaskIsComplete() = runBlocking {
        // GIVEN - Save a new active task in the local data source.
        val newIncompleteTask = Task("New Task",  "Incomplete Task", false)
        localDataSource.saveTask(task = newIncompleteTask)

        // WHEN
        // 2. Mark it as complete.
        localDataSource.completeTask(newIncompleteTask.id)
        val result: Result<Task> = localDataSource.getTask(newIncompleteTask.id)

        // THEN - Check that the task can be retrieved from the local data source and is complete.
        assertThat(result.isSucceeded, `is`(true))
        result as Result.Success
        assertThat(result.data.isCompleted, `is`(true))
    }

}