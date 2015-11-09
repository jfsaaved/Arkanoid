package com.jfsaaved.arkanoid.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;;
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

import java.util.Random;
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
    private float scaleX;
    private float scaleY;

    /**
     * Game variables
     */
    private boolean start;

    public PlayScreen(Main game){
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new StretchViewport(Main.V_WIDTH, Main.V_HEIGHT, camera);

        initObjects();
        start = false;
    }

    /**
     * Populate the game
     * player is the paddle
     * ball is the object that hits the bricks
     * bricks is a collection of brick, that the player must aim to destroy
     */
    private void initObjects(){
        hud = new HUD(game.sb);
        player = new Rectangle(0,10,50,10);
        ball = new Circle(player.getX() + player.getWidth()/2,player.getY() + 20,10);

        //Create brick objects; these are the ones we hit with the ball.
        bricks = new Vector<Brick>();
        Texture texture = new Texture(Gdx.files.internal("brick.jpg"));
        for(int col = 500; col < 700; col += 40){
            for(int row = 0; row < Main.V_WIDTH; row += 40){
                bricks.add(new Brick(row,col,texture));
            }
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();
        update(delta);

        //Render the objects
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.sb.setProjectionMatrix(camera.combined);
        hud.stage.draw();
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

    /**
     * Collision detection methods
     */

    private void wallCollision(){
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
    }

    private void brickCollision(Brick brick){
        if(ball.intersects(brick.getRectangle().getX(),brick.getRectangle().getY(),
                            brick.getRectangle().getWidth(), brick.getRectangle().getHeight())
                            && !brick.getHide()){
            brick.setHide(true);

            if(angle > Math.PI/2)
                angle = (float) Math.PI / 4;
            else if(angle < Math.PI/2)
                angle = (float) (3 * Math.PI) / 4;
            else
                angle = (float) Math.PI/2;

            speed = -500;
        }
    }

    private void playerCollision(){
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

    private void update(float delta){
        handleInput();
        if(start){

            //Call collision detections for bricks
            for(Brick item : bricks)
                brickCollision(item);
            // Collision detection for player
            playerCollision();
            wallCollision();

            double ballX = ball.getCenterX();
            double ballY = ball.getCenterY();

            ballX = ballX + (velocityX * delta);
            ballY = ballY + (velocityY * delta);

            ball.setCenterX(ballX);
            ball.setCenterY(ballY);

            recalculateBall();
        }
    }

    private void recalculateBall(){
        scaleX = (float) Math.cos(angle);
        scaleY = (float) Math.sin(angle);
        velocityX = speed * scaleX;
        velocityY = speed * scaleY;
    }

    private void handleInput(){
        if(Gdx.input.isTouched()){
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(),0);
            viewport.unproject(touchPos);

            player.setPosition(touchPos.x - player.getWidth()/2,10);

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
