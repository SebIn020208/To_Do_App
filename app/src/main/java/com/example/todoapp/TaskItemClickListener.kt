package com.example.todoapp

interface TaskItemClickListener {
    // Task 항목을 삭제하는 함수
    fun deleteTaskItem(taskItem: TaskItem)

    // Task 항목을 편집하는 함수
    fun editTaskItem(taskItem: TaskItem)

    // Task 항목을 완료 처리하는 함수
    fun completeTaskItem(taskItem: TaskItem)
}
