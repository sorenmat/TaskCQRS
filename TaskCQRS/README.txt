Premis
-----------------------
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
   



Domain
-------------------

First we need to define our domain model. In our case we wanna make an simple TODO application.
We need to define some basic entities.

 Person
 Todo
 