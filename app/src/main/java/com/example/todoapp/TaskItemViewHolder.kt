package com.example.todoapp

import android.content.Context
import android.graphics.Paint
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.time.format.DateTimeFormatter
import com.example.todoapp.databinding.TaskItemCellBinding

class TaskItemViewHolder(
    private val context: Context,
    private val binding: TaskItemCellBinding,
    private val clickListener: TaskItemClickListener
): RecyclerView.ViewHolder(binding.root) {
    private val timeFormat = DateTimeFormatter.ofPattern("HH:mm")

    fun bindTaskItem(taskItem: TaskItem) {
        binding.name.text = taskItem.name

        if (taskItem.isCompleted()) {
            // 작업이 완료되었을 때 텍스트에 취소선 추가 및 색상 변경
            binding.name.apply {
                paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                setTextColor(ContextCompat.getColor(context, R.color.black)) // 완료된 텍스트에 대해 원하는 색상으로 설정
            }
        } else {
            // 완료가 아니라면 텍스트의 취소선을 제거하고 원래 색상으로 설정
            binding.name.apply {
                paintFlags = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                setTextColor(ContextCompat.getColor(context, R.color.black)) // 미완료 텍스트에 대해 원하는 색상으로 설정
            }
        }

        // 완료 버튼 이미지와 색상 설정
        binding.completeButton.apply {
            setImageResource(taskItem.imageResource())
            setColorFilter(taskItem.imageColor(context))
            setOnClickListener {
                clickListener.completeTaskItem(taskItem)
            }
        }

        // 완료 버튼 클릭 리스너 설정
        binding.completeButton.setOnClickListener {
            clickListener.completeTaskItem(taskItem)
        }

        // Task 항목을 편집하기 위한 클릭 리스너 설정
        binding.taskCellContainer.setOnClickListener {
            clickListener.editTaskItem(taskItem)
        }

        // Task 항목을 삭제하기 위한 클릭 리스너 설정
        binding.deleteButton.setOnClickListener {
            clickListener.deleteTaskItem(taskItem)
        }

        if (taskItem.dueTime() != null) {
            // 기한이 있는 경우 텍스트에 표시합니다.
            binding.dueTime.text = timeFormat.format(taskItem.dueTime())
        } else {
            // 기한이 없는 경우 빈 문자열로 설정합니다.
            binding.dueTime.text = ""
        }
    }
}
