package com.scalaprog.cqrs
import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.scalaprog.cqrs.domain.Task
import command.CreateNewTask
import java.util.Date
import com.scalaprog.cqrs.command.ChangeDueDateOnTask
import com.scalaprog.cqrs.events.Event

@RunWith(classOf[JUnitRunner])
class EventHandlerTest extends FlatSpec with ShouldMatchers {
  val eventBus = new EventBus
  val eventStore = new EventStore(eventBus)

  eventBus.registerHandler(new EventHandler() {
    def publish(event: Event) {
      println("Event: " + event)
    }
  })
  "EventStore" should "contain two events" in {
    val taskAggregate = new Task(eventStore)

    val createTaskCommand = new CreateNewTask(Guid.create("task"), "fix cable tv", "Fix the tv man, Springer is on soon", new Date(), 1)
    taskAggregate.create(createTaskCommand)

    val changeDueDate = new ChangeDueDateOnTask(new Date())
    taskAggregate.changeDueDate(changeDueDate)

    println(eventStore)
    eventStore.eventStream.size should equal(2)

  }
}

