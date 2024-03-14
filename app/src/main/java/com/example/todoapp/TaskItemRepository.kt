package com.example.todoapp

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class TaskItemRepository(private val taskItemDao: TaskItemDao) {
    // 모든 Task 항목을 가져오는 Flow
    val allTaskItems: Flow<List<TaskItem>> = taskItemDao.allTaskItems()

    // Task 항목을 삽입하는 함수
    @WorkerThread
    suspend fun insertTaskItem(taskItem: TaskItem) {
        taskItemDao.insertTaskItem(taskItem)
    }

    // Task 항목을 업데이트하는 함수
    @WorkerThread
    suspend fun updateTaskItem(taskItem: TaskItem) {
        taskItemDao.updateTaskItem(taskItem)
    }

    // Task 항목을 삭제하는 함수
    @WorkerThread
    suspend fun deleteTaskItem(taskItem: TaskItem) {
        taskItemDao.deleteTaskItem(taskItem)
    }
}
