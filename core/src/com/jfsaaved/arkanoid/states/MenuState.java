package com.jfsaaved.arkanoid.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 *
 * Author: Julian Saavedra
 * E-mail: julian.felipe.saavedra@gmail.com
 * Date: November 8, 2015
 *
 */

public class MenuState extends State {

    private Label name;
    private Label me;
    private Label start;

    public MenuState(GSM gsm){
        super(gsm);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        name = new Label("Simple Arkanoid Game", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        me = new Label("by Julian Saavedra", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        start = new Label("Touch to begin.", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table.add(name); table.row();
        table.add(me); table.row();
        table.add(start);

    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm,0));
        }
    }

    @Override
    protected void update(float dt) {
        handleInput();
    }

    @Override
    protected void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        stage.draw();
        sb.end();
    }

    @Override
    protected void renderShapes(ShapeRenderer sr) {

    }

    @Override
    protected void dispose() {
        stage.dispose();
    }
}
