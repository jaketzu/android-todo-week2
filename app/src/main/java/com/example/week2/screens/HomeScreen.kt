package com.example.week2.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.week2.domain.Task
import com.example.week2.viewmodel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel: TaskViewModel = viewModel()) {
    val priorityValues = Task.Priority.entries
    val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.UK)

    Column(
        Modifier
            .padding(12.dp),
    ) {
        Text(
            "Tasks", modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                OutlinedTextField(
                    value = viewModel.taskTitle,
                    onValueChange = { viewModel.taskTitle = it },
                    label = { Text("Task title") },
                    placeholder = { Text("Title") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.6f)
                )

                OutlinedTextField(
                    value = viewModel.taskDescription,
                    onValueChange = { viewModel.taskDescription = it },
                    label = { Text("Task description") },
                    placeholder = { Text("Description") },
                    maxLines = 4,
                    modifier = Modifier.fillMaxWidth(0.6f)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.14f)
                    .border(
                        1.dp,
                        Color.Gray,
                        OutlinedTextFieldDefaults.shape
                    )
                    .clickable {
                        viewModel.isDropdownExpanded = true
                    },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("${priorityValues[viewModel.priorityIndex]} ▽")
                DropdownMenu(
                    expanded = viewModel.isDropdownExpanded,
                    onDismissRequest = {
                        viewModel.isDropdownExpanded = false
                    }) {

                    priorityValues.forEachIndexed { index, priority ->
                        DropdownMenuItem(
                            text = {
                                Text("$priority")
                            },
                            onClick = {
                                viewModel.isDropdownExpanded = false
                                viewModel.priorityIndex = index
                                viewModel.taskPriority = priority
                            })
                    }
                }
            }
        }

        Spacer(Modifier.height(4.dp))

        Button(modifier = Modifier.fillMaxWidth(), onClick = {
            if (viewModel.taskTitle.trim() != "") {
                val taskDueDate = Calendar.getInstance()
                taskDueDate.add(Calendar.DAY_OF_MONTH, 1)

                val task = Task(
                    viewModel.tasks.size,
                    viewModel.taskTitle,
                    viewModel.taskDescription,
                    viewModel.taskPriority,
                    taskDueDate.time
                )

                viewModel.addTask(task)
                viewModel.sortAndFilter(viewModel.doneType, viewModel.sortType)

                viewModel.taskTitle = ""
                viewModel.taskDescription = ""
            }
        }) { Text("Add task") }

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(onClick = {
                viewModel.doneType = if (viewModel.doneType == 2) 0 else viewModel.doneType + 1
                viewModel.sortAndFilter(viewModel.doneType, viewModel.sortType)
            }, Modifier.weight(1f)) { Text("Filter by done") }

            Button(onClick = {
                viewModel.sortType = !viewModel.sortType
                viewModel.sortAndFilter(viewModel.doneType, viewModel.sortType)
            }, Modifier.weight(1f)) { Text("Sort by due date") }
        }

        Spacer(Modifier.height(12.dp))

        LazyColumn(
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(viewModel.visibleTasks) { item ->
                Card {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            Modifier.fillMaxWidth(0.67f),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(item.title)
                            Text("${item.priority}    ${formatter.format(item.dueDate.time)}")
                            Text(item.description)
                        }

                        Checkbox(checked = item.done, onCheckedChange = {
                            viewModel.toggleDone(item.id)
                            viewModel.sortAndFilter(viewModel.doneType, viewModel.sortType)
                        })

                        Button(
                            onClick = {
                                viewModel.removeTask(item.id)
                                viewModel.sortAndFilter(viewModel.doneType, viewModel.sortType)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                        ) { Text("\uD83D\uDDD1") }
                    }
                }
            }
        }
    }
}
