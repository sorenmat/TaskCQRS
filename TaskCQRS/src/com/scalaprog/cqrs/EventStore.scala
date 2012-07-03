package com.scalaprog.cqrs
import com.scalaprog.cqrs.events.Event
import com.scalaprog.cqrs.domain.Task

/**
 * The event store is where the events are stored. Normally this would be a File, database or some other persistence storage.
 * This storage is append only, we DO NEVER change events in the event store... EVER...
 */
class EventStore(bus: EventBus) {
   var eventStream = List[Event]()
  
  /**
   * Stores events in the event store, this is a dummy implementation
   */
  def store[E <: Event](event: E) {
    eventStream = event :: eventStream
    bus.publish(event)
  }
  
   override def toString = eventStream.mkString(", ")
   
  
}