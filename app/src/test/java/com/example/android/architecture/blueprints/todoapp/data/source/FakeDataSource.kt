package com.example.android.architecture.blueprints.todoapp.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Task

class FakeDataSource(var tasks: MutableList<Task>? = mutableListOf()) : TasksDataSource {

    private val mutableLiveData = MutableLiveData<Result<Task>>()
    private val _liveData: LiveData<Result<Task>> = mutableLiveData

    private val mutableListLiveData = MutableLiveData<Result<List<Task>>>()
    private val _liveDataList: LiveData<Result<List<Task>>> = mutableListLiveData

    override suspend fun saveTask(task: Task) {
        tasks?.add(task)
    }

    override suspend fun deleteTask(taskId: String) {
        tasks?.clear()
    }

    override suspend fun getTasks(): Result<List<Task>> {
        tasks?.let { return Result.Success(ArrayList(it)) }
        return Result.Error(Exception("Tasks not found"))
    }

    override fun observeTasks(): LiveData<Result<List<Task>>> {
        // TODO("Not yet implemented")
        return _liveDataList
    }

    override suspend fun refreshTasks() {
        // TODO("Not yet implemented")
    }

    override fun observeTask(taskId: String): LiveData<Result<Task>> {
        // TODO("Not yet implemented")
        return _liveData
    }

    override suspend fun getTask(taskId: String): Result<Task> {
        // TODO("Not yet implemented")
        return Result.Success(Task())
    }

    override suspend fun refreshTask(taskId: String) {
        // TODO("Not yet implemented")
    }

    override suspend fun completeTask(task: Task) {
        // TODO("Not yet implemented")
    }

    override suspend fun completeTask(taskId: String) {
        // TODO("Not yet implemented")
    }

    override suspend fun activateTask(task: Task) {
        // TODO("Not yet implemented")
    }

    override suspend fun activateTask(taskId: String) {
        // TODO("Not yet implemented")
    }

    override suspend fun clearCompletedTasks() {
        // TODO("Not yet implemented")
    }

    override suspend fun deleteAllTasks() {
        tasks?.clear()
    }

}