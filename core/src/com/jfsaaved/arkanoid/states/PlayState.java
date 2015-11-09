package com.jfsaaved.arkanoid.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.jfsaaved.arkanoid.Main;
import com.jfsaaved.arkanoid.objects.Ball;
import com.jfsaaved.arkanoid.objects.Brick;
import com.jfsaaved.arkanoid.objects.Player;

import java.util.Vector;


/**
 *
 * Author: Julian Saavedra
 * E-mail: julian.felipe.saavedra@gmail.com
 * Date: November 8, 2015
 *
 */

public class PlayState extends State {

    // Game variables
    private boolean start;
    private int score;
    private int numBricks; // Amount of bricks in the bricks object. Used to determine if level is completed.

    /**
     * Game objects
     * player is the paddle. Controlled by touch.
     * ball is the object to hit.
     * bricks is a collection of object brick. Player must aim to destroy all bricks.
     */
    private Player player;
    private Ball ball;
    private Vector<Brick> bricks;

    // Game HUD
    private Label scoreL;

    public PlayState(GSM gsm, int score){
        super(gsm);
        start = false;
        this.score = score;

        player = new Player(Main.WIDTH/2 - 50, 10, 100, 10);
        ball = new Ball(Main.WIDTH/2, player.getY() + 20, 10);

        bricks = new Vector<Brick>();
        for(int col = 500; col < 740; col += 40){
            for(int row = 0; row < Main.WIDTH; row += 40)
                bricks.add(new Brick(row,col,"brick.jpg"));
        }
        numBricks = bricks.size();

        /**
         * Add a score interface
         */
        Table table = new Table();
        table.setFillParent(true);
        table.setY(360);
        stage.addActor(table);
        scoreL = new Label("SCORE: "+score, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table.add(scoreL);

    }

    @Override
    protected void handleInput() {
        if(Gdx.input.isTouched()){
            mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(mouse);

            // Set so that the touch is at the middle of the player object.
            player.setX(mouse.x - player.getWidth()/2);
            if(player.getX() < 0)
                player.setX(0);
            if(player.getX() + player.getWidth() > Main.WIDTH)
                player.setX(Main.WIDTH - player.getWidth());

            if(!start){
                ball.setX(player.getX() + player.getWidth()/2);

                if(player.contains((int) mouse.x, (int) mouse.y)){
                    start = true;
                    ball.setSpeed(500);
                    ball.setAngle(Math.PI/2);
                }
            }
        }
    }

    @Override
    protected void update(float dt) {
        handleInput();
        ball.update(dt);
        scoreL.setText("SCORE: "+score);

        if(start) {
            detectBrickCollision();
            detectPlayerCollision();
            detectWallCollision();
            onDeadBall();
            onClear();
        }
    }

    /**
     * Collision detection methods
     * Methods are for the bricks, player and the wall (the game width and height)
     * ball.setSpeed(x) where is x either 500 or -500
     * 500 means ball is going up, -500 means it is going down
     */
    private void detectBrickCollision(){
        for(Brick brick : bricks){
            if(brick.contains(ball.getX(),ball.getY()) && !brick.getHide()){
                brick.setHide(true);
                // Redirect the angle of the ball to the opposite of its original angle
                if(ball.getAngle() == 2)
                    ball.setAngle(Math.PI / 4);
                else if(ball.getAngle() == 0)
                    ball.setAngle((3 * Math.PI) / 4);
                else if(ball.getAngle() == 1)
                    ball.setAngle(Math.PI/2);
                numBricks--;
                score++;
                ball.setSpeed(-500);
            }
        }
    }

    private void detectPlayerCollision(){
        if(player.contains(ball.getX(), ball.getY())){
            // Use the center of the player as a reference to check where to redirect the ball
            float center = player.getX() + player.getWidth()/2;
            if(ball.getX() > center)
                ball.setAngle(Math.PI/4);
            else if(ball.getX() < center)
                ball.setAngle((3*Math.PI)/4);
            else
                ball.setAngle(Math.PI/2);
            ball.setSpeed(500);
        }
    }

    private void detectWallCollision(){
        if(ball.getX() > Main.WIDTH) {
            if(ball.getSpeed() > 0)
                ball.setAngle((3 * Math.PI)/4);
            else
                ball.setAngle(Math.PI/4);
        }
        else if(ball.getX() < 0) {
            if(ball.getSpeed() > 0)
                ball.setAngle(Math.PI/4);
            else
                ball.setAngle((3 * Math.PI)/4);
        }

        if(ball.getY() > Main.HEIGHT){
            if(ball.getAngle() == 2)
                ball.setAngle(Math.PI / 4);
            else if(ball.getAngle() == 0)
                ball.setAngle((3 * Math.PI) / 4);
            else if(ball.getAngle() == 1)
                ball.setAngle(Math.PI/2);
            ball.setSpeed(-500);
        }
    }

    // If ball passed through the player
    private void onDeadBall(){
        if(ball.getY() < 0)
            gsm.set(new PlayState(gsm, 0));
    }

    // When all bricks are destroyed
    private void onClear(){
        if(numBricks <= 0)
            gsm.set(new PlayState(gsm, score));
    }

    @Override
    protected void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        for(Brick object : bricks)
            object.render(sb);
        sb.end();

        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        stage.draw();
        sb.end();
    }

    @Override
    protected void renderShapes(ShapeRenderer sr) {
        sr.setProjectionMatrix(camera.combined);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        player.renderShape(sr);
        ball.renderShape(sr);
        sr.setColor(1,1,0,1);
        sr.end();
    }

    @Override
    protected void dispose(){
        stage.dispose();
        for(Brick brick : bricks)
            brick.dispose();
    }
}
