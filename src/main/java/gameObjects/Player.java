package gameObjects;

import graphics.Assets;
import input.KeyBoard;
import main.Game;
import math.Vector2D;
import states.GameState;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Player extends MovingObject {

    private Vector2D heading;
    private Vector2D aceleration;

    private boolean acelerating = false;

    private Chronometer fireRate;

    public Player(Vector2D position, Vector2D velocity, double maxVelocity, BufferedImage texture, GameState gameState) {
        super(position, velocity, maxVelocity, texture, gameState);
        this.gameState = gameState;
        heading = new Vector2D(0, 1);
        aceleration = new Vector2D();
        fireRate = new Chronometer();
    }

    @Override
    public void update() {

        if(KeyBoard.SHOOT && !fireRate.isRunning()){
            gameState.getMovingObjects().add(0, new Laser(
                    getCenter().addVectors(heading.scale(width/2)),
                    heading,
                    Constants.LASER_VELOCITY,
                    angle,
                    Assets.redLaser,
                    gameState
            ));
            fireRate.run(Constants.FIRERATE);
        }

        if(KeyBoard.RIGHT)
            angle += Constants.DELTAANGLE;
        if(KeyBoard.LEFT)
            angle -= Constants.DELTAANGLE;

        if(KeyBoard.UP){
            aceleration = heading.scale(Constants.ACELERATION_SENCIBILITY);
            acelerating = true;
        } else {
            if(velocity.getMagnitude()!=0){
                aceleration = (velocity.scale(-1).normalize()).scale(Constants.ACELERATION_SENCIBILITY/2);
                acelerating = false;
            }
        }

        velocity = velocity.addVectors(aceleration);

        velocity = velocity.limit(maxVelocity);

        heading = heading.setDirection(angle - Math.PI/2);

        position = position.addVectors(velocity);

        if(position.getX() > Constants.WIDTH)
            position.setX(0);
        if(position.getY() > Constants.HEIGHT)
            position.setY(0);
        if(position.getX() < 0 )
            position.setX(Constants.WIDTH);
        if(position.getY() < 0 )
            position.setY(Constants.HEIGHT);

        fireRate.update();
    }

    @Override
    public void draw(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;

        AffineTransform affineTransform1 = AffineTransform.getTranslateInstance(position.getX() + width/2 + 35,
                position.getY() + height/2 + 3);
        AffineTransform affineTransform2 = AffineTransform.getTranslateInstance(position.getX(), position.getY() + height/2);

        affineTransform1.rotate(angle, -35, -2);
        affineTransform2.rotate(angle, width/2, 0);


        if(acelerating){
            graphics2D.drawImage(Assets.propultion, affineTransform1, null);
            graphics2D.drawImage(Assets.propultion, affineTransform2, null);
        }

        affineTransform = AffineTransform.getTranslateInstance(position.getX(), position.getY());
        affineTransform.rotate(angle, width/2, height/2);
        graphics2D.drawImage(Assets.player, affineTransform, null);
    }

    public Vector2D getCenter(){
        return new Vector2D(position.getX() + width/2, position.getY() + height/2);
    }
    
}
