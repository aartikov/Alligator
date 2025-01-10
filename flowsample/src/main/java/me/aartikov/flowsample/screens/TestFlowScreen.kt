package me.aartikov.flowsample.screens;

import java.io.Serializable;

import me.aartikov.alligator.FlowScreen;


public class TestFlowScreen implements FlowScreen, Serializable {
	private int mCounter;

	public TestFlowScreen(int counter) {
		mCounter = counter;
	}

	public int getCounter() {
		return mCounter;
	}
}
