import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color; // gives the ability to name colors for images.

/**
 * A "generic" clickable button. Note that you cannot create any objects of this type. Instead,
 *   you should inherit this class into a new subclass. In the new subclass, make sure *NOT* to
 *   have an act method, and make sure you *DO* have a doButtonWork method (matching the header
 *   below).
 * 
 * @author Natalie Stiegemeier
 * @version 10/15/2015
 */
public abstract class GenericButton extends Actor
{
    // constants 
    /** how long (in number of act calls) a button should stay clicked */
    public static final int CLICK_DELAY=4; 
    /** one more than CLICK_DELAY indicates button is unclicked*/
    public static final int UNCLICKED=CLICK_DELAY+1; 
    
    // instance variables
    private String label;                // actual text found in the button. 
    
    private GreenfootImage unclickedImg; // image to use when buttin is not clicked, 
    private GreenfootImage clickedImg;   // image to use when but has been clicked recently.
    
    private int clickedCountDown;        // keeps track of how long to keep button appearing clicked
    
    /**
     * GenericButton - builds a button with a label of the given text. Note that
     *    you can NOT construct a Generci button because the class is abstract;
     *    classes that inherit this one can call this constructor using super()
     *    from their own constructor(s)
     *    
     *    @param text the label to utilize in the resulting button.
     */
    public GenericButton(String text)
    {
        clickedCountDown=UNCLICKED; // set the button countdown as unclicked 
    
        label=new String(text);     // store text to place into button
    
        buildImages();              // build images for use with this button
    
        setImage(unclickedImg);     // default image is the nlicked version.
    }
    
    /*
     * buildImages - (private) builds the two images needed while the button is act-ing. 
     *   one for an unclicked button, the other for when the button has been clicked.
     */
    private void buildImages()
    {
       // the colors for the button. The unlicked version has gray background with white text,
       Color bgColor = Color.gray;
       Color fgColor = Color.white;
       
       // build the unclicked image
       unclickedImg = buildImage(bgColor, fgColor);
       
       // build the clicked version of the button image. Note that it has the reversed color scheme,
       clickedImg = buildImage(fgColor, bgColor);
    }
    
    /*
     * buldImage - (private) builds a single image with specified colors:
     *    bg - the button background color
     *    fg - the button foreground color
     */
    private GreenfootImage buildImage(Color bg, Color fg)
    {
        // build an image to hold the text label
        GreenfootImage txtImg = new GreenfootImage(label, 24, fg, bg);
        
        //build an image big enough to hold the text and the sourrounding border for the button itself
        GreenfootImage buttonImg = new GreenfootImage(txtImg.getWidth()+10, txtImg.getHeight()+10);
        
        // fill the buttons's image with background color
        buttonImg.setColor(bg);
        buttonImg.fill();
        
        // add the text to the button image
        buttonImg.drawImage(txtImg, 5,5);
        
        // draw a rectangle around the text
        buttonImg.setColor(fg);
        buttonImg.drawRect(1,1,buttonImg.getWidth()-3, buttonImg.getHeight()-3);
        
        // return "finished product" image.
        return buttonImg;
    }
    
    /**
     * Act - when the mouse is clicked on this button, makes the button image change and
     *    calls doButtonWork(), which is where the "action" for the button should be
     */
    public void act() 
    {
        // if the button has been clicked for long enough, reset it
        if (clickedCountDown<=0)
        {
            
            setImage(unclickedImg);      // reset means changing image to unclicked version and ... 
            clickedCountDown=UNCLICKED;  // ...storing that it is now UNCLICKE
        }
        // if button was just clicked ...
        else if (clickedCountDown==CLICK_DELAY)
        {
            doButtonWork();     // do work for the button and ...
            clickedCountDown--; // start countdown to resetting button
        }
        // if button was recently clicked, but not yet ready to be reset ...
        else if (clickedCountDown<CLICK_DELAY)
        {
            clickedCountDown--; // continue countdown to resetting btton
        }
        
        // if someone clicks this button ...
        if (Greenfoot.mouseClicked(this))
        {
            clickedCountDown=CLICK_DELAY; // make sure next pass starts doing the work and ...
            setImage(clickedImg);         // rememebr button has been clicked,
        }
    } 
    
    /**
     * setLabel - changes the label text. Also redraws button and moves it to keep it left aligned.
     * 
     * @param newText - the new text to be plaed in the button.
     */
    public void setLabel(String newText)
    {
        label = new String(newText);  // store the new text.
        
        // need the old width to figure out how far to move the button for left alignment
        int oldWidth = unclickedImg.getWidth();
        
        buildImages(); // update bith images for button
        
        // figure out if changing text in middle of button click...
        boolean clicked=false;        
        if (clickedCountDown<=CLICK_DELAY)
            clicked=true;
        
        // if the button was clicked ... 
        if (clicked)
            setImage(clickedImg);   // ... use clicked image version  
        else // button is not in "clicked state, so ...
            setImage(unclickedImg); // ... use unclicked version of button
        
        
        // calculate difference in size between old and new button images ...
        int newWidth = unclickedImg.getWidth(); 
        int deltaWidth = newWidth-oldWidth;
        
        // move the newly imaged button according to its new size. 
        //setLocation(getX()+deltaWidth/2, getY());
    }
    
    /**
     * getLabel - returns what text is currently in the button
     *   @return a String containing the button's text.
     */
    public String getLabel()
    {
        return label;
    }
    
    /**
     * doButtonWork - code to do when this button is pressed. YOU MUST OVERRIDE THIS METHOD IN
     *   YOUR SUBCLASS!
     */
    public void doButtonWork()
    {
        System.out.println("You clicked a generic button. Perhaps you forgot an overload?");
    }
}

