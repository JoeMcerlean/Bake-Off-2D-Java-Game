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
 * Created by Joseph McErlean
 */
public class Score extends BOSprite {

    private int count = 0;
    private Text score;
    private Text finalScore;
    private SoundBuffer soundBuffer = new SoundBuffer();
    private SoundBuffer soundBufferDec = new SoundBuffer();
    private Sound sound = new Sound();
    private Sound sounddec = new Sound();
    private Font font = new Font();
    private RenderWindow parent;

    /**
     *
     * @param parent
     * @throws IOException
     */
    public Score(RenderWindow parent) throws IOException {

        // Super
        super(parent); 

        // Set parent
        this.parent = parent;

        // Load a sound and set buffer
        soundBuffer.loadFromFile(Paths.get("media/sound/pickup.wav"));
        sound.setBuffer(soundBuffer);

        // Load a sound and set buffer
        soundBufferDec.loadFromFile(Paths.get("media/sound/score_down.wav"));
        sounddec.setBuffer(soundBufferDec);

        // Get score font
        font.loadFromFile(Paths.get("media/fonts/bakeofffont.ttf"));

        // Set score properties 
        score = new Text("Score: "+count, font, 16);
        score.setColor(new Color(255, 255, 255));
        score.setPosition(10,5);
    }

    /**
     *
     */
    public void draw(){

        // Set score text
        score.setString("Score: " + count);

        // Render on window
        score.draw(parent, RenderStates.DEFAULT);
    }

    /**
     *
     */
    public void inc(){

        inc(1);
    }

    /**
     *
     * @param num
     */
    public void inc(int num){

        //PLAY SOUND
        sound.play();

        count = count + num;
    }

    /**
     *
     */
    public void dec(){

        //PLAY SOUND
        sounddec.play();

        count--;
    }

    /**
     *
     * @return
     */
    public Text getScore(){

        return score;
    }

    /**
     *
     * @return
     */
    public Text getFinalScore(){

        return finalScore;
    }

    /**
     *
     */
    public void displayFinalScore(){

        // SCORE TEXT
        finalScore = new Text(Integer.toString(count), font, 100);
        finalScore.setColor(new Color(0, 0, 0));
        finalScore.setPosition(625,325);
    }

    /**
     *
     * @return
     */
    public int getCount(){

        return count;
	}

    /**
     *
     * @return
     */
    public int getFinalCount()
    {
     return count;
    }

    /**
     *
     * @param num
     */
    public void setScore(int num) {
        sound.play();
        count = num;
    }

    /**
     *
     * @param playerName
     */
    public void publishScore(String playerName){

        try{

            URLConnection connection = new URL("http://www.danielmace.co.uk/bakeoff/index.php?NAME="+playerName.replace(" ", "%20")+"&SCORE=" + getCount()).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            connection.connect();

            BufferedReader r  = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
            sb.append(line);
        }

        } catch(Exception e){ e.printStackTrace(); }
    }

    /**
     * Applies bonus score upon completion of a recipe.
     */
    public void setBonusScore() {
        count = count+50;
    }
}
