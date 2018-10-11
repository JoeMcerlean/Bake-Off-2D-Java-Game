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
public class Indicator extends BOSprite{

    private RenderWindow parent;
    int i=1;
    boolean start = false;
    double d =1;

    /**
     * Indicator constructor to create the slider for the multiplier.
     * @param parent
     */
    public Indicator(RenderWindow parent){

        //setup class
        super(parent, "media/props/indicator.png");
        this.parent = parent;
        //set location
      this.setPosition(290, 60);
    }

    /**
     * Method used to stop the slider on the multiplier.
     */
    public void stop(){
      start= false;
    }

    /**
     * Method used to start the slider on the multiplier.
     */
    public void start(){
      start= true;
    }

    /**
     * Method used to return the score multiplier on the slider.
     * @return
     */
    public int getScore(){
      if(i<=134&&i>=80)
        return 3;
      else if((i<=79&&i>=36)||(i<=174&&i>=135) )
       return 2;
      else
        return 1;

    }

    /**
     * Method used to restart the game.
     */
    public void restart() {
      this.setPosition(290, 60);
      i=1;
      d=1.2;
      start =false;
    }

    /**
     * Method used to draw the slider on the multiplier.
     */
    public void draw() {
      if(start && this.getPosition().x < 960) {
        this.setPosition(this.getPosition().x+i, this.getPosition().y);
        d= d*1.3;
        i= (int) Math.round(d);
      } else if(this.getPosition().x >= 960){
        this.setPosition(960,this.getPosition().y);
      }
      parent.draw(this);
    }
}
