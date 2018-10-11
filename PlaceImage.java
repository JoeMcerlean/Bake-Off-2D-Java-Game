import org.jsfml.system.*;
import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;

/*
  Created By Joseph McErlean
*/

/**
 * This class is utilised in order to place static images on window.
 * @author NathanGrimble
 */
public class PlaceImage extends BOSprite
{
  private String path;
  private RenderWindow parent;
  
  /**
   * Creates and places a new image with the given characteristics
   * @param parent The window in which it the image will be placed in.
   * @param path The route to where the image is saved within the files.
   * @param xPos The X axis position of where to place the image on the window.
   * @param yPos The X axis position of where to place the image on the window.
   */
  public PlaceImage(RenderWindow parent, String path, int xPos, int yPos)
  {
    super(parent);
    
    this.path = path;
    this.parent = parent;
    this.loadTexture(path);
    this.setPosition(xPos, yPos);
  }
  
  /**
   * Draws the image onto the window making it visible.
   */
  public void draw()
  {
    parent.draw(this);
  }
}