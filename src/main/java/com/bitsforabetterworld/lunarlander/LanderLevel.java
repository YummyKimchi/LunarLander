package com.bitsforabetterworld.lunarlander;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.EnumSet;

import javax.swing.SwingUtilities;

import com.bitsforabetterworld.lunarlander.ui.Display;
import com.bitsforabetterworld.lunarlander.ui.LanderKeyListener;

public class LanderLevel {
	
	private static int score = 0;
	private static Display display;
	private static Control control;
	private static Lander lander;
	private static int level = 0;
	private static final SecureRandom rand = new SecureRandom();
	
	public static synchronized int getScore() {
		return score;
	}
	
	public static synchronized void reportSuccessfulLanding(double fuelRemaining) {
		score += (1+level) * (10 + (int)fuelRemaining);
	}
	public static void runLoop() {
		
		try {
			long lastUpdateNanos = System.nanoTime();
			while (lander != null) {
				Thread.sleep(60L);
        		long now = System.nanoTime();
        		if (lastUpdateNanos < now) {
        			double dtSeconds = ((double)(now - lastUpdateNanos)) / 1000000000.0;
        			EnumSet<Command> commands = control.getCommand(lander.getPosition(), lander.getVelocity());
        			lander.clockTick(dtSeconds, commands);
        		}
        		lastUpdateNanos = now;
				
				SwingUtilities.invokeLater(new Runnable() {
		            public void run() {
		            	display.update();
		            }
				});
			}
		}
		catch (InterruptedException ex) {
			System.err.println("Interrupted");
		}
		
	}

	public synchronized static void nextLevel() {
		double initialVelocity = 0.0;
		double fuel = (20 - level) * 5;
		double initialDirection = 0.0;
		double initialHeading = 0.0;
		double initialX = Constants.WIDTH_OF_SCREEN * 0.5;
		double initialY = Constants.TOP_OF_SCREEN * 0.9;
		if (level > 0) {
			initialHeading = rand.nextDouble() * 2.0 * Math.PI;
		}
		if (level > 1) {
			initialX = Constants.WIDTH_OF_SCREEN * rand.nextDouble();
		}
		if (level > 2) {
			initialY = Constants.TOP_OF_SCREEN * (1.0 + rand.nextDouble()) * 0.5;
		}
		if (level > 3) {
			initialDirection = rand.nextDouble() * 2.0 * Math.PI;
			initialVelocity = 5.0 * level;
		}
		lander = new Lander.Builder()
				.x(initialX)
				.y(initialY)
				.thrusterAcceleration(Constants.THRUSTER_ACCELERATION)
				.gravityAcceleration(Constants.GRAVITY_ACCELERATION)
				.fuel(fuel)
				.theta(initialHeading)
				.dx(initialVelocity * Math.sin(initialDirection))
				.dy(initialVelocity * Math.cos(initialDirection))
				.build();
		++level;
		display.setLander(lander);
	}
	
	public static void setupDisplayAndControls(boolean useAutonomousMode) {
		
		display = new Display();
		final LanderKeyListener landerKeyListener = new LanderKeyListener();
		final Control teleopControl = new TeleopControl(landerKeyListener);
		final Control autonomousControl = new AutonomousControl();
		if (useAutonomousMode) {
			control = autonomousControl;
		}
		else {
			control = teleopControl;
		}
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	try {
            		display.createAndShowGUI(landerKeyListener);
            	}
            	catch (IOException exp) {
            		System.err.println("Exception: "+exp);
            		exp.printStackTrace(System.err);
            	}
            }
		});
	}

}
