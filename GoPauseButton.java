import greenfoot.*;

/**
 * A clickable button to make world generations pause or go. Labels button as "Go" or "Pause" depending on whether it's paused or not.
 * 
 * @author Natalie Stiegemeier
 * @version 10/15/2015
 */
public class GoPauseButton extends GenericButton
{
    /**
     * labels the button "Go"
     */
    public GoPauseButton()
    {
        super("Go");
    }   
    
    // pauses world and labels button "Pause" or unpauses world and labels button "Go"
    public void doButtonWork() 
    {
        if (this.getLabel().equals("Go"))
            {
                this.setLabel("Pause");
                //Pauses simulation
                ((LifeWorld) getWorld()).setPaused(false);
            }
        else
            {
             this.setLabel("Go");
             ((LifeWorld) getWorld()).setPaused(true);
            }
    }
}


