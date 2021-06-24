package com.example.android.architecture.blueprints.todoapp

import android.content.Context
import androidx.room.Room
import com.example.android.architecture.blueprints.todoapp.data.source.DefaultTasksRepository
import com.example.android.architecture.blueprints.todoapp.data.source.TasksDataSource
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository
import com.example.android.architecture.blueprints.todoapp.data.source.local.TasksLocalDataSource
import com.example.android.architecture.blueprints.todoapp.data.source.local.ToDoDatabase
import com.example.android.architecture.blueprints.todoapp.data.source.remote.TasksRemoteDataSource

object ServiceLocator {

    private var database: ToDoDatabase? = null

    /**
     * Marks the JVM backing field of the annotated property as volatile, meaning that writes to
     * this field are immediately made visible to other threads. In others words, it can be used
     * and requested by multiple threads.
     * The '@Volatile' annotation cannot be used on immutable properties
     */
    @Volatile
    var tasksRepository: TasksRepository? = null

    /**
     * This method can never create a repository twice. It may happen if the call can be accessed
     * by multiple threads. To avoid this we use the syncronized key word.
     */
    fun providerTaskRepository(context: Context) : TasksRepository {
        synchronized(this) {
            return tasksRepository ?: createTaskRepository(context = context)
        }
    }

    private fun createTaskRepository(context: Context): TasksRepository {
        val newRepo = DefaultTasksRepository(TasksRemoteDataSource, createTaskLocalDataSource(context))
        tasksRepository = newRepo
        return newRepo
    }

    private fun createTaskLocalDataSource(context: Context): TasksDataSource {
        val database = database ?: createDatabase(context = context)
        return TasksLocalDataSource(tasksDao = database.taskDao())
    }

    private fun createDatabase(context: Context): ToDoDatabase {
        val databaseCreationResult = database ?: Room.databaseBuilder(
            context.applicationContext,
            ToDoDatabase::class.java,
            "Tasks.db"
        ).build()
        database = databaseCreationResult
        return databaseCreationResult
    }

}