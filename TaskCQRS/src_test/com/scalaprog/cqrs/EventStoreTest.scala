package com.scalaprog.cqrs
import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.scalaprog.cqrs.domain.Task
import command.CreateNewTask
import java.util.Date
import com.scalaprog.cqrs.command.ChangeDueDateOnTask

@RunWith(classOf[JUnitRunner])
class EventStoreTest extends FlatSpec with ShouldMatchers {
  val eventStore = new EventStore

  "EventStore" should "contain two events" in {
    val taskAggregate = new Task(eventStore)

    val createTaskCommand = new CreateNewTask(Guid.create("task"), "fix cable tv", "Fix the tv man, Springer is on soon", new Date(), 1)
    taskAggregate.create(createTaskCommand)

    val changeDueDate = new ChangeDueDateOnTask(new Date())
    taskAggregate.changeDueDate(changeDueDate)

    println(eventStore)
    eventStore.eventStream.size should equal(2)

  }

  "Execution of ChangeDueDateOnTask" should "fail" in {
    val taskAggregate = new Task(eventStore)

    val changeDueDate = new ChangeDueDateOnTask(new Date())
    evaluating {
      taskAggregate.changeDueDate(changeDueDate)
    } should produce[RuntimeException]

  }
  
 "Loading from eventstore" should "give the same date on both aggregates" in {
    val taskAggregate = new Task(eventStore)

    val createTaskCommand = new CreateNewTask(Guid.create("task"), "fix cable tv", "Fix the tv man, Springer is on soon", new Date(), 1)
    taskAggregate.create(createTaskCommand)

    val theDate = new Date()
    val changeDueDate = new ChangeDueDateOnTask(theDate)
    taskAggregate.changeDueDate(changeDueDate)

    
   val newAggregate = taskAggregate.getByid(classOf[Task], taskAggregate.id)
   newAggregate.dueDate should equal(theDate)
   
  }
  
}

