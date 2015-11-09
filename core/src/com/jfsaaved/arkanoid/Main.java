package com.jfsaaved.arkanoid;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jfsaaved.arkanoid.screens.PlayScreen;

/**
 *
 * Author: Julian Saavedra
 * E-mail: julian.felipe.saavedra@gmail.com
 * Date: November 8, 2015
 *
 */

public class Main extends Game {

    public static final int V_WIDTH = 400;
    public static final int V_HEIGHT = 800;

	public SpriteBatch sb;
    public ShapeRenderer sr;
	
	@Override
	public void create () {
		sb = new SpriteBatch();
        sr = new ShapeRenderer();
        setScreen(new PlayScreen(this,0));
	}

	@Override
	public void render () {
        super.render();
	}
}
