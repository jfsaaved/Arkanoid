package com.jfsaaved.arkanoid.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;


/**
 *
 * Author: Julian Saavedra
 * E-mail: julian.felipe.saavedra@gmail.com
 * Date: November 8, 2015
 *
 */

public class Ball extends GameObject{

    private Circle circle;
    private float speed;
    private float angle;

    public Ball(float x, float y, float radius){
        circle = new Circle(x, y, radius);
    }

    public void setSpeed(float speed){
        this.speed = speed;
    }

    public float getSpeed(){
        return speed;
    }

    public void setAngle(double angle){
        this.angle = (float) angle;
    }

    public int getAngle(){
        return (int) angle;
    }

    public void update(float dt){

        float scaleX = (float) Math.cos(angle);
        float scaleY = (float) Math.sin(angle);
        float velocityX = speed * scaleX;
        float velocityY = speed * scaleY;

        float ballX = circle.x;
        float ballY = circle.y;

        ballX = ballX + (velocityX * dt);
        ballY = ballY + (velocityY * dt);

        circle.setPosition(ballX, ballY);

    }

    @Override
    public void setX(float x) {
        circle.setX(x);
    }

    @Override
    public void setY(float y) {
        circle.setY(y);
    }

    @Override
    public float getX() {
        return circle.x;
    }

    @Override
    public float getY() {
        return circle.y;
    }

    @Override
    public float getWidth() { return circle.circumference(); }

    @Override
    public float getHeight() { return circle.circumference(); }

    @Override
    public boolean contains(float x, float y){ return circle.contains(x, y); }

    @Override
    public void render(SpriteBatch sb) {}

    @Override
    public void renderShape(ShapeRenderer sr) {
        sr.circle(circle.x, circle.y, circle.radius);
    }

    @Override
    public void dispose() {

    }
}
