package com.mijuego.cuttherope;

import com.badlogic.gdx.Game;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    @Override
    public void create() {
        this.setScreen(new MenuPrincipal());
    }
}
