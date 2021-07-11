package gameObjects;

import math.Vector2D;
import states.GameState;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public abstract class MovingObject  extends GameObject{

    protected Vector2D velocity;
    protected AffineTransform affineTransform;
    protected double angle;
    protected double maxVelocity;
    protected int width;
    protected int height;
    protected GameState gameState;

    public MovingObject(Vector2D position, Vector2D velocity, double maxVelocity, BufferedImage texture, GameState gameState) {
        super(position, texture);
        this.velocity = velocity;
        this.maxVelocity = maxVelocity;
        this.gameState = gameState;
        width = texture.getWidth();
        height = texture.getWidth();
        angle = 0;
    }

}
