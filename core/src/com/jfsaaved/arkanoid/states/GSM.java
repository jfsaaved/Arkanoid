package com.jfsaaved.arkanoid.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Stack;


/**
 *
 * Author: Julian Saavedra
 * E-mail: julian.felipe.saavedra@gmail.com
 * Date: November 8, 2015
 *
 */

public class GSM {

    private Stack<State> states;

    public GSM(){
        states = new Stack<State>();
    }

    public void push(State state){
        states.push(state);
    }

    public void pop(){
        states.peek().dispose();
        states.pop();
    }

    public void set(State state){
        states.peek().dispose();
        states.pop();
        states.push(state);
    }

    public void update(float dt){
        states.peek().update(dt);
    }

    public void render(SpriteBatch sb){
        states.peek().render(sb);
    }

    public void renderShape(ShapeRenderer sr){
        states.peek().renderShapes(sr);
    }


}
