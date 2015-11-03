import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.*;
import java.io.*;
import java.util.List;

/**
 * Builds a life simulation of a two dimensional grid of square where some are empty and some are filled with a specimen. New generations are created that cause squares to lose or 
 * gain specimens. Specimens can be sorted according to either row or column. 
 * 
 * @author Natalie Stiegemeier
 * @version 10/15/2015
 */
public class LifeWorld extends World
{
    private int boardSize;     // number of rows and columns the "game" board has
    private int squareWidth;   // width in pixels of one board square
    private int squareHeight;  // height in pixels of one board square
    private boolean paused;    // tracks when paused button has been pressed
    private boolean[][] currentGenGrid; // tracks specimens in current generation
    private boolean[][] futureGenGrid; // tracks specimens in future generation
    
    /**
     * Constructor for objects of class LifeWorld.
     * 
     */
    public LifeWorld()
    {    
        // build world that is (within reason) big enough to handle most games. 
        super(800, 700, 1); 
        LoadButton myLoad = new LoadButton(); // button for load class
        SortRButton mySort = new SortRButton(); // button for sorting rows class
        GoPauseButton myGoPause = new GoPauseButton(); // button for pausing/go class
        SortCButton mySortC = new SortCButton(); // button for sorting columns class
        int y = 2 + myLoad.getImage().getHeight(); // starting y position for top button
        int x = 40; // x position for the buttons
        addObject(myLoad, x, y); // places load button in world
        y+= 5 + mySort.getImage().getHeight()/2 + myLoad.getImage().getHeight()/2; // new y position for second button, below first button
        addObject(mySort, x, y); // places sort rows button in world
        y+= 5 + mySortC.getImage().getHeight()/2 + mySort.getImage().getHeight()/2; // new y position for third button, below previous button
        addObject(mySortC, x, y); // places sort columns button in world
        y+= 5 + myGoPause.getImage().getHeight()/2 + mySortC.getImage().getHeight()/2; // new y position for fourth button, below previous button
        addObject(myGoPause, x, y); // places pause/go button in world
        
        this.setPaused(true); // initially pauses world 
        this.buildBG(11); // builds initial grid in world 11x11
        // creates 9 specimens in 3x3 square in center of grid of squares
        this.currentGenGrid= new boolean[][]
            {
            {false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,true,true,true,false,false,false,false},
            {false,false,false,false,true,true,true,false,false,false,false},
            {false,false,false,false,true,true,true,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false}
            };
        this.loadSpecimensFromGrid(); // places those 9 specimens in world
        
        // initially places no specimens in future generation
        this.futureGenGrid= new boolean[][]
            {
            {false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false},
            {false,false,false,false,false,false,false,false,false,false,false}
            };

    }
      
    // utility method - given a board row number, returns corresponding y-value of pixel
    // in dead center of row
    //   rowNumber: the row number to use (noting that such start at 0).
    private int rowToY(int rowNumber)
    {
        // board starts 25 pixels from top,
        //  we need to go rowNumber rows from there, each of which is squareHeight high,
        //  and then move into middle of that row, 
        return 25 +  rowNumber*squareHeight + squareHeight/2;
    }
        
    // utility method - given a board column number, returns corresponding x-value of pixel
    // in dead center of column
    //   colNumber: the column number to use (noting that such start at 0).
    private int colToX(int colNumber)
    {
        // board starts 125 pixels from left,
        //  we need to go colNumber columns from there, each of which is squareWidth wide,
        //  and then move into middle of that column, 
        return 125 +  colNumber*squareWidth + squareWidth/2;
    }

    /**
     * buildBG - builds the background image for the world, based on the size of the board.
     *   @param boardSize - the size of the board (same as number of rows or columns)
     */
    public void buildBG(int boardSize)
    {
        // remember the boardSize (store in instance variable)
        this.boardSize = boardSize;
        
        // calculate dimensions  of board's squares in pixels
        squareWidth = (getWidth() - 150)/boardSize;
        squareHeight = (getHeight()-50)/boardSize;
        
        // next, build up a Greenfoot Image for the world's background. 
        GreenfootImage bgImage = new GreenfootImage(getWidth()-1,getHeight()-1);
        bgImage.setColor(Color.lightGray);
        
        int verticalLineX = 125;  // start drawing vertical lines on board at x=125
        int horizontalLineY = 25; // start drawing horizontal lines on board at y=25
        
        // draw boardSize+1 vertical and horizontal lines
        for(int lineNumber=0; lineNumber<boardSize+1; lineNumber++)
        {
            //draw next vertical line
            bgImage.drawLine( verticalLineX,25  , verticalLineX,25+squareHeight*boardSize);
            
            // draw next horizontal line
            bgImage.drawLine( 125,horizontalLineY  , 125+squareWidth*boardSize, horizontalLineY);
            
            verticalLineX += squareWidth;    // move on to next vertical line
            horizontalLineY += squareHeight; // move on to next horizontal line
        }
        
        // update world background image accordingly. 
        setBackground(bgImage);
    }
    
    /**
     * getBoardSize - returns the number of squares along one side of the board (remember that the
     *      board is a square grid (of squares, each of which may or may not contain a specimen). 
     *      
     * @return the side length of the current simulation board
     */
    public int getBoardSize()
    {
        return boardSize;
    }

    /**
     * setBoardSize - sets the board's sidelength to the number of squares specified. NOTE THAT THIS
     *       WILL REDRWAW THE WORLD'S BACKGROUND!
     *      
     * @param newBoardSize -  the new side length for the simulation board
     */
    public void setBoardSize(int newBoardSize)
    {
        buildBG(newBoardSize);
    }
    
    /**
    * Checks to see if world is paused
    * @return the world either pauses or unpaused
    */
    public boolean getPaused()
    {
        return this.paused;
    }
    
    /**
    * @param action - tells world to pause or unpause
    */
    public void setPaused(boolean action)
    {
        this.paused = action;
    }
    
    /**
     * while world isn't paused, each square in game board counts how many of its neighboring 8 squares (left, right, up, down, & diagonally) contain a specimen while not counting
     * itself. If a specimen is currently in a square and its neighbor count is less than 2 or more than 3, it will die and square will be empty for next generation. If their
     * neighbor count is 2 of 3, it lives in current square in next generation. If there isn't currently a specimen in a square and its neighbor count is 3, a new specimen is born into
     * the square in the next generation but if its neighbor count isn't 3, it remains empty in next generation. *Current generation is being calculated, not next generation or combo
     * of both*
     */
    public void act()
    {
        int neighbors=0; // number of neighbors throughout current generation
        int evalRow = 0; // what row is being evaluated
        int evalCol = 0; // what column is being evaluated
        if (!this.getPaused()) 
        {
            for(int row=0; row<this.currentGenGrid.length; row++) //loops through rows to find specimens
            { 
                    neighbors=0; // puts neighbor count back at 0 each time it goes through a new row
                    for(int col=0; col<this.currentGenGrid.length; col++) // loops through columns to find specimens
                    {
                        neighbors=0;
                        for(int i=0; i<3; i++)//evaluate neighbors before, during, and after rows around specimen
                        {
                            for(int j=0; j<3; j++)//evaluate neighbors before, during, and after columns around specimen
                            {
                                try 
                                {
                                    evalRow = i+row-1; // current row
                                    evalCol = j+col-1; // current column
                                    if (row==5&&col==4)
                                        neighbors= neighbors;
                                    if (this.getGrid(evalRow, evalCol))
                                    {
                                        if (i==1 && j==1)//never consider current specimen
                                            {
                                                
                                            }
                                        else
                                            neighbors++; // add a neighbor to count if condition is met
                                    }
                                }
                                catch(IndexOutOfBoundsException e)
                                {
                                 //assume array out of bounds and doing nothing
                                }
                            }
                            
                        }
                        if ((this.getGrid(row,col))&&(neighbors<2||neighbors>3)) // don't add specimen in next generation and kill current specimen
                            setGrid(row,col,false);
                        else if ((neighbors==2||neighbors==3)&&(this.getGrid(row,col)))// specimen alive and stays alive in next generation
                            setGrid(row,col,true);
                        else if ((neighbors==3)&&(!this.getGrid(row,col)))// no specimen and new one created under right conditions
                             setGrid(row,col,true);
                        else 
                            setGrid(row,col,false); // no specimen for all other cases in next generation
                    }                          
            }
            //At this point, we have evaluated the current generation and need to reload the world from the new generation:
            this.updateGenerationGrid();
            this.loadSpecimensFromGrid();
           
        }
    }
    
    /**
     * creates current and future generation grid
     * @param gridSize - grid size for current and future generation
     */
    public void createGrid(int gridSize)
    {
        this.currentGenGrid = new boolean[gridSize][gridSize];
        this.futureGenGrid = new boolean[gridSize][gridSize];
    }
    
    public boolean getGrid(int row, int col)
    {
        return this.currentGenGrid[row][col];
    }
    
