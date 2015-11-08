package com.jfsaaved.arkanoid.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jfsaaved.arkanoid.Main;
import com.jfsaaved.arkanoid.objects.Brick;
import com.jfsaaved.arkanoid.scenes.HUD;

import java.util.Random;
import java.util.Vector;

import javafx.scene.shape.Circle;

/**
 * Created by 343076 on 08/11/2015.
 */
public class PlayScreen implements Screen {

    private Main game;
    private OrthographicCamera camera;
    private Viewport viewport;

    /*
    Game objects
     */
    private HUD hud;
    private Rectangle player;
    private Circle ball;
    private Vector<Brick> bricks;

    private float velocityX;
    private float velocityY;

    private float speed;
    private float angle;

    /*
    Game variables
     */
    private boolean start;

    public PlayScreen(Main game){
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new StretchViewport(Main.V_WIDTH, Main.V_HEIGHT, camera);
    }

    private void initObjects(){
        hud = new HUD(game.sb);
        player = new Rectangle(0,10,50,10);
        ball = new Circle(player.getX() + player.getWidth()/2,player.getY() + 20,10);

        /*
        Create brick objects; these are the ones we hit with the ball.
         */
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
        initObjects();
        start = false;
    }

    @Override
    public void render(float delta) {
        camera.update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.sb.setProjectionMatrix(camera.combined);
        hud.stage.draw();
        game.sb.begin();
        for(Brick item : bricks)
            game.sb.draw(item.getTexture(), item.getRectangle().getX(), item.getRectangle().getY());
        game.sb.end();

        game.sr.setProjectionMatrix(camera.combined);
        game.sr.begin(ShapeRenderer.ShapeType.Filled);
        game.sr.setColor(1,1,0,1);
        game.sr.circle((float)ball.getCenterX(), (float)ball.getCenterY(), (float)ball.getRadius());
        game.sr.rect(player.getX(),player.getY(),player.getWidth(),player.getHeight());
        game.sr.end();

        update(delta);
    }

    private void update(float delta){
        handleInput();
        if(start){
            double ballY = ball.getCenterY();
            ballY = ballY + (velocityX * delta);
            ball.setCenterY(ballY);
        }
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
            }

            if(player.contains(touchPos.x, touchPos.y)){
                start = true;
                speed = 50;
                angle = 90;

                float scaleY = (float) Math.sin(angle);
                velocityX = speed * scaleY;
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

    }
}
