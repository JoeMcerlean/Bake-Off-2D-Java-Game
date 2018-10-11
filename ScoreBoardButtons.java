import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import javafx.scene.layout.Background;
import org.jsfml.system.*;
import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;

import java.awt.Desktop;
import java.net.URI;
import java.net.*;


/**
 * This class creates a new button to be utilised on the scoreboard
 * @author Joseph McErlean
 */
public class ScoreBoardButtons extends BOSprite
{
  private RenderWindow parent;
  private Text buttonText;
  private String labelText;
  private Font BOFont;
  private BakeOff bakeOff;
  private BakeOff newGame;
  private int wantedScene;

  /**
   *
   * @param parent The window in which it the image will be placed in.
   * @param labelText The text that will be displayed within the buttons
   * @param bakeOff The bakeOff instance being utilised in order to utilise its methods and variables.
   * @param x The X axis position of where to place the button on the window.
   * @param y The X axis position of where to place the image on the window.
   * @param wantedScene The wanted scene to have the buttons go to.
   */
  public ScoreBoardButtons(RenderWindow parent, String labelText, BakeOff bakeOff, int x ,int y, int wantedScene)
  {

    //Setup BOSprite super class
    super(parent);

    //set parent
    this.parent = parent;

    //set bake off
    this.bakeOff = bakeOff;

    //set label text
    this.labelText = labelText;

    //setup a texture
    this.loadTexture("media/props/button.png");

    //Set start location
    this.setPosition(x,y);

    this.wantedScene = wantedScene;

    //load font
    try
    {
      BOFont = new Font(); BOFont.loadFromFile(Paths.get("media/fonts/bakeofffont.ttf"));
    }
    catch(IOException e){ }

    //Setup text label
    buttonText = new Text(labelText, BOFont, 32);

    //Set label position
    buttonText.setPosition(x+50,y+20);

  }
  @Override
  /**
   * Draws the button onto the window making it visible.
   */
  public void draw()
  {
    parent.draw(this);
    parent.draw(buttonText);
  }
  /**
   * Handles the event occuring on the buttons
   * @param event The click event
   */
  public void eventHandle(Event event)
  {
    //if mouse button is pressed
    if(event.type == Event.Type.MOUSE_BUTTON_PRESSED)
    {
      //if pressed within border of button perform body
      if(this.getGlobalBounds().contains(Mouse.getPosition(parent).x, Mouse.getPosition(parent).y))
      {
        //if restart button is pressed
	if(wantedScene == 1)
	{
	  parent.close();
	  try
	  {
	    bakeOff.stopMusic();
	    bakeOff = new BakeOff();
	  }
	  catch(IOException e)
	  {

	  }
	}
	 //go to leaderboard
	else
	{
        try {

            if(Desktop.isDesktopSupported()){

                Desktop.getDesktop().browse(new URL("https://www.danielmace.co.uk/bakeoff/").toURI());
            } else{

                Runtime runtime = Runtime.getRuntime();
                String[] args = { "osascript", "-e", "open location \"" + "https://www.danielmace.co.uk/bakeoff/" + "\"" };
                Process process = runtime.exec(args);
            }

        } catch (Exception e) {

        }
	}
      }
    }
  }
}
