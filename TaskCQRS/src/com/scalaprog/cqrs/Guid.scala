package com.scalaprog.cqrs
import java.util.UUID

case class Guid(val id: UUID, idType: String) {
  
  override def toString = idType+"-"+id

}