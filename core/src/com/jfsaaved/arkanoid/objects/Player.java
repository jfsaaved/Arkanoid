package com.jfsaaved.arkanoid.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;


/**
 *
 * Author: Julian Saavedra
 * E-mail: julian.felipe.saavedra@gmail.com
 * Date: November 8, 2015
 *
 */

public class Player extends GameObject {

    private Rectangle rectangle;

    public Player(float x, float y, float width, float height){
        rectangle = new Rectangle(x, y, width, height);
    }

    @Override
    public void setX(float x){ rectangle.setX(x); }

    @Override
    public void setY(float y){
        rectangle.setY(y);
    }

    @Override
    public float getX() {
        return rectangle.x;
    }

    @Override
    public float getY() {
        return rectangle.y;
    }

    @Override
    public float getWidth() { return rectangle.getWidth(); }

    @Override
    public float getHeight() { return rectangle.getHeight(); }

    @Override
    public boolean contains(float x, float y){ return rectangle.contains(x, y); }

    @Override
    public void render(SpriteBatch sb) {}

    @Override
    public void renderShape(ShapeRenderer sr) {
        sr.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    @Override
    public void dispose(){}
}
