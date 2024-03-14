package com.example.todoapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskItemDao {
    // 모든 작업 항목을 가져오는 Flow를 반환하는 쿼리
    @Query("SELECT * FROM task_item_table ORDER BY id ASC")
    fun allTaskItems(): Flow<List<TaskItem>>

    // Task 항목을 삽입하는 함수
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTaskItem(taskItem: TaskItem)

    // Task 항목을 업데이트하는 함수
    @Update
    suspend fun updateTaskItem(taskItem: TaskItem)

    // Task 항목을 삭제하는 함수
    @Delete
    suspend fun deleteTaskItem(taskItem: TaskItem)
}
