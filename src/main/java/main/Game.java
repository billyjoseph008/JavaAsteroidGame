package main;

import gameObjects.Constants;
import graphics.Assets;
import input.KeyBoard;
import states.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends JFrame implements Runnable {

    private Canvas canvas;
    private Thread mainThread;
    private boolean isRunning = false;

    private BufferStrategy bufferStrategy;
    private Graphics graphics;

    private final int FPS = 60;
    private double TARGETTIME = 1000000000/FPS;
    private double delta = 0;
    private int AVERAGEFPS = FPS;

    private GameState gameState;

    private KeyBoard keyBoard;

    public Game(){

        setTitle("Space Game");
        setSize(Constants.WIDTH, Constants.HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        canvas = new Canvas();
        keyBoard = new KeyBoard();
        canvas.setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
        canvas.setMaximumSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
        canvas.setMinimumSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
        canvas.setFocusable(true);
        add(canvas);
        canvas.addKeyListener(keyBoard);
        setVisible(true);
    }

    public static void main(String args[]){
        Game game  = new Game();
        game.start();
    }

    private void update(){
        keyBoard.update();
        gameState.update();
    }

    private void draw(){

        bufferStrategy = canvas.getBufferStrategy();
        if(bufferStrategy == null){
            canvas.createBufferStrategy(3);
            return;
        }

        graphics = bufferStrategy.getDrawGraphics();
        graphics.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);
        graphics.setColor(Color.BLACK);
        gameState.draw(graphics);
        graphics.drawString("FPS: " + AVERAGEFPS, 10, 20);
        graphics.dispose();
        bufferStrategy.show();
    }

    private void init(){
        Assets.init();
        gameState = new GameState();
    }

    @Override
    public void run() {

        long now = 0;
        long lastTime = System.nanoTime();
        int frames = 0;
        long time = 0;

        init();

        while(isRunning){

            now = System.nanoTime();
            delta += (now - lastTime) / TARGETTIME;
            time += (now - lastTime);
            lastTime = now;

            if(delta >= 1) {
                update();
                draw();
                delta --;
                frames ++;
            }

            if(time >= 1000000000){
               AVERAGEFPS = frames;
               frames = 0;
               time = 0;
            }
        }
        stop();
    }

    private void start(){
        mainThread = new Thread(this);
        mainThread.start();
        isRunning = true;
    }

    private void stop() {
        try {
            mainThread.join();
            isRunning = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
