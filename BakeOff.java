import org.jsfml.system.*;
import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;
import java.util.*;
import java.nio.*;
import java.nio.file.*;
import java.io.*;
import java.util.Random;
import java.net.*;
import java.nio.charset.*;
import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;

/**
 * Main game class producing the objects and rendering.
 */
public class BakeOff {

    private ArrayList scenes = new ArrayList(); // ArrayList to store each scene. Each item is nested arraylist that contains objects that extend BOSprite.
    private int sceneID = 0, level = 1; // Scene ID (for collection of objects) and Level ID (for speeds etc. )
    private Sprite scene1BG, scene1FG; // common BG + FG sprites
    private Texture s1bg, s1fg; // common BG + FG textures
    private Text timer; // timer to countdown 30s
    private Clock clock; // clock to get current time
    public int timeElapsed, timeLimit = 120; // elapsed and time limit for 80s countdown
    private Font BOFont = new Font(); // custom BakeOff font
    private Score score; // common score - also provides sounds when score increased.
	private String charName = ""; // name of current character -- will be used to load character image so must match .PNG
	private BOSprite charSprite; // common character sprite -- will be rendered on each game scene (2, 3)
    private RenderWindow window; // window to present game
	private int ingredientCount;
	private int fullIngredientCount;
    public String playerName = "";

	private SoundBuffer soundBuffer = new SoundBuffer();
	private Sound sound = new Sound();
	private Sound welcome;

    /**
     * Main game constructor producing the objects and rendering
     * @throws IOException
     */
    public BakeOff() throws IOException {

        //Run setup -- deals with window, fonts, clocks, general admin...
        setup();

        // Objects for scene 0: Start Screen
		ArrayList sceneryobjects0 = new ArrayList();
		sceneryobjects0.add(new InGameButton(window, "Start Game", this, 665, 230));
		sceneryobjects0.add(new InGameTextbox(window, "Your Name", this, 325, 230));

	    // Objects for scene 1: Character Select
		ArrayList sceneryobjects1 = new ArrayList();
		sceneryobjects1.add(new SelectCharacter(window, this, "andrew", 170, 230));
		sceneryobjects1.add(new SelectCharacter(window, this, "val", 360, 230));
		sceneryobjects1.add(new SelectCharacter(window, this, "shrek", 550, 230));
		sceneryobjects1.add(new SelectCharacter(window, this, "ian", 740, 230));
		sceneryobjects1.add(new SelectCharacter(window, this, "selasi", 930, 230));

        // Objects for scene 2: Conveyor Game
        ArrayList sceneryobjects2 = new ArrayList();
        sceneryobjects2.add(charSprite);
        sceneryobjects2.add(score);

        // Open and randomize recipe
        Recipe rec = new Recipe(score, window, sceneryobjects2);

		sceneryobjects2.add(rec);

        // Objects for scene 3: Oven Minigame
        ArrayList sceneryobjects3 = new ArrayList();
        Indicator indicator = new Indicator(window);
        sceneryobjects3.add(indicator);
        OvenButton ovenButton = new OvenButton(window,indicator,score, this);
        sceneryobjects3.add(ovenButton);

        // Objects for scene 4: Score Screen
        ArrayList sceneryobjects4 = new ArrayList();
        ScoreBoard sb = new ScoreBoard(window, score, this); 
		ScoreBoardButtons rButton = new ScoreBoardButtons(window,"Restart Game", this, 320,600,1);
		ScoreBoardButtons lButton = new ScoreBoardButtons(window,"LeaderBoard", this, 665,600,5);
        sceneryobjects4.add(sb);
		sceneryobjects4.add(rButton);
		sceneryobjects4.add(lButton);

        // Add all sceneryobjects to the scenes arraylist
        scenes.add(sceneryobjects0);
        scenes.add(sceneryobjects1);
        scenes.add(sceneryobjects2);
        scenes.add(sceneryobjects3);
        scenes.add(sceneryobjects4);

        // Main loop
        while(window.isOpen()) {

            ArrayList obj = (ArrayList) scenes.get(this.sceneID); // Load objects
            rec.lap(timeElapsed); // Recipe render
            window.clear(Color.BLACK); // Clear            
            window.draw(scene1BG); // Draw Background

            // Draw every BOSprite
            for (int i = 0; i < obj.size(); i++)
                ((BOSprite) obj.get(i)).draw();

            window.draw(scene1FG); // Draw the foreground
            timerTick(); // Timer ticks
            window.display(); // Display window

            // For each event in game window...
            for(Event event : window.pollEvents()) {

                // Loop through and handle events on all ingredients
                for (int i = 0; i < obj.size(); i++)
                    ((BOSprite) obj.get(i)).eventHandle(event);

                // Window close events (Close button or ESC key...)
                if (event.type == Event.Type.CLOSED || event.type == Event.Type.KEY_PRESSED && event.asKeyEvent().key == Keyboard.Key.ESCAPE)
                    window.close();
            }
        }
    }

