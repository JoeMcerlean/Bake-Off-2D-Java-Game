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
public class Bar extends BOSprite{

    private RenderWindow parent;

    /**
     * Draws the current text position bar.
     * @param parent
     */
    public Bar(RenderWindow parent){

        //setup class
        super(parent, "media/ing/bar.jpg");
        this.parent = parent;
        //set location
        this.setPosition(640-this.getTexture().getSize().x/2, 100-this.getTexture().getSize().y/2);
    }
}
