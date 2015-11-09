package com.jfsaaved.arkanoid.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
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

public class HUD {

    private Stage stage;
    private int score;

    private Label scoreLabel;

    public HUD(SpriteBatch sb, Viewport viewport, int score){
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        this.score = score;
        Label fName = new Label("JULIAN", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label lName = new Label("SAAVEDRA", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", this.score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(fName).expandX().padTop(10);
        table.add(lName).expandX().padTop(10);
        table.add(scoreLabel).expandX().padTop(10);

        stage.addActor(table);

    }

    public void addScore(int value){
        this.score += value;
    }

    public void updateScore(){
        scoreLabel.setText(String.format("%06d", score));
    }

    public Stage getStage(){
        return stage;
    }
}