    /**
    * sets values in next generation grid
    * @param row - horizontal location of empty square or square with specimen 
    * @param column - vertical location of empty square or square with specimen 
    * @param specimenAlive - square with specimen or square without specimen 
    */
    public void setGrid(int row, int col, boolean specimenAlive)
    {
        this.futureGenGrid[row][col] = specimenAlive;
        
    }
    
    //clear world and loads grid and specimens from file
    public void loadSpecimensFromGrid()
    {
        int gridSize = this.currentGenGrid.length; // current size of grid
        //Clear specimens from world
        this.removeObjects(this.getObjects(Specimen.class));
         for(int row=0; row<gridSize; row++)
         {
             for(int col=0; col<gridSize; col++)
             {
                 if (getGrid(row, col))
                 {
                       //create new specimen at this grid location
                       Specimen newSpecimen = new Specimen(this.squareWidth-1, this.squareHeight-1);
                       addObject(newSpecimen, colToX(col), rowToY(row));
                 }
                 
             }
         
         }
    }
    //Overwrite old generation with new
    public void updateGenerationGrid()
    {
        //this.currentGenGrid = this.futureGenGrid;
        int gridSize = this.futureGenGrid.length; // length of future grid
        for(int row=0; row<gridSize; row++)
         {
             for(int col=0; col<gridSize; col++)
             {
                 if (this.futureGenGrid[row][col])
                 {
                       this.currentGenGrid[row][col] = true;
                 }
                 else
                     this.currentGenGrid[row][col] = false;
             }
         
         }
    }
    
    // causes world to be paused, modifies pause button label to go, and sorts rows in grid in descending order with largest number of specimens at top 
    public void sortRows()
    {
        int rowCount = 0; // row being sorted and compared to next row
        int nextRowCount = 0; // next row being sorted and compared to previous row
        int gridSize = this.futureGenGrid.length; // future generation grid size
        boolean wasSwapCalled = true; // tests for swaps rows
        //orders rows in descending order starting at (0,0)
        while (wasSwapCalled)
        {
            wasSwapCalled = false;
            for(int row=0; row+1<gridSize; row++)
            {
                 rowCount=0;
                 nextRowCount=0;
                 for(int col=0; col<gridSize; col++)
                 {
                     if (this.futureGenGrid[row][col])
                     {
                           rowCount++;
                     }
                     if ((this.futureGenGrid[row+1][col]))
                     {
                         nextRowCount++;
                     }
                 }
                 if (nextRowCount>rowCount)
                    {
                        this.swap(row, row+1);  
                        wasSwapCalled = true;
                    }
            }
        }
    }
    
    /**
     * swaps row with more specimens for previous row with less specimens
     * @param row1 - current row
     * @param row2 - following row
     */
    private void swap(int row1 , int row2) 
    {
        boolean temp[]; // grid for future generation
        if(row1 == row2) // don't do anything if both current row and following row are the same
            return;
        temp = this.futureGenGrid[row2];
        this.futureGenGrid[row2] = this.futureGenGrid[row1]; // replace row2 with row1
        this.futureGenGrid[row1] = temp; 

    }

    // sorts columns in grid from lowest to highest starting from the left 
    public void sortColumns()
    {
        int colCount = 0; // column being sorted and compared to next column
        int nextColCount = 0; // next column being sorted and compared to previous column
        int gridSize = this.futureGenGrid.length; // size of future generation
        boolean wasSwapCalled = true; // tests for swaps columns
        
        while (wasSwapCalled)
        {
            wasSwapCalled = false;
            for(int col=0; col+1<gridSize; col++)
            {
                 colCount=0;
                 nextColCount=0;
                 for(int row=0; row<gridSize; row++)
                 {
                     if (this.futureGenGrid[row][col])
                     {
                         colCount++;
                     }
                     if ((this.futureGenGrid[row][col+1]))
                     {
                         nextColCount++;
                     }
                 }
                 if (nextColCount<colCount)
                    {
                        this.swapC(col, col+1, gridSize);  
                        wasSwapCalled = true;
                    }
            }
        }
    }
    /**
     * swaps columns with less specimens than previous column with more specimens 
     * @param col1 - current column
     * @param col2 - following column
     */
    private void swapC(int col1 , int col2, int gridSize) 
    {
        boolean temp;
        if(col1 == col2) 
            return;
        for(int row=0; row<gridSize; row++)
        {
            temp = this.futureGenGrid[row][col1];
            this.futureGenGrid[row][col1] = this.futureGenGrid[row][col2];
            this.futureGenGrid[row][col2] = temp;
        }

    }
}
