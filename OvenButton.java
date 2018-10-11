import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import javafx.scene.layout.Background;
import org.jsfml.system.*;
import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;
import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;

/**
 * Created by Joseph McErlean
 */
public class OvenButton extends BOSprite{

  private RenderWindow parent;
	private Text buttonText;
	private Font BOFont;
  private int phase = 0;
  private Score score;
  private boolean done = false;
  private Indicator indicator;
  private SoundBuffer soundBuffer;
  private Sound sound;
  private Text multiplier;
  private BakeOff bake; 
    public OvenButton(RenderWindow parent,Indicator indicator, Score score, BakeOff bake) {

        //Setup BOSprite super class
        super(parent);

		//set parent
    this.bake = bake; 
		this.parent = parent;
    this.indicator = indicator;
    this.score = score;

    soundBuffer = new SoundBuffer();
		try {
			soundBuffer.loadFromFile(Paths.get("media/sound/score.wav"));
		} catch(IOException ex) {
		//Something went wrong
			System.err.println("Failed to load the sound:");
			ex.printStackTrace();
		}

		//Create a sound and set its buffer
		sound = new Sound();
		sound.setBuffer(soundBuffer);

		//setup a texture
		this.loadTexture("media/props/button.png");

        //Set start location
        this.setPosition(1000-(this.getTexture().getSize().x/2), 900/2-(this.getTexture().getSize().y/2));

		//load font
		try {
			BOFont = new Font(); BOFont.loadFromFile(Paths.get("media/fonts/bakeofffont.ttf"));
		} catch(IOException e){ }

		//Setup text label
		buttonText = new Text("Start", BOFont, 32);
    multiplier = new Text("", BOFont, 60);

		//Set label position
		buttonText.setPosition(1000-(buttonText.getLocalBounds().width/2), 900/2-(buttonText.getLocalBounds().height/2)-12);
    multiplier.setPosition(890,300);
    multiplier.setColor(new Color(0,0,0));
    }

    /**
     *
     * @return
     */
    public boolean isDone(){
      return done;
    }

    /**
     *
     */
    public void restart () {
      phase=0;
      buttonText.setString("Start");
      indicator.restart();
      done = false;

      this.setPosition(1080-(this.getTexture().getSize().x/2), 950/2-(this.getTexture().getSize().y/2));
  		buttonText.setPosition(1080-(buttonText.getLocalBounds().width/2), 950/2-(buttonText.getLocalBounds().height/2)-12);
    }

    /**
     *
     * @param event
     */
    public void eventHandle(Event event){

      if(event.type == Event.Type.MOUSE_BUTTON_PRESSED && !done){
			     if(this.getGlobalBounds().contains(Mouse.getPosition(parent).x, Mouse.getPosition(parent).y)){
             if (phase == 0) {
               buttonText.setString("Stop");
               indicator.start();
               phase = 1;
             }
             else if (phase == 1) {
               buttonText.setString("Score");
               indicator.stop();
               score.setScore(score.getCount()*indicator.getScore());
               multiplier.setString(indicator.getScore()+"x multiplier");
               phase = 2;
             }
             else if(phase == 2){
              this.setPosition(-100,-100);
              buttonText.setPosition(-100,-100);
              sound.play();
              bake.setScene(4);
             }

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

		parent.draw(this);
		parent.draw(buttonText);
        parent.draw(multiplier);
    }
}
