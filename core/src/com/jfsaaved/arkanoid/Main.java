package com.jfsaaved.arkanoid;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jfsaaved.arkanoid.states.GSM;
import com.jfsaaved.arkanoid.states.PlayState;

import javafx.stage.Stage;

/**
 *
 * Author: Julian Saavedra
 * E-mail: julian.felipe.saavedra@gmail.com
 * Date: November 8, 2015
 *
 */

public class Main extends ApplicationAdapter {

    public static final String TITLE = "Arkanoid by Julian Saavedra";
    public static final int WIDTH = 400;
    public static final int HEIGHT = 800;

    private GSM gsm;
	private SpriteBatch sb;
    private ShapeRenderer sr;
	
	@Override
	public void create () {
        sb = new SpriteBatch();
        sr = new ShapeRenderer();
        gsm = new GSM();
        gsm.push(new PlayState(gsm, 0));
	}

	@Override
	public void render () {
        gsm.update(Gdx.graphics.getDeltaTime());

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gsm.render(sb);
        gsm.renderShape(sr);
	}


}
