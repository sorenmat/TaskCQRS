package com.scalaprog.cqrs
import com.scalaprog.cqrs.events.Event

class EventBus {

  private var eventHandlers = List[EventHandler]()

  def registerHandler(eh: EventHandler) {
    eventHandlers = eh :: eventHandlers
  }
  
  def publish(event: Event) {
    eventHandlers.foreach(handler => handler.publish(event))
  }
}
