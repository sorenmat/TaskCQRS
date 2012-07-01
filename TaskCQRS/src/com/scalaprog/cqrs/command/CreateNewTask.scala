package com.scalaprog.cqrs.command
import com.scalaprog.cqrs.Guid
import java.util.Date

class CreateNewTask(val id: Guid, val name: String, val desc: String, val dueDate: Date, val priority: Int) {

}