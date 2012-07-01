package com.scalaprog.cqrs
import com.scalaprog.cqrs.events.Event
import com.scalaprog.cqrs.domain.Task

class EventStore {
   var eventStream = List[Event]()
  
  /**
   * Stores events in the event store, this is a dummy implementation
   */
  def store[E <: Event](event: E) {
    eventStream = event :: eventStream
  }
  
   def getByid[X <: AggregateRoot[_]](id: Guid) = {
     val e = eventStream.filter(e => e.id == id)
     val task = new Task(this)
     task.loadFromHistory(e)
     task
   }
   override def toString = eventStream.mkString(", ")
   
  
}