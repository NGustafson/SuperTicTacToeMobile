package com.ngustafson247.framework;

/**
 * Created by Nick on 3/18/2015.
 */
public abstract class Screen {
    protected final Game game;
    protected final boolean hasBackFunction;

    public Screen(Game game, boolean hasBackFunction) {
        this.game = game;
        this.hasBackFunction = hasBackFunction;
    }

    public abstract void update(float deltaTime);

    public abstract void paint(float deltaTime);

    public abstract void pause();

    public abstract void resume();

    public abstract void dispose();

    public boolean hasBackFunction() {
        return hasBackFunction;
    }

    public abstract void backButton();
}