    /*
        Timer tick
    */

    /**
     * Timer used to control the decrementing time and to set scene to baking after the timer reaches zero.
     */
    public void timerTick(){

        // Update only on scene 2
        if(getSceneID() == 2){

            // Update time elapsed 
            timeElapsed = timeLimit - (int)clock.getElapsedTime().asSeconds();

            // Draw timer on screen [TOP RIGHT]
            timer.setString(String.valueOf(timeElapsed));
            timer.draw(window, RenderStates.DEFAULT.DEFAULT);
            timer.setPosition(1242, 5);
 
            // Check IF 0S LEFT 
            if(timeElapsed == 0)
                setScene(3);  // CHANGE TO OVEN SCREEN
        }
    }


    /**
     * Setup the game.
     * @throws IOException
     */
    public void setup() throws IOException{

        // Create the window
        window = new RenderWindow(new VideoMode(1280, 720), "Bake Off", WindowStyle.DEFAULT);
        window.setFramerateLimit(30);

		// Setup the character sprite -- use val as placeholder for sizes (180 x 260)
		charSprite = new BOSprite(window, "media/chars/val.png");
		charSprite.setPosition(0, 720-260);

        // Get background layers from scene
        scene1BG = new Sprite(); s1bg = new Texture(); s1bg.loadFromFile(Paths.get("media/scenes/scene-"+sceneID+"-bg.png")); scene1BG.setTexture(s1bg);
        scene1FG = new Sprite(); s1fg = new Texture(); s1fg.loadFromFile(Paths.get("media/scenes/scene-"+sceneID+"-fg.png")); scene1FG.setTexture(s1fg);

        // Load bake off font
        BOFont.loadFromFile(Paths.get("media/fonts/bakeofffont.ttf"));

        // Setup Timer text
        timer = new Text(String.valueOf(timeLimit), BOFont, 16);
        timer.setColor(new Color(255, 255, 255));

		// Setup sound
		soundBuffer = new SoundBuffer();
		soundBuffer.loadFromFile(Paths.get("media/sound/soundtrack.wav"));

        // Setup Score
        score = new Score(window);

		//Create a sound and set its buffer
		sound.setBuffer(soundBuffer);

        //intro music initiated - NG
        soundBuffer = new SoundBuffer();
        soundBuffer.loadFromFile(Paths.get("media/sound/Welcome.wav"));
        welcome = new Sound();
        welcome.setBuffer(soundBuffer);
        welcome.play();
    }

    /*
        Set the scene from int #ID c
        Loads BG, FG from files in media/scenes/ directory
        BG, FG File format: media/scenes/scene-n-[fg, bg].png
    */

    /**
     * Method used to set the scene that takes an integer as a parameter.
     * @param c
     */
    public void setScene(int c){

        // SET SCENE ID 
        sceneID = c; 

        // CHECK FOR SCENE 5: PUBLISH SCORE
        if(c == 5)
            score.publishScore(playerName); 

        // CHECK FOR SCENE 2: PLAY BAKE OFF THEME & SETUP CLOCK
		if(c == 2){
			sound.play();
            clock = new Clock();
        }

        // LOAD FG + BG FROM MEDIA / SCENES FOLDER
        if(c < scenes.size() && c >= 0){

            try{
                s1bg.loadFromFile(Paths.get("media/scenes/scene-"+sceneID+"-bg.png")); scene1BG.setTexture(s1bg);
                s1fg.loadFromFile(Paths.get("media/scenes/scene-"+sceneID+"-fg.png")); scene1FG.setTexture(s1fg);
            } catch(IOException e){ }
        }
    }

	/*
		Set the char name
	*/

    /**
     * Method used to set the character image to be used in the game.
     * @param charName
     */
	public void setChar(String charName){

		this.charName = charName;
		charSprite.loadTexture("media/chars/"+charName+".png");
	}

    /**
     * Method to stop the music in the game.
     */
	public void stopMusic()
	{
	  sound.stop();
	}

    /*
        Returns the current Scene ID
    */

    /**
     * Method used to return the sceneID value.
     * @return
     */
    public int getSceneID(){

        return sceneID;
    }
}
