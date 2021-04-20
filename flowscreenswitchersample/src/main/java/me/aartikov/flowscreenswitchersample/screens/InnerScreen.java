package me.aartikov.flowscreenswitchersample.screens;

import java.io.Serializable;

import me.aartikov.alligator.Screen;

public class InnerScreen implements Screen, Serializable {
    private int mCounter;

    public InnerScreen(int counter) {
        mCounter = counter;
    }

    public int getCounter() {
        return mCounter;
    }
}
