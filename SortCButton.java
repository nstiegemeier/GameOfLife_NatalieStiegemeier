import greenfoot.*;

/**
 * A clickable button that sorts specimens according to columns from lowest to highest.
 * 
 * @author Natalie Stiegemeier
 * @version 10/15/2015
 */
public class SortCButton extends GenericButton
{
     /**
     * labels the button "Sort-C"
     */
    public SortCButton()
    {
        super("Sort-C");
    }   
    
    // pauses world when clicked, sets label of go/pause button to "Go" then sorts columns
    public void doButtonWork() 
    {
        //Pauses simulation
        ((LifeWorld) getWorld()).setPaused(true);
        GoPauseButton myButton = (GoPauseButton) getWorld().getObjects(GoPauseButton.class).get(0);
        myButton.setLabel("Go");
        //Sorts columns with the smallest number of specimens to the left and the greatest number of specimens to the right
        ((LifeWorld) getWorld()).sortColumns();
        ((LifeWorld) getWorld()).updateGenerationGrid();
        ((LifeWorld) getWorld()).loadSpecimensFromGrid();  
       
    }
}    

