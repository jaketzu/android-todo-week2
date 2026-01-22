package com.example.week2.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.week2.domain.Task
import com.example.week2.domain.mockData

class TaskViewModel : ViewModel() {
    var tasks by mutableStateOf(listOf<Task>())
        private set
    var visibleTasks by mutableStateOf(listOf<Task>())
        private set

    var taskTitle by mutableStateOf("")
    var taskDescription by mutableStateOf("")

    var taskPriority by mutableStateOf(Task.Priority.LOW)
    var priorityIndex by mutableIntStateOf(0)
    var isDropdownExpanded by mutableStateOf(false)

    var doneType by mutableIntStateOf(0)
    var sortType by mutableStateOf(false)

    init {
        tasks = mockData()
        visibleTasks = tasks

        taskTitle = ""
        taskDescription = ""

        taskPriority = Task.Priority.LOW
        priorityIndex = 0
        isDropdownExpanded = false

        doneType = 0
        sortType = false
    }

    fun addTask(task: Task) {
        tasks += task
    }

    fun toggleDone(id: Int) {
        tasks = tasks.map { task ->
            if (task.id == id) task.copy(done = !task.done)
            else task
        }
    }

    fun removeTask(id: Int) {
        tasks = tasks.filter { it.id != id }
    }


    fun sortAndFilter(filterType: Int, sortType: Boolean) {
        visibleTasks = when (filterType) {
            1 -> tasks.filter { !it.done }
            2 -> tasks.filter { it.done }
            else -> tasks
        }

        visibleTasks =
            if (!sortType) visibleTasks.sortedBy { it.dueDate } else visibleTasks.sortedByDescending { it.dueDate }
    }
}