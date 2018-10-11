import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.Mouse;
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
public class SelectCharacter extends BOSprite
{
    private String name;
    private RenderWindow parent; 
	private Font BOFont;
	private BakeOff bakeOff; 
	private Text buttonText;

    /**
     *
     * @param parent
     * @param bakeOff
     * @param name
     * @param x
     * @param y
     */
    public SelectCharacter(RenderWindow parent, BakeOff bakeOff,  String name, int x, int y)
    {
        super(parent);
		
		this.bakeOff = bakeOff; 
        this.parent = parent;
        this.name = name;

        this.loadTexture("media/chars/"+name+".png");
        this.setPosition(x, y);
		
		//load font
		try {
			BOFont = new Font(); BOFont.loadFromFile(Paths.get("media/fonts/bakeofffont.ttf")); 
		} catch(IOException e){ }
		
		//Setup text label
		buttonText = new Text(name, BOFont, 28); 
		buttonText.setColor(new Color(48, 48, 74));
		
		//Set label position
		buttonText.setPosition(x+90-(buttonText.getLocalBounds().width/2), y+270);
    }

    /**
     *
     */
    public void draw()
    {
        parent.draw(this);
		parent.draw(buttonText);
    }

    /**
     *
     * @param event
     */
	public void eventHandle(Event event){

        if(event.type == Event.Type.MOUSE_BUTTON_PRESSED)
			if(this.getGlobalBounds().contains(Mouse.getPosition(parent).x, Mouse.getPosition(parent).y)){
				bakeOff.setChar(name);
				bakeOff.setScene(2);
			}
    }
}
