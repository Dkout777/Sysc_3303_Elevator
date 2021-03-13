# Sysc3303 Project

A elevator system and simulator.

## Instructions
There are 2 kinds of inputs to the system
the first is Floorinput.txt 
 
there are 4 inputs
1. Number of seconds after launch this event happens (number)
2. Floor that the event happens on (number)
3. whether it is up or down true for up, flase for down (String)
4. Elevator button (depreciated)(int)

The Second is ElevatorInputs.txt
there are 2 inputs

1. number of seconds after launch this event happens(int)
2. floor button (int)

To set up and run you need to set inputs for both files and then run Scheduler.java as a java application

## File Description

The main systems are the Floor, Scheduler and the Elevator

All communication between these are done by Channel, a synchronized class that takes a Data class
Each one of the systems has there own data class so Elevator sends ElevatorData, Floor sends FloorData and the Scheduler sends SchedulerData
This is becasue each of the Systems has different things to communicate to there fellows

### Floor 
  The floor reads from the floor input file and sends it's information to the scheduler at the appropriate time.
  It sends the floor number and the up/down button press to the scheduler.
 
### Scheduler
 
 The Scheduler listens to 2 diffent Channels and when it has information it sends it on it's own channel to the Elevator
 the scheduler keeps track of the elevators up and down state with an Enum named ElevatorState and keeps track of its 
 own state using an enum named SchedulerState.java, Elevator state only has 2 states, up or down and SchedulerState has 
 4 states
 
 When Scheduler Has multiple stops it keeps track of them in an arraylist of Stops, an internal data type with an int for the floor and two booleans
 for whether it can be used in an up or down state
 
 The Scheduler uses an up then down algorithim to scheduler it's stops
 
 it starts by going up and servicing all up stops higher than it. THen it finds the highest down stop and goes to it continuing down servicing all 
 down stops lower than it
 
 WAITING    <br>
 BUTTONPUSHED <br>
 FLOORREQUESTED <br>
 FLOORRECEIVED <br>
 
 ### Elevator 
 
The elevator has a thread dedicated to updating the scheduler about any button presses in the car. It reads in a file with a series of buttons and times they were pressed.
When this thread gets to the appropriate time after runtime it sends the scheduler the button press through the elevator channel.
 
 The elevator has 4 states, it starts at the in the waiting state and waits for an input from the scheduler. When it receives a destination it will close the door and it will
 begin moving towards the required floor. Every Time it passes a floor it will send the scheduler an update that contains what floor it is currently at. This done so that the
 scheduler has the opportunity to redirect the destination to any floors on the current path. Once it gets to its destination it will close the doors and re enter the waiting
 state.
 

## Work Responsibilities
1.Isaac Pruner<br>
Scheduler System <br>
Scheduler.Java<br>
SchedulerState.java<br>
SchedulerData.java<br>
ElevatorState.java<br>
Stop.java<br>

2.Dimitry Koutchine<br>
Elevator System<br>
Elevator.java<br>
ElevatorData.java<br>

3.Moonis Mohammad<br>
Floor.java<br>
FloorData.java<br>
Data.java<br>



  
 
 
 
 
 
 
 
