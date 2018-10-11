import org.jsfml.system.*;
import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;
import java.util.*;
import java.nio.*;
import java.nio.file.*;
import java.io.*;
import java.util.Random;
import java.nio.charset.*;

/**
 * Created by Joseph McErlean
 */
public class Recipe extends BOSprite{

	private String name = "";
	private ArrayList<String> ingredients = new ArrayList<String>();
	private ArrayList<String> ingredientList = new ArrayList<String>();
	private ArrayList<String> recipeList = new ArrayList<String>();
	private ArrayList sceneObjects; 
	private RenderWindow parent; 
	private Score score; 
	private boolean first = true;
	private int prev = 0; 
    private Text ingsList;
    private Text recList;
    private Font font = new Font();
	private int ingCount = 1;
	private int fullIngCount = 0;
	private int speedMod = 0;
	private String[] checkList = new String[8];
	
	public Ingredient check;
	public ArrayList<String> checkL = new ArrayList<String>();

	/**
	 *
	 * @param score
	 * @param parent
	 * @param sceneObjects
	 */
	public Recipe(Score score, RenderWindow parent, ArrayList sceneObjects){

		super(parent);

		// Load recipe
		newRecipe();

		// Set score
		this.score = score;

		// Set Parent
		this.parent = parent; 

		// Set objects
		this.sceneObjects = sceneObjects;

		//Load from file
		try{

			//loadFromFile();
			font = new Font(); font.loadFromFile(Paths.get("media/fonts/whitechocolatemint.ttf"));
		} catch(IOException e){

			e.printStackTrace();
		}
	}

	/**
	 *
	 * @param elapsed
	 */
	public void lap(int elapsed){

		Random rand = new Random();

		if(elapsed != prev){

			for(int i = 0; i < speedMod; i++){

				// add an ingredient
				Ingredient ing = new Ingredient(this, parent, score, "" + (rand.nextInt(24) + 1), i*64, speedMod);
				sceneObjects.add(ing);
			}

			prev = elapsed; 
		}
	}

	/*
		Requires
	*/

	/**
	 *
	 * @param id
	 * @param i
	 * @return
	 */
	public boolean requires(String id, Ingredient i){
		
		if (ingredients.contains(id) && !checkL.contains(id)){
			checkL.add(i.getName());
			fullIngCount++;
			return true;
		}

		return false;
	}


	/*public void checkOffList(String id, Ingredient i){

		if (ingredients.contains(id) && checkL.contains(id)){

			for(int k=0; k<8; k++) {
				System.out.println("test1");
				if (ingredientList.get(k) == i.getName()){
					checkList[k] = "x\n";
					System.out.println("test");
				}

				//checkList[i] = "x\n";
			}

			//
			for(int j = 0; j < 8; j++){
				recList = new Text("" + checkList[j], font, 24);
				recList.setColor(new Color(255, 150, 150));
				recList.setPosition(780,210);
			}
		}
	}
	*/


	/*
		Load the recipe from a file
	*/

	/**
	 *
	 * @throws IOException
	 */
	public void loadFromFile() throws IOException{

		//Load the recipe num file
		BufferedReader fileIn = new BufferedReader(new FileReader("recipes/"+name+".txt"));

		//Loop through file
		String line = ""; 
		while((line = fileIn.readLine()) != null){

			ingredients.add(line);
		}

		//Close file
		fileIn.close();
		
		//Load the recipe num file
		fileIn = new BufferedReader(new FileReader("recipe/"+name+".txt"));
		
		recipeList.add(name);
		
		//Loop through file
		line = ""; 
		while((line = fileIn.readLine()) != null){

			ingredientList.add(line);
		}

		//Close file
		fileIn.close();
	}

	//new recipe

	/**
	 *
	 */
	public void newRecipe(){

		// Reset counters
		speedMod++;
		ingCount = 1;
		fullIngCount = 0;
		ingredients = new ArrayList<String>();
		ingredientList = new ArrayList<String>();
		recipeList = new ArrayList<String>();
		checkL = new ArrayList<String>();

		// Get random recipe name
		String[] list = {"victoriasponge", "bread", "brownies", "chocolate chip cookie", "chocolate cake", "gingerbread", "jam", "macaron", "pavlova", "savoury scones", "scones", "teacake"};
		Random r = new Random();
		this.name = list[r.nextInt(list.length)];

		// Load from file
		try{

			loadFromFile();
		} catch(IOException e){ }
	}

	//event handle
	/*public void eventHandle(Event event){

		if (event.type == Event.Type.KEY_PRESSED && event.asKeyEvent().key == Keyboard.Key.R)
			newRecipe();
	}*/

	/**
	 *
	 */
	public void draw() {

		// Check ingredient count
		if(ingCount == fullIngCount) {

			newRecipe();
			score.setBonusScore();
		}

		// RECIPE TEXT
		String rt = "";
		
		rt += recipeList.get(0) + "\n";
		//
		String ft = "";
		for(int i = 0; i < ingredientList.size(); i++){
			
			ft +=  ingredientList.get(i) + "\n";
			ingCount = i+1;
		}
		recList = new Text("" + rt, font, 24);
		recList.setColor(new Color(255, 150, 150));
		recList.setPosition(720,210);
		
		ingsList = new Text("" + ft, font, 24);
		ingsList.setColor(new Color(0, 0, 0));
		ingsList.setPosition(720,240);

		//if()
		
		parent.draw(recList); 
		parent.draw(ingsList); 
	}

	/**
	 *
	 * @return
	 */
	public int getIngCount(){
		
		return ingCount;
	}

	/**
	 *
	 * @return
	 */
	public int getFullIngCount(){
		
		return fullIngCount;
	}
}