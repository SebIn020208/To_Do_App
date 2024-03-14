package com.example.todoapp

import android.app.Application

class ToDoApplication : Application() {
    // TaskItemDatabase 인스턴스를 지연 초기화로 생성
    private val database by lazy { TaskItemDatabase.getDatabase(this) }

    // TaskItemRepository 인스턴스를 지연 초기화로 생성
    val repository by lazy { TaskItemRepository(database.taskItemDao()) }
}