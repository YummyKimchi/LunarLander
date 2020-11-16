package com.bitsforabetterworld.lunarlander;

public class Main {
	public static void main(String[] args) {
		LanderLevel.setupDisplayAndControls(true);
		LanderLevel.nextLevel();
		LanderLevel.runLoop();
	}

}
