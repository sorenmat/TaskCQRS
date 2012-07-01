package com.scalaprog.cqrs
import com.scalaprog.cqrs.events.Event

trait AggregateRoot[EventType] {
  protected def applyEvent(e: EventType) : Event => Unit

  def uncommittedEvents: Iterable[EventType] = _uncommittedEvents

  def markCommitted = _uncommittedEvents = List()

  def loadFromHistory(history: Iterable[Event]) = history.foreach(e => applyEvent(e.asInstanceOf[EventType]))

  protected def record[E <: EventType](event: E) {
    applyEvent(event)
    _uncommittedEvents = event :: _uncommittedEvents
  }

  private var _uncommittedEvents = List[EventType]()
}