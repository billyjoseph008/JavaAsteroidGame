package gameObjects;

import main.Game;
import math.Vector2D;
import states.GameState;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Laser extends  MovingObject{

    public Laser(Vector2D position, Vector2D velocity, double maxVelocity, double angle, BufferedImage texture, GameState gameState) {
        super(position, velocity, maxVelocity, texture, gameState);
        this.angle = angle;
        this.velocity = velocity.scale(maxVelocity);
    }

    @Override
    public void update() {
        position = position.addVectors(velocity);
        if(position.getX() < 0 || position.getX() > Constants.WIDTH || position.getY() < 0 || position.getY() > Constants.HEIGHT){
            gameState.getMovingObjects().remove(this);
        }
    }

    @Override
    public void draw(Graphics graphics) {

        Graphics2D graphics2D = (Graphics2D) graphics;
        affineTransform = AffineTransform.getTranslateInstance(position.getX() - width/2, position.getY());
        affineTransform.rotate(angle, width/2, 0);
        graphics2D.drawImage(texture, affineTransform, null);

    }
}
