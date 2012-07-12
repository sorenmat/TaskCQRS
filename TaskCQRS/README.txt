Premis
---------------------
We need to make a Task application.


Commands
---------------------
We need to define the commands for our little application, after greate tought and talking with the domain experts we ended up with the first command.

CreateNewTask

The command name should be defining a clear intend of what should be done, and needless to say it has to be unique in the domain.
The command is send to the domain (-model), where an aggreate should handle the command.
This makes it clear that we should create a taskaggregate. The aggregate should handle all commands assoiated to its bounded context.
The aggreate should transform the command into a number of events, if the command is accepted. All events should be named in past tense, 
since it something that has happend in the domain, and can't be undone.
   

AggregateRoot
---------------------

The aggregate root is a business context. The idea is that the aggregate will encapsulate enough state to validate commands executed against it.  
In our small example we keep the state of the id and duedate in the Task aggregate. We need the id to validate the business rule that states that the CreateCommand
should be the first command, and since the CreateCommand is the only command to change the id we use that.
The duedate is kept to make the example more complete.
The state of the aggregate root is almost never strings other then identity keys. The reason for this is that strings are rarely good to make a decision upon.
The other reason is that the aggregate state is kept in memory, and therefore should be as simple as possible.
  


So where is my data stored ?
If the aggregate root only contain enough data/state to make business decisions on whether or not a command can successfully be executed, where do my data live ??
This is where the ReadModel/ViewModel comes into play.


ReadModel / ViewModel  
---------------------

The ReadModel should react to events published via the EventBus. If we have a task readmodel, that model should react to TaskCreated and TaskDueDateChanged events.
