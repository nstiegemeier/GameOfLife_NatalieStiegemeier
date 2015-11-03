import greenfoot.*;
import java.util.List;

/**
 * A clickable button that sorts specimens in rows with largest number of specimens at the top going down in descending order. 
 * 
 * @author Natalie Stiegemeier 
 * @version 10/15/2015
 */
public class SortRButton extends GenericButton
{
     /**
     * labels the button "Sort-R"
     */
    public SortRButton()
    {
        super("Sort-R");
    }   
    
    // pauses world, changes go/pause button label to "Go" then sorts rows
    public void doButtonWork() 
    {
        //Pauses simulation
        ((LifeWorld) getWorld()).setPaused(true);
        GoPauseButton myButton = (GoPauseButton) getWorld().getObjects(GoPauseButton.class).get(0);
        myButton.setLabel("Go");
        //Sorts rows with the largest number of specimens at the top and the fewest number of specimens at the bottom
        ((LifeWorld) getWorld()).sortRows();
        ((LifeWorld) getWorld()).updateGenerationGrid(); 
        ((LifeWorld) getWorld()).loadSpecimensFromGrid();  
        
    }
}

