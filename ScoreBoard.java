import org.jsfml.system.*;
import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;
import java.nio.file.*;
import java.io.*;

/*
  Created by Nathan Grimble
*/

/**
 * This class is utilised in order to set up the scoreboard and its objects.
 * @author Joseph McErlean
 */
public class ScoreBoard extends BOSprite {

	private Text header;
	private Text scoreText;
	private RenderWindow parent;
	private Font bOFont = new Font();
	private String path;
	private Score score;
	private BakeOff bakeOff;

	private BOSprite mary;
	private BOSprite paul;

	private BOSprite s1;
	private BOSprite s2; 

	/**
	* 
	* @param parent The window in which it the image will be placed in.
	* @param score The score class instance being utilised in order to utilise its methods and variables.
	* @param bakeOff The bakeOff instance being utilised in order to utilise its methods and variables.
	*/
	public ScoreBoard(RenderWindow parent, Score score, BakeOff bakeOff) throws IOException {
		
		// SUPER
		super(parent);

		// INITIALISE 
		this.parent = parent;
		this.score = score;
		this.bakeOff = bakeOff;

		// LOAD BAKE OFF FONT
		bOFont.loadFromFile(Paths.get("media/fonts/bakeofffont.ttf"));

		// SETUP PAUL AND MARY IMAGES
		mary = new BOSprite(parent, "media/chars/merry.png"); /*, 820, 170); */
		mary.setPosition(820, 170); 
		paul = new BOSprite(parent, "media/chars/paul.png"); /*, 160, 140); */
		paul.setPosition(160, 140);

		// SETUP HEADER
		header = new Text("FINALSCORE", bOFont, 75);
		header.setColor(new Color(208, 194, 169));
		header.setPosition(450,0);

		// SETUP SCORE TEXT
		scoreText = new Text("SCORE: "+score.getFinalCount(),bOFont,45);
		scoreText.setColor(new Color(62, 62, 94));
		scoreText.setPosition(540,320);

		// SETUP SPEECH BUBBLES
		s1 = new BOSprite(parent, "media/props/badSMerry.png"); 
		s1.setPosition(430, 450);
		s2 = new BOSprite(parent, "media/props/badSPaul.png"); 
		s2.setPosition(430, 170);
	}

	/*
		DRAW THE BOSPRITES ON THE SCENE
	*/
	public void draw(){

		mary.draw();
		paul.draw();

		// LOAD RELEVANT SCORE IMAGES
		if(score.getFinalCount() <= 10) {

			// BAD SCORE
			s1.loadTexture("media/props/badSMerry.png");
			s2.loadTexture("media/props/badSPaul.png");

		} else if(score.getFinalCount() <= 20 && score.getFinalCount() > 10) {

			// LOW SCORE
			s1.loadTexture("media/props/lowSMerry.png");
			s2.loadTexture("media/props/lowSPaul.png");

		} else if(score.getFinalCount() <= 30 && score.getFinalCount() > 20) {

			// MED SCORE
			s1.loadTexture("media/props/medSMerry.png");
			s2.loadTexture("media/props/medSPaul.png");

		} else if(score.getFinalCount() <= 40 && score.getFinalCount() > 30) {

			// GOOD SCORE
			s1.loadTexture("media/props/goodSMerry.png");
			s2.loadTexture("media/props/goodSPaul.png");

		} else if(score.getFinalCount() > 40) {

			// GREAT SCORE
			s1.loadTexture("media/props/greatSMerry.png");
			s2.loadTexture("media/props/greatSPaul.png");
		}

		s1.draw();
		s2.draw();

		header.draw(parent, RenderStates.DEFAULT);
		scoreText.setString("SCORE: "+score.getFinalCount());
		scoreText.draw(parent, RenderStates.DEFAULT);
	}
}
