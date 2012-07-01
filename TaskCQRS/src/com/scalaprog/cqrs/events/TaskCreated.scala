package com.scalaprog.cqrs.events
import com.scalaprog.cqrs.Guid
import java.util.Date

class TaskCreated(id: Guid, name: String, desc: String, dueDate: Date, priority: Int) {

}