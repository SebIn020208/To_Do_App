package com.example.todoapp

import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import java.sql.Time
import java.time.LocalTime
import com.example.todoapp.databinding.FragNewTaskSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NewTaskSheet(var taskItem: TaskItem?) : BottomSheetDialogFragment()
{
    private lateinit var binding: FragNewTaskSheetBinding // NewTaskSheet에서 사용되는 레이아웃 바인딩
    private lateinit var taskViewModel: TaskViewModel // ViewModel 인스턴스 저장
    private var dueTime: LocalTime? = null // Task 마감 시간 저장

    // View가 생성되었을 때 호출
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()

        // taskItem이 null이 아닌 경우에는 작업을 편집하는 모드로 설정
        if (taskItem != null)
        {
            binding.taskTitle.text = "Edit Task"
            val editable = Editable.Factory.getInstance()
            binding.name.text = editable.newEditable(taskItem!!.name)
            binding.desc.text = editable.newEditable(taskItem!!.desc)
            if(taskItem!!.dueTime() != null){
                dueTime = taskItem!!.dueTime()!!
                updateTimeButtonText()
            }
        }
        else
        {
            binding.taskTitle.text = "New Task"
        }

        // taskViewModel 초기화
        taskViewModel = ViewModelProvider(activity).get(TaskViewModel::class.java)
        // saveButton과 timePickerButton의 클릭 이벤트를 설정
        binding.saveButton.setOnClickListener {
            saveAction()
        }
        binding.timePickerButton.setOnClickListener {
            openTimePicker()
        }
    }

    // 시간 선택 다이얼로그 열어서 dueTime 및 버튼의 텍스트 업데이트
    private fun openTimePicker() {
        if(dueTime == null)
            dueTime = LocalTime.now()
        val listener = TimePickerDialog.OnTimeSetListener{ _, selectedHour, selectedMinute ->
            dueTime = LocalTime.of(selectedHour, selectedMinute)
            updateTimeButtonText()
        }
        val dialog = TimePickerDialog(activity, listener, dueTime!!.hour, dueTime!!.minute, true)
        dialog.setTitle("Task Due")
        dialog.show()

    }

    // 버튼의 텍스트를 마감 시간에 맞게 업데이트
    private fun updateTimeButtonText() {
        binding.timePickerButton.text = String.format("%02d:%02d",dueTime!!.hour,dueTime!!.minute)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // inflater와 container를 사용해 레이아웃 바인딩 초기화
        binding = FragNewTaskSheetBinding.inflate(inflater,container,false)
        // 바인딩된 뷰의 root를 반환
        return binding.root
    }

    // 저장 동작 수행
    private fun saveAction()
    {
        // 입력된 작업 이름과 설명을 가져와서 새로운 TaskItem을 생성하거나 기존 TaskItem을 업데이트
        val name = binding.name.text.toString()
        val desc = binding.desc.text.toString()
        val dueTimeString = if(dueTime == null) null else TaskItem.timeFormatter.format(dueTime)
        // 작업이 추가되면 taskViewModel을 통해 TaskItem을 추가
        // 작업이 편집되면 taskViewModel을 통해 TaskItem을 업데이트
        if(taskItem == null)
        {
            val newTask = TaskItem(name,desc,dueTimeString,null)
            taskViewModel.addTaskItem(newTask)
        }
        else
        {
            taskItem!!.name = name
            taskItem!!.desc = desc
            taskItem!!.dueTimeString = dueTimeString
            taskViewModel.updateTaskItem(taskItem!!)
        }
        // 입력 필드를 초기화 & bottomsheet닫기
        binding.name.setText("")
        binding.desc.setText("")
        // 다이어로그 닫기
        dismiss()
    }
}