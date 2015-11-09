package com.jfsaaved.arkanoid.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jfsaaved.arkanoid.Main;
import com.jfsaaved.arkanoid.objects.Brick;
import com.jfsaaved.arkanoid.scenes.HUD;
import java.util.Vector;

import javafx.scene.shape.Circle;

/**
 *
 * Author: Julian Saavedra
 * E-mail: julian.felipe.saavedra@gmail.com
 * Date: November 8, 2015
 *
 */

public class PlayScreen implements Screen {

    private Main game;
    private OrthographicCamera camera;
    private Viewport viewport;

    /**
     * Game Objects
     */
    private HUD hud;
    private Rectangle player;
    private Circle ball;
    private Vector<Brick> bricks;

    /**
     * Ball properties
     */

    private float velocityX;
    private float velocityY;
    private float speed;
    private float angle;

    /**
     * Game variables
     */
    private int score;
    private boolean start;
    private int numBricks;

    public PlayScreen(Main game, int score){
        this.game = game;
        this.score = score;
        camera = new OrthographicCamera();
        viewport = new StretchViewport(Main.V_WIDTH, Main.V_HEIGHT, camera);
        hud = new HUD(game.sb, viewport, score);
        start = false;
    }

    /**
     * Populate the game
     * player is the paddle
     * ball is the object that hits the bricks
     * bricks is a collection of brick, that the player must aim to destroy
     */
    private void initObjects(){
        player = new Rectangle(Main.V_WIDTH/2,10,50,10);
        ball = new Circle(player.getX() + player.getWidth()/2,player.getY() + 20,10);

        //Create brick objects; these are the ones we hit with the ball.
        bricks = new Vector<Brick>();
        Texture texture = new Texture(Gdx.files.internal("brick.jpg"));
        for(int col = 500; col < 740; col += 40){
            for(int row = 0; row < Main.V_WIDTH; row += 40){
                bricks.add(new Brick(row,col,texture));
                numBricks++;
            }
        }
    }

    @Override
    public void show() {
        initObjects();
    }

    @Override
    public void render(float delta) {
        camera.update();
        hud.updateScore();
        update(delta);

        //Render the objects
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.sb.setProjectionMatrix(camera.combined);
        hud.getStage().draw();
        game.sb.begin();
        for(Brick item : bricks)
            item.draw(game.sb);
        game.sb.end();

        game.sr.setProjectionMatrix(camera.combined);
        game.sr.begin(ShapeRenderer.ShapeType.Filled);
        game.sr.setColor(1,1,0,1);
        game.sr.circle((float)ball.getCenterX(), (float)ball.getCenterY(), (float)ball.getRadius());
        game.sr.rect(player.getX(),player.getY(),player.getWidth(),player.getHeight());
        game.sr.end();

    }

    private void update(float delta){
        handleInput();
        if(start){

            //Call collision detections for bricks
            for(Brick item : bricks)
                onBrickCollision(item);

            onPlayerCollision();
            onWallCollision();
            onDeadBall();
            onClear();

            double ballX = ball.getCenterX();
            double ballY = ball.getCenterY();

            ballX = ballX + (velocityX * delta);
            ballY = ballY + (velocityY * delta);

            ball.setCenterX(ballX);
            ball.setCenterY(ballY);

            recalculateBall();
        }
    }

    /**
     * Collision detection methods
     * onWall is the boundary of the viewport
     * onBrick is for the brick objects
     * onPlayer is for the paddle
     *
     * NOTE: -500 on speed, means it's going down, 500 on speed means it's going up
     */

    private void onWallCollision(){
        if((ball.getCenterX() + ball.getRadius()/2) > Main.V_WIDTH) {
            if(speed > 0)
                angle = (float) (3 * Math.PI) / 4;
            else
                angle = (float) Math.PI / 4;
        }
        else if((ball.getCenterX() + ball.getRadius()/2) < 0) {
            if(speed > 0)
                angle = (float) Math.PI / 4;
            else
                angle = (float) (3 * Math.PI) / 4;
        }

        if((ball.getCenterY() + ball.getRadius()/2) > Main.V_HEIGHT){
            if((int) angle == 2)
                angle = (float) Math.PI / 4;
            else if((int) angle == 0)
                angle = (float) (3 * Math.PI) / 4;
            else if((int) angle == 1)
                angle = (float) Math.PI/2;

            speed = -500;
        }
    }

    private void onBrickCollision(Brick brick){
        if(ball.intersects(brick.getRectangle().getX(),brick.getRectangle().getY(),
                brick.getRectangle().getWidth(), brick.getRectangle().getHeight())
                && !brick.getHide()){
            brick.setHide(true);
            if((int) angle == 2)
                angle = (float) Math.PI / 4;
            else if((int) angle == 0)
                angle = (float) (3 * Math.PI) / 4;
            else if((int) angle == 1)
                angle = (float) Math.PI/2;
            speed = -500;
            numBricks--;
            hud.addScore(1);
        }
    }

    private void onPlayerCollision(){
        if(ball.intersects(player.getX(), player.getY(),player.getWidth(), player.getHeight())){
            float center = player.getX() + player.getWidth()/2;
            if(ball.getCenterX() > center)
                angle = (float) Math.PI/4;
            else if(ball.getCenterX() < center)
                angle = (float) (3*Math.PI)/4;
            else
                angle = (float) Math.PI/2;
            speed = 500;
        }
    }

    // Reset the game if the ball (y) goes lower than 0
    private void onDeadBall(){
        if(ball.getCenterY() < 0)
            game.setScreen(new PlayScreen(game,0));
    }

    private void onClear(){
        if( numBricks <= 0)
            game.setScreen(new PlayScreen(game,score));
    }

    private void recalculateBall(){
        float scaleX = (float) Math.cos(angle);
        float scaleY = (float) Math.sin(angle);
        velocityX = speed * scaleX;
        velocityY = speed * scaleY;
    }

    // A method that calculate mouse/finger position for paddle interaction
    private void handleInput(){
        if(Gdx.input.isTouched()){
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(),0);
            viewport.unproject(touchPos);


            player.setX(touchPos.x - player.getWidth()/2);

            // To avoid player getting off boundary
            if(player.getX() < 0)
                player.setX(0);
            if(player.getX() + player.getWidth() > Main.V_WIDTH)
                player.setX(Main.V_WIDTH - player.getWidth());

            if(!start) {
                ball.setCenterX(player.getX() + player.getWidth()/2);
                ball.setCenterY(player.getY() + 20);

                if(player.contains(touchPos.x, touchPos.y)){
                    start = true;
                    speed = 500;
                    angle = (float) Math.PI/2;
                    recalculateBall();
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        for(Brick item : bricks)
            item.getTexture().dispose();
    }
}
