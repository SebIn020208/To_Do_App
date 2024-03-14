package com.example.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.databinding.ActivityMainBinding
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity(), TaskItemClickListener
{
    private lateinit var binding: ActivityMainBinding //MainActivity에서 사용되는 레이아웃 바인딩
    private val taskViewModel: TaskViewModel by viewModels {
        TaskItemModelFactory((application as ToDoApplication).repository)
    } // ViewModel인스턴스 저장

    // activity 생성될 때 호출
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        // 레이아웃 바인딩 초기화
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 'newTaskButton' button click event 설정
        binding.newTaskButton.setOnClickListener {
            NewTaskSheet(null).show(supportFragmentManager, "newTaskTag")
        }
        // RecyclerView 초기화
        setRecyclerView()
    }

    // Task 삭제
    override fun deleteTaskItem(taskItem: TaskItem) {
        // Task 삭제 대화상자 표시
        AlertDialog.Builder(this)
            .setTitle("Delete Task")
            .setMessage("Are you sure you want to delete this task?") // 정말 지우고 싶은지 물어보고
            .setPositiveButton("Delete") { dialog, _ -> // delete 누르면 삭제
                taskViewModel.deleteTaskItem(taskItem)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ -> // cancel누르면 삭제 X
                dialog.dismiss()
            }
            .show()
    }

    // RecyclerView 초기화
    private fun setRecyclerView()
    {
        val mainActivity = this
        taskViewModel.taskItems.observe(this){
            binding.todoListRecyclerView.apply {
                // RecyclerView layoutManager를 LinearLayoutManager로 설정
                // layoutManager->RecyclerView내 항목 배열 제어
                layoutManager = LinearLayoutManager(applicationContext)
                // RecyclerView adapter를 TaskItemAdapter로 설정
                // TaskItemAdapter->RecyclerView에 데이터 제공, 각 항목에 해당하는 뷰 생성
                adapter = TaskItemAdapter(it, mainActivity)
            }
        }
    }

    // Task 수정
    // 편집할 TaskItem을 인자로 받고, 새로운 작업 시트를 열어 편집
    override fun editTaskItem(taskItem: TaskItem)
    {
        NewTaskSheet(taskItem).show(supportFragmentManager,"newTaskTag")
    }

    // TaskItem 상태 완료
    // 완료된 TaskItem을 인자로 받아 ViewModel을 통해 해당 TaskItem의 상태를 완료로 설정
    override fun completeTaskItem(taskItem: TaskItem)
    {
        taskViewModel.setCompleted(taskItem)
    }
}
