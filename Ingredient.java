import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.function.*;
import java.io.*;

import javafx.scene.layout.Background;
import org.jsfml.system.*;
import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;

/**
 * Created by Joseph McErlean
 */
public class Ingredient extends BOSprite{

    private String name;
    private RenderWindow parent;
    private boolean conveyor = true;
    private boolean dropped = false;
    private boolean picked = false;
    private int conveyorSpeed = 3;
    private Score score;
    private Recipe rec; 
	public ArrayList<String> checkList = new ArrayList<String>();
	private int ingCounter = 0;
    private int speedMod = 1;

    /**
     *
     * @param rec
     * @param parent
     * @param score
     * @param name
     * @param offset
     * @param speedMod
     */
    public Ingredient(Recipe rec, RenderWindow parent, Score score, String name, int offset, int speedMod) {

        //Setup BOSprite super class
        super(parent);

        //Setup Object
        this.name = name;
        this.parent = parent;
        this.score = score;
        this.rec = rec;
        this.speedMod = speedMod;

        //Apply Texture
        this.loadTexture("media/ing/"+name+".png");

        //Set start location
        this.setPosition(1280+offset, 503);
    }

    /**
     *
     * @param parent
     */
    public Ingredient(RenderWindow parent){
        super(parent);
    }


    /**
     *
     */
    public void mousePick(){

        conveyor = false;
        picked = true;
    }

    //MouseDrop

    /**
     *
     */
    public void mouseDrop(){

        //GET MOUSE POSITION
        int mX = Mouse.getPosition(parent).x;
        int mY = Mouse.getPosition(parent).y;
		
		Ingredient ing = this;

        //CHECK IF MOUSE IS IN DROP AREA
        if(167 < mX && mX < 313 && 473 < mY && mY < 567){
            if(rec.requires(name, ing)){
				checkList.add(name);
                //rec.checkOffList(name, ing);
				ingCounter++;
				dropped = true;
				score.inc();
				this.setPosition(1344, 784);
			
            } else{

                dropped = true;
                score.dec();
                this.setPosition(1344, 784);
            }
        }
        else
            conveyor = true;

        //reset origin (set position to counter)
        this.setPosition(this.getPosition().x - this.getOrigin().x, this.getPosition().y - this.getOrigin().y);
        this.setOrigin(0,0);

        //set picked to false -- mouse not grabbing...
        picked = false;
    }

    /**
     *
     * @param event
     */
    public void eventHandle(Event event){

        //Handle own events

            if(event.type == Event.Type.MOUSE_BUTTON_PRESSED){

                //Loop through ing
                Ingredient i = this;

                //MOUSE X ANDY
                int mX = Mouse.getPosition(parent).x;
                int mY = Mouse.getPosition(parent).y;

                //TOP LEFT, TOP RIGHT
                float x1 = i.getPosition().x;
                float x2 = i.getTexture().getSize().x + i.getPosition().x;

                //BOTTOM LEFT AND BOTTOM RIGHT
                float y1 = i.getPosition().y;
                float y2 = i.getTexture().getSize().y + i.getPosition().y;

                //CHECK IF MOUSE IS IN RANGE OF INGREDIENT
                if((x1 < mX && mX < x2) && (y1 < mY && mY < y2)){

                    //Find and set new origin...
                    i.setOrigin(mX - this.getPosition().x, mY - this.getPosition().y);

                    //Set to Picked
                    i.mousePick();
                }
            } else if(event.type == Event.Type.MOUSE_BUTTON_RELEASED){

                //set mouse x and y
                int mX = Mouse.getPosition(parent).x;
                int mY = Mouse.getPosition(parent).y;

                //Loop through ing
                Ingredient i = this;

                if(i.getGlobalBounds().contains(mX, mY)){

                    i.mouseDrop();
                }
            }
    }

    /*
        Draw the ingredient on the window...
    */

    /**
     *
     */
    @Override
    public void draw() {

        if(picked)
            this.setPosition(Mouse.getPosition(parent).x, Mouse.getPosition(parent).y);
        else
            if(conveyor)
                if(this.getPosition().x > 704) {
                    if(this.getPosition().y < 501 || this.getPosition().y > 520)
                        this.setPosition(this.getPosition().x, this.getPosition().y+4);
                    else
                        this.setPosition(this.getPosition().x - conveyorSpeed*speedMod, this.getPosition().y);
                }
                else if(this.getPosition().y < 800){
                    this.setPosition(this.getPosition().x, this.getPosition().y+2*4);
                    this.rotate(-0.25f);
                }
                else
                    conveyor = false;

        //Draw
        if(!dropped)
            parent.draw(this);
    }

    /**
     *
     * @return
     */
	public String getName() {
	
		return name;
	}
}
