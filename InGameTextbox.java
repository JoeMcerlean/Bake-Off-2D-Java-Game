import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import javafx.scene.layout.Background;
import org.jsfml.system.*;
import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;

/**
 * Created by Joseph McErlean
 */
public class InGameTextbox extends BOSprite{

    private RenderWindow parent;
	private Text buttonText;
	private String labelText;
	private Font BOFont;
	private BakeOff bakeOff;
    private BOSprite bar;
    private Clock clock; // clock to get current time
    public int timeElapsed; // time elapsed
    private int pt = 0; // past time

    /**
     * Text box used to enter player name.
     * @param parent Takes parent as a RenderWindow parameter.
     * @param labelText Takes labelText as a String parameter.
     * @param bakeOff Takes instance of BakeOff as a parameter.
     * @param x Takes integer x as a parameter.
     * @param y Takes integer y as a parameter.
     */
    public InGameTextbox(RenderWindow parent, String labelText, BakeOff bakeOff, int x, int y) {

        //Setup BOSprite super class
        super(parent);

		//set parent
		this.parent = parent;

		//set bake off
		this.bakeOff = bakeOff;

		//set label text
		this.labelText = labelText;

		//setup a texture
		this.loadTexture("media/props/textbox.png");

        //Set start location
        this.setPosition(x, y);

		//load font
		try {
			BOFont = new Font(); BOFont.loadFromFile(Paths.get("media/fonts/bakeofffont.ttf"));
		} catch(IOException e){ }

		//Setup text label
		buttonText = new Text(labelText, BOFont, 32);
        buttonText.setColor(new Color(0,0,0));

		//Set label position
		buttonText.setPosition(x+15, y+ this.getTexture().getSize().y/2 - buttonText.getLocalBounds().height);

        // Setup bar
        bar = new BOSprite(parent, "media/props/bar-black.png");
        bar.setPosition(x+this.getTexture().getSize().x - 20, y+24);

        // Setup clock
        clock = new Clock();

        // Set player name
        bakeOff.playerName = buttonText.getString(); 
    }

    /**
     * Checks for event of text being entered.
     * @param event Checks for text being entered.
     */
    public void eventHandle(Event event){

        if(event.type == Event.Type.TEXT_ENTERED)
            if(event.asTextEvent().unicode == 8){
                if(buttonText.getString().length() > 0)
                    buttonText.setString(buttonText.getString().substring(0, buttonText.getString().length()-1));
            } else {
                if(buttonText.getString().length() < 16)
                    buttonText.setString(buttonText.getString() + event.asTextEvent().character);
            }

        bakeOff.playerName = buttonText.getString(); 
    }

    /*
        Draw the textbox on the window...
    */

    /**
     * Draws the text bar.
     */
    @Override
    public void draw() {

        // Set bar position
        bar.setPosition(this.getPosition().x + 32 + buttonText.getLocalBounds().width, bar.getPosition().y);

        // Timer ticks
        timeElapsed = (int)clock.getElapsedTime().asSeconds();

        // Check ticks
        if(timeElapsed%2 == 0)
            bar.loadTexture("media/props/bar-white.png");
        else
            bar.loadTexture("media/props/bar-black.png");

        // Set PT
        pt = timeElapsed;

		parent.draw(this);
		parent.draw(buttonText);
        bar.draw();
    }
}
