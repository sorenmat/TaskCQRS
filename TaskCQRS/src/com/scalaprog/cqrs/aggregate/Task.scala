package com.scalaprog.cqrs.domain
import com.scalaprog.cqrs.command.CreateNewTask
import com.scalaprog.cqrs.events.Event
import com.scalaprog.cqrs.events.TaskCreated
import com.scalaprog.cqrs.EventStore
import com.scalaprog.cqrs.AggregateRoot
import com.scalaprog.cqrs.events.TaskEvent
import com.scalaprog.cqrs.Guid
import com.scalaprog.cqrs.command.ChangeDueDateOnTask
import com.scalaprog.cqrs.events.TaskDueDateChanged
import com.scalaprog.cqrs.events.TaskDueDateChanged
import java.util.Date

/**
 * Aggregate root the task commands and translate them to events. These event are then fired against the event store, and listeners
 */
class Task(eventStore: EventStore) extends AggregateRoot[TaskEvent](eventStore) {

  def this() {
    this(null)
  }
  
  var id: Guid = _
  var dueDate: Date = _

  def create(c: CreateNewTask) {
    val taskEvent = new TaskCreated(c.id, c.name, c.desc, c.dueDate, c.priority)
    applyEvent(taskEvent, true)
  }

  def changeDueDate(c: ChangeDueDateOnTask) {
    if (id == null)
      throw new RuntimeException("The task needs to be created first.")

    val changeEvent = new TaskDueDateChanged(id, c.dueDate)
    applyEvent(changeEvent, true)
  }

  // Aplpy the event to the internal state, and notify observers.
  def applyEvent(e: TaskEvent, isNew: Boolean) = {
    e match {
      case e: TaskCreated => id = e.id
      case e: TaskDueDateChanged => dueDate = e.dueDate
      case _ =>
    }
    if(isNew) {
    	eventStore.store(e)
    }
    e => Unit
  }
}
