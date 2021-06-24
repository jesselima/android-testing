package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.taskdetail.TaskDetailFragment
import com.example.android.architecture.blueprints.todoapp.taskdetail.TaskDetailFragmentArgs
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
class TasksFragmentTest {

    @Test
    fun activeTaskDetail_DisplayInUi() {
        // Given
        val activeTask = Task(
            title = "Title",
            description = "Description",
            isCompleted = false
        )

        // When
        val bundle = TaskDetailFragmentArgs(activeTask.id).toBundle()
        /**
         * The reason to explicitly give this AppTheme, it's because when using
         * the launchFragmentInContainer(), the fragment is launched in an empty activity .
         * Because fragments usually inherit their theme from the activity. So this is a way to
         * ensure the the fragment under test have the correct theme.
         */
        launchFragmentInContainer<TaskDetailFragment>(bundle, R.style.AppTheme)

        Thread.sleep(4000)

    }

}