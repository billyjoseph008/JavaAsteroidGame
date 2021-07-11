package graphics;

import java.awt.image.BufferedImage;

public class Assets {

    public static BufferedImage player;

    //Effects
    public static BufferedImage propultion;
    public static BufferedImage blueLaser;
    public static BufferedImage redLaser;
    public static BufferedImage greenLaser;

    public static void init(){
        player = Loader.ImageLoader("/ships/player.png");
        propultion = Loader.ImageLoader("/effects/propultion.png");
        blueLaser = Loader.ImageLoader("/lasers/blueLaser.png");
        redLaser = Loader.ImageLoader("/lasers/redLaser.png");
        greenLaser = Loader.ImageLoader("/lasers/greenLaser.png");
    }

}
