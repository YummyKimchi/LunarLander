# LunarLander
A basic lunar lander program to be used in learning about autonomous control of robots.

## Running the program

To run from Visual Studio Code, open the file `src\main\java\com\bitsforabetterworld\lunarlander\Main.java`. Click on the `Run` link above the `main` method.

Or to run from PowerShell, cd to the LunarLander directory and run `.\gradlew run`

## Trying the game

First, try to land the lander in teleop mode. 

The goal is to have a successful landing, which means that your lander gets to a Y position of 0
with the following requirements:

- Vertical velocity (dy) less than -8 m/s
- Horizontal velocity (dx) between -5 and 5 m/s
- Angle (theta) between -0.3 and 0.3 radians
- Change in angle (dtheta) between -0.2 and 0.2 radians/second

Keyboard controls:

	W: thrust
	A: rotate counterclockwise
	D: rotate clockwise
	N: Next level

## Creating an autonomous program

Then, try your hand at programming the autonomous mode!

In Main.main(), change this:

	setupDisplayAndControls(false);

to this:
	
	setupDisplayAndControls(true);
	

Then fill in code in AutonomousControl.java to decide if we should thrust (by setting thrustCommand = Command.Thrust) or
turn on the rotation motors (by settings rotateCommand = Command.RollClockwise 
or Command.RollCounterclockwise)

Use the Position and Velocity of the lander to decide what action (if any) to take.

Try to get a high score! You get points for finishing a level, for fuel left at the
end of a level, and for landing on the landing pad.
