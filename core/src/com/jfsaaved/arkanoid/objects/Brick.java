package com.jfsaaved.arkanoid.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 *
 * Author: Julian Saavedra
 * E-mail: julian.felipe.saavedra@gmail.com
 * Date: November 8, 2015
 *
 */
public class Brick {

    private Rectangle rectangle;
    private Texture texture;
    private boolean hide;

    public Brick(int x, int y, Texture texture){
        this.texture = texture;
        this.rectangle = new Rectangle(x,y,texture.getWidth(),texture.getHeight());
        hide = false;
    }

    public Rectangle getRectangle(){
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle){
        this.rectangle = rectangle;
    }

    public Texture getTexture(){
        return texture;
    }

    public void setTexture(Texture texture){
        this.texture = texture;
    }

    public void setHide(boolean b){
        hide = b;
    }

    public boolean getHide(){
        return hide;
    }

    public void draw(SpriteBatch sb){
        if(!hide)
            sb.draw(texture,rectangle.getX(),rectangle.getY());
    }

}
