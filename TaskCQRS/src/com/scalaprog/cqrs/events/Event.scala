package com.scalaprog.cqrs.events
import com.scalaprog.cqrs.Guid

case class Event(val id: Guid) {
  var version = -1
  override def toString = id.idType + " - " + id.id + " - " + getClass().getSimpleName()
}