package com.example.android.architecture.blueprints.todoapp.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.android.architecture.blueprints.todoapp.data.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class TaskDaoTest {

    /**
     * A JUnit Test Rule that swaps the background executor used by the Architecture Components
     * with a different one which executes each task synchronously.
     *
     * You can use this rule for your host side tests that use Architecture Components.
     */
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ToDoDatabase

    @Before
    fun initLocalDataBase() {
        /**
         * Creates a RoomDatabase.Builder for an in memory database. Information stored in an in memory
         * database disappears when the process is killed.
         * Once a database is built, you should keep a reference to it and re-use it.
         *
         * Context - The context for the database. This is usually the Application context.
         * klass   - The abstract class which is annotated with {@link Database} and extends
         *                {@link RoomDatabase}.
         * @param <T>     The type of the database class.
         * @return A {@code RoomDatabaseBuilder<T>} which you can use to create the database.
         */
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ToDoDatabase::class.java
        ).build()
    }

    @After
    fun closeLocalDataBase() = database.close()


    @Test
    fun insertTaskAndGetById() = runBlocking {
        // GIVEN - Insert a task.
        val task = Task("title", "description")
        database.taskDao().insertTask(task)

        // WHEN - Get the task by id from the database.
        val loaded = database.taskDao().getTaskById(task.id)

        // Then - The loaded data contains the expected values
        assertThat(loaded as Task, notNullValue())
        assertThat(loaded.id, `is`(task.id))
        assertThat(loaded.title, `is`(task.title))
        assertThat(loaded.description, `is`(task.description))
        assertThat(loaded.isCompleted, `is`(task.isCompleted))
    }

}