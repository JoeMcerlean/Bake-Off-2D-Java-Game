import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.function.*;

import javafx.scene.layout.Background;
import org.jsfml.system.*;
import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;

/**
 * Created by Joseph McErlean
 */
public class BOSprite extends Sprite {

    private RenderWindow parent;
    private Texture texture = new Texture();

    /**
     *
     * @param parent
     */
    public BOSprite(RenderWindow parent) {

        //Initialize
        this.parent = parent;
    }

    /**
     *
     * @param parent
     * @param imagePath
     */
    public BOSprite(RenderWindow parent, String imagePath) {

        //Initialize
        this.parent = parent;

        //setup texture
        this.loadTexture(imagePath);
    }
	
	//get texture

    /**
     *
     * @return
     */
	public Texture getTexture(){
		
		return texture; 
	}

    //load texture

    /**
     *
     * @param path
     */
    public void loadTexture(String path){

        try{

            this.texture.loadFromFile(Paths.get(path));
            this.setTexture(texture);
        } catch (IOException e){

            //Print the stack trace
            System.out.println(e.getStackTrace());
            System.out.println("Error when loading the texture: " + path);
        }
    }

    //draw

    /**
     *
     */
    public void draw(){

        //Draw the Sprite on the renderWindow
        parent.draw(this);
    }

    //event handle

    /**
     *
     * @param event
     */
    public void eventHandle(Event event){ }
}
