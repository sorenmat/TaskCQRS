package com.scalaprog.cqrs
import com.scalaprog.cqrs.events.Event

trait EventHandler {
  def publish(event: Event)
}