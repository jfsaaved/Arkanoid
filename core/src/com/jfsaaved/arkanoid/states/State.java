package com.jfsaaved.arkanoid.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jfsaaved.arkanoid.Main;


/**
 *
 * Author: Julian Saavedra
 * E-mail: julian.felipe.saavedra@gmail.com
 * Date: November 8, 2015
 *
 */

public abstract class State {

    protected GSM gsm;
    protected OrthographicCamera camera;
    protected Vector3 mouse;
    protected Stage stage;

    public State(GSM gsm){
        this.gsm = gsm;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Main.WIDTH, Main.HEIGHT);
        mouse = new Vector3();

        Viewport viewport = new StretchViewport(Main.WIDTH, Main.HEIGHT, camera);
        stage = new Stage(viewport);
    }

    protected abstract void handleInput();
    protected abstract void update(float dt);
    protected abstract void render(SpriteBatch sb);
    protected abstract void renderShapes(ShapeRenderer sr);
    protected abstract void dispose();


}
