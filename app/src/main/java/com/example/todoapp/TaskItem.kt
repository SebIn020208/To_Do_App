package com.example.todoapp

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.UUID

// TaskItem -> 데이터베이스의 task_item_table 테이블과 매핑됨
@Entity(tableName = "task_item_table")
class TaskItem(
    @ColumnInfo(name = "name") var name: String, // TaskItem의 이름을 저장하는 변수
    @ColumnInfo(name = "desc") var desc: String, // TaskItem의 설명을 저장하는 변수
    @ColumnInfo(name = "dueTimeString") var dueTimeString: String?, // TaskItem의 마감 시간을 저장하는 변수,  Nullable 타입 -> null값 가질 수 O
    @ColumnInfo(name = "completedDateString") var completedDateString: String?, // TaskItem의 완료 일자를 저장하는 변수, Nullable 타입 -> null값 가질 수 O
    // 각 TaskItem은 고유한 id 값을 가짐. id 자동으로 생성되도록 설정
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)
{
    // TaskItem의 완료 일자를 LocalDate 형식으로 반환
    fun completedDate(): LocalDate? = if (completedDateString == null) null
    else LocalDate.parse(completedDateString, dateFormatter)
    // TaskItem의 마감 시간을 LocalTime 형식으로 반환
    fun dueTime(): LocalTime? = if (dueTimeString == null) null
    else LocalTime.parse(dueTimeString, timeFormatter)

    // TaskItem이 완료되었는지 여부를 반환
    fun isCompleted() = completedDate() != null
    // TaskItem의 이미지 리소스를 반환
    fun imageResource(): Int = if(isCompleted()) R.drawable.checked_24 else R.drawable.unchecked_24
    // TaskItem의 이미지 색상을 반환
    fun imageColor(context: Context): Int = if(isCompleted()) purple(context) else black(context)

    // 주어진 Context를 사용해 purple(보라색) / black(검은색)의 색상 값을 반환
    private fun purple(context: Context) = ContextCompat.getColor(context, R.color.purple_500)
    private fun black(context: Context) = ContextCompat.getColor(context, R.color.black)

    companion object {
        // DateTimeFormatter를 사용하여 날짜와 시간의 형식을 정의함
        val timeFormatter: DateTimeFormatter = DateTimeFormatter.ISO_TIME
        val dateFormatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE
    }
}