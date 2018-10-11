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
public class InGameButton extends BOSprite{

    private RenderWindow parent;
	private Text buttonText;
	private String labelText;
	private Font BOFont;
	private BakeOff bakeOff;

	/**
	 *
	 * @param parent
	 * @param labelText
	 * @param bakeOff
	 * @param x
	 * @param y
	 */
    public InGameButton(RenderWindow parent, String labelText, BakeOff bakeOff, int x, int y) {

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
        this.setPosition(x, y);

		//load font
		try {
			BOFont = new Font(); BOFont.loadFromFile(Paths.get("media/fonts/bakeofffont.ttf"));
		} catch(IOException e){ }

		//Setup text label
		buttonText = new Text(labelText, BOFont, 32);

		//Set label position
		buttonText.setPosition(x + buttonText.getLocalBounds().width/2, y+ this.getTexture().getSize().y/2 - buttonText.getLocalBounds().height);
    }

	/**
	 *
	 * @return
	 */
	public String getButtonText(){

        return buttonText.getString();
    }

	/**
	 *
	 * @param event
	 */
	public void eventHandle(Event event){

        if(event.type == Event.Type.MOUSE_BUTTON_PRESSED)
			if(this.getGlobalBounds().contains(Mouse.getPosition(parent).x, Mouse.getPosition(parent).y))
				bakeOff.setScene(1);
    }

    /*
        Draw the ingredient on the window...
    */

	/**
	 *
	 */
	@Override
    public void draw() {

		parent.draw(this);
		parent.draw(buttonText);
    }
}
