import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;

/**
 * creates specimens for world. They are blue squares.
 * 
 * @author Natalie Stiegemeier
 * @version 10/15/2015
 */
public class Specimen extends Actor
{
    /**
     * Constructor for a specimen to add into the grid (world)
     * 
     *   @param width - the width of the new specimen's image
     *   @param height - the height of the new specimen's image
     */
    public Specimen(int width, int height)
    {
        // build a blue rectangel(square),
        GreenfootImage img = new GreenfootImage(width, height);
        img.setColor(Color.blue);
        img.fill();
        
        // make this blue square the Actor's image. 
        setImage(img);
    }


}
