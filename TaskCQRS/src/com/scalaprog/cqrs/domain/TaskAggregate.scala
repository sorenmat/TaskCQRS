package com.scalaprog.cqrs.domain
import com.scalaprog.cqrs.command.CreateNewTask
import com.scalaprog.cqrs.events.TaskCreated

// observer is somehow the eventstore...
class TaskAggregate(state: TaskAggreagateState, eventStore: EventStore) {

  def handle(c: CreateNewTask) {
    // do something.
    if (state.version > 0)
      throw new RuntimeException("Can't create aggregate more then once..")
    val taskEvent = new TaskCreated(c.id, c.name, c.desc, c.dueDate, c.priority)

    apply(taskEvent)
  }

  // Aplpy the event to the internal state, and notify observers.
  def apply(e: TaskCreated) {
    state.apply(e)
    eventStore.store(e)
  }

}

class TaskAggreagateState {
  var version = 0
}