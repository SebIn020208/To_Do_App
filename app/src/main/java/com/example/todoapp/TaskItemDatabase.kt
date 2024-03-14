package com.example.todoapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// RoomDatabase 어노테이션을 사용하여 데이터베이스 정의
@Database(entities = [TaskItem::class], version = 1, exportSchema = false)
abstract class TaskItemDatabase : RoomDatabase() {

    // DAO를 가져오기 위한 함수 선언
    abstract fun taskItemDao(): TaskItemDao

    // 데이터베이스의 인스턴스를 제공하기 위한 객체 정의
    companion object {
        @Volatile
        private var INSTANCE: TaskItemDatabase? = null

        // 데이터베이스의 인스턴스를 가져오는 함수
        fun getDatabase(context: Context): TaskItemDatabase {
            // INSTANCE가 null이 아닌 경우, 이미 생성된 인스턴스를 반환
            INSTANCE?.let {
                return it
            }

            synchronized(this) {
                // 데이터베이스의 새로운 인스턴스를 생성
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskItemDatabase::class.java,
                    "task_database"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}
