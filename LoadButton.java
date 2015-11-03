import greenfoot.*;
import java.awt.FileDialog;
import java.util.*;
import java.io.*;
import java.lang.*;

/**
 * Allows user to select file and upload new generation. Pauses the grid at first and changes the go/pause button label to "Go." The first line in the file specifies
 * rows and columns. Then next lines represenet the presence or absense of specimens in grid. 
 * 
 * @author Natalie Stiegemeier
 * @version 10/15/2015
 */
public class LoadButton extends GenericButton
{
    /**
     * labels the button "Load"
     */
    public LoadButton()
    {
        super("Load");
    }   
    
    // pauses world, labels go/pause button as "Go" and calls load file
    public void doButtonWork() 
    {
        //Pauses simulation
        ((LifeWorld) getWorld()).setPaused(true);
        GoPauseButton myButton = (GoPauseButton) getWorld().getObjects(GoPauseButton.class).get(0);
        myButton.setLabel("Go");
        //Loads file
        this.loadFile();
    }
    
    /**
     * Loads selected file into world. 
     */
    public void loadFile() 
    {     
        String filename; // name of file selected
        FileDialog myDialog = null;
        myDialog = new FileDialog(myDialog, "Select a file", FileDialog.LOAD);
        myDialog.setVisible(true);
        
        filename = myDialog.getDirectory() + myDialog.getFile();
        //allow user to select file name and load it, if error, asume user clicked cancel and return
        Scanner myReader;
        try
        {
            File myFile = new File(filename);
            myReader = new Scanner(myFile);
        }
        catch(IOException e)
        {
            System.out.println("Clicked cancel");
            myReader=null;
            return;
        }
        //Clear specimens from world
        getWorld().removeObjects(getWorld().getObjects(Specimen.class));
        int counter = 0; // total file line count
        int gridSize = 0; // grid size according to file
        String nextChar = "";
        String nextString = "";
        
        //loops through file until it reaches end and sets and populates grids with specimens.
        while (myReader.hasNext())
        {
            if(counter==0)
           {
               gridSize = myReader.nextInt();
               ((LifeWorld) getWorld()).buildBG(gridSize);
               ((LifeWorld) getWorld()).createGrid(gridSize);
           }
           else
           {
               nextString = myReader.next();
               for(int i=0; i<gridSize; i++)
               {
                   nextChar = nextString.substring(i,i+1);
                   if (nextChar.equals("."))
                    ((LifeWorld) getWorld()).setGrid(counter-1,i, false);
                   else
                    ((LifeWorld)getWorld()).setGrid(counter-1,i, true);
                }              
           }
           counter++;
        }
        
        ((LifeWorld) getWorld()).updateGenerationGrid(); // updates the grid for future generation
        ((LifeWorld) getWorld()).loadSpecimensFromGrid(); // loads new generation to grid
        myReader.close();
    }
    
}
