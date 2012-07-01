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

/**
 * Aggregate root the task commands and translate them to events. These event are then fired against the event store, and listeners
 */
class Task(eventStore: EventStore) extends AggregateRoot[TaskEvent] {

  var id: Guid = _

  def create(c: CreateNewTask) {
    val taskEvent = new TaskCreated(c.id, c.name, c.desc, c.dueDate, c.priority)
    applyEvent(taskEvent)
  }

  def changeDueDate(c: ChangeDueDateOnTask) {
    if(id == null)
      throw new RuntimeException("The task needs to be created first.")
    
    val changeEvent = new TaskDueDateChanged(id, c.dueDate)
    applyEvent(changeEvent)
  }

  // Aplpy the event to the internal state, and notify observers.
  def applyEvent(e: TaskEvent) = {
    e match {
      case e: TaskCreated => id = e.id
      case _ => 
    }
    eventStore.store(e)
    e => Unit
  }
  
}
