package com.scalaprog.cqrs.events
import com.scalaprog.cqrs.Guid
import java.util.Date

class TaskDueDateChanged(id: Guid, dueDate: Date) extends TaskEvent(id) {

}