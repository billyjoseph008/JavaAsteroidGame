package states;

import gameObjects.Constants;
import gameObjects.MovingObject;
import gameObjects.Player;
import graphics.Assets;
import math.Vector2D;

import java.awt.*;
import java.util.ArrayList;

public class GameState {

    private Player player;

    private ArrayList<MovingObject> movingObjects = new ArrayList<MovingObject>();

    public GameState(){
        player = new Player(new Vector2D(400, 300), new Vector2D(), Constants.PLAYER_MAX_VELOCITY, Assets.player, this);
        movingObjects.add(player);
    }

    public void update(){
        for(int i = 0; i < movingObjects.size(); i++){
            movingObjects.get(i).update();
        }
    }

    public void draw(Graphics graphics){

        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        for(int i = 0; i < movingObjects.size(); i++){
            movingObjects.get(i).draw(graphics);
        }
    }

    public ArrayList<MovingObject> getMovingObjects() {
        return movingObjects;
    }
}
