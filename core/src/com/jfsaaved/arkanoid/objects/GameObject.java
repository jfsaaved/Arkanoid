package com.jfsaaved.arkanoid.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 *
 * Author: Julian Saavedra
 * E-mail: julian.felipe.saavedra@gmail.com
 * Date: November 8, 2015
 *
 */

public abstract class GameObject {

    public abstract void setX(float x);
    public abstract void setY(float y);
    public abstract float getX();
    public abstract float getY();

    public abstract float getWidth();
    public abstract float getHeight();
    public abstract boolean contains(float x, float y);

    public abstract void render(SpriteBatch sb);
    public abstract void renderShape(ShapeRenderer sr);
    public abstract void dispose();

}
