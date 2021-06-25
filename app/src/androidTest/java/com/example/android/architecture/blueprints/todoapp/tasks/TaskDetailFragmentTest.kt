package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.ServiceLocator
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.FakeAndroidTestRepository
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository
import com.example.android.architecture.blueprints.todoapp.taskdetail.TaskDetailFragment
import com.example.android.architecture.blueprints.todoapp.taskdetail.TaskDetailFragmentArgs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * So @MediumTest marks this as a test that takes a medium amount of time to run.
 * Generally, unit tests are annotated with @SmallTest.
 * Integration tests are considered median run time tests.
 * End-to-end tests are annotated with a @LargeTest annotation.
 */
@MediumTest
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class TaskDetailFragmentTest {

    private lateinit var repository: TasksRepository

    @Before
    fun initRepository() {
        repository = FakeAndroidTestRepository()
        ServiceLocator.tasksRepository = repository
    }

    @After
    fun cleanupDatabase() = runBlockingTest {
        ServiceLocator.resetRepository()
    }

    @Test
    fun activeTaskDetail_DisplayInUi() = runBlockingTest {
        // Given
        val activeTask = Task(
            title = "Title",
            description = "Description",
            isCompleted = false
        )

        repository.saveTask(activeTask)

        // When
        val bundle = TaskDetailFragmentArgs(activeTask.id).toBundle()
        /**
         * The reason to explicitly give this AppTheme, it's because when using
         * the launchFragmentInContainer(), the fragment is launched in an empty activity .
         * Because fragments usually inherit their theme from the activity. So this is a way to
         * ensure the the fragment under test have the correct theme.
         *
         * The reason to explicitly give this AppTheme, it's because when using
         * the launchFragmentInContainer(), the fragment is launched in an empty activity .
         * Because fragments usually inherit their theme from the activity. So this is a way to
         * ensure the the fragment under test have the correct theme.
         */
        launchFragmentInContainer<TaskDetailFragment>(bundle, R.style.AppTheme)

        Thread.sleep(4000)

    }

}