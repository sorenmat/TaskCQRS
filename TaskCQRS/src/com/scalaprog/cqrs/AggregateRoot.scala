package com.scalaprog.cqrs
import com.scalaprog.cqrs.events.Event

abstract class AggregateRoot[EventType](eventStore: EventStore) {
 
  var version = -1
  protected def applyEvent(e: EventType, isNew: Boolean) : Event => Unit

  def uncommittedEvents: Iterable[EventType] = _uncommittedEvents

  def markCommitted = _uncommittedEvents = List()

  def loadFromHistory(history: Iterable[Event]) = history.foreach(e => applyEvent(e.asInstanceOf[EventType], false))

//  protected def record[E <: EventType](event: E) {
//    applyEvent(event)
//    _uncommittedEvents = event :: _uncommittedEvents
//  }

  private var _uncommittedEvents = List[EventType]()
  
  
  
  /**
   * Get an aggregate from the event store, by playing all the events again.
   */
 def getByid[E <: AggregateRoot[_]](clazz: Class[E], id: Guid) = {
     val e = eventStore.eventStream.filter(e => e.id == id)
     val task = clazz.newInstance()
     task.loadFromHistory(e)
     task
   }

  
}

