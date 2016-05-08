import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import javax.imageio.ImageIO;
import javax.media.j3d.*;
import javax.vecmath.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Component;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import com.sun.j3d.utils.image.*;
import javax.swing.*;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import javax.media.j3d.Alpha;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Material;
import javax.media.j3d.PointLight;
import javax.media.j3d.PositionPathInterpolator;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.ScaleInterpolator;
import javax.media.j3d.SpotLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.Sphere;
import javax.swing.Timer;

public class Pong3d1pControl extends Applet implements ActionListener, KeyListener {
	/**
	 * 	Pong tester used to test the pong game
	 */
	PongTester tester = new PongTester();
	/**
	 * The button which will start a new game of pong
	 */
	Button go = new Button("New Game");
	/**
	 * The button which will call the test function of PongTester test
	 */
	Button test = new Button("Test Game");
	/**
	 *  The transform group which will determine the placement of the ball in the universe
	 */
	TransformGroup ballTrans;
	/**
	 *  The transform group which will determine the placement of the human paddle in the universe
	 */
	TransformGroup humanTrans;
	/**
	 *  The transform group which will determine the placement of the computer paddle in the universe
	 */
	TransformGroup computerTrans;
	/**
	 *  The transform 3d which act as the coordinates to where the ball will be placed, gets added to the TransformGroup
	 */
	Transform3D bTrans = new Transform3D(); //ball transforms
	/**
	 *  The transform 3d which act as the coordinates to where the human paddle will be placed, gets added to the TransformGroup
	 */
	Transform3D hTrans = new Transform3D(); //human transforms
	/**
	 *  The transform 3d which act as the coordinates to where the computer paddle will be placed, gets added to the TransformGroup
	 */
	Transform3D cTrans = new Transform3D(); //computer transforms
	/**
	 * A float representing the z axis placement of the ball
	 */
	float depth=0.0f;
	/**
	 * A float representing which direction along the y axis the ball is traveling
	 */
	float sign = 1.0f; // going up or down
	/**
	 * A float representing which direction along the x axis the ball is traveling
	 */
	float dir = 1.0f; //going left or right
	/**
	 * The timer used to continuously call the functions to simulate the game
	 */
	Timer timer;
	/**
	 * A float representing the x location of the ball
	 */
	float xloc=0.0f;
	/**
	 * A float representiong the x location of the human paddle
	 */
	float hxloc = 0.0f;
	/**
	 * A float representing the point of reference where the bottom of the universe will appear to be
	 */
	float ground = -0.2f;
	/**
	 * A float representing the bounds of how far left or right the universe extends to
	 */
	float xMAX = 0.45f;
	/**
	 * A double representing how much the ball "squishes" or x axis gets pinched when coliding
	 */
	double squish = 1.0;
	/**
	 * Coordinates used for the ball when calculating next placement of all pong components
	 */
	Coordinates bCoords = new Coordinates();
	/**
	 * Coordinates used for the human paddle when calculating next placement of all pong components
	 */
	Coordinates hCoords = new Coordinates();
	/**
	 * Coordinates used for the computer paddle when calculating next placement of all pong components
	 */
	Coordinates cCoords = new Coordinates();
	/**
	 * Pong3d1pComputer used for determining the computer paddle AI logic
	 */
	Pong3d1pComputer cLogic = new Pong3d1pComputer();
	/**
	 * Pong3d1pBall used for determining the ball logic
	 */
	Pong3d1pBall bLogic = new Pong3d1pBall();
	/**
	 * Int representing the size of how many coordinate components are used in each ball logic calculation
	 */
	int size = 5;
	/**
	 * An array of floats of size size representing each of the different components used in the ball logic calculation
	 * 0 = 
	 * 1 =
	 * 2 =
	 * 3 =
	 * 4 = 
	 */
	float[] bBounce = new float[size];
	/**
	 * A float representing the z coordinate of the human paddle
	 */
	float z = 1.5f;
	/**
	 * A float representing the z coordinate of the computer paddle
	 */
	float computerZ = -2.5f;
	/**
	 * A float representing the x coordinate of the computer paddle
	 */
	float computerX = 0.0f;
	/**
	 * A boolean representing if the game has been delayed because a point has been scored
	 */
	boolean isDelayed = false;
	/**
	 * An int representing the current score of the human
	 */
	int humanScore = 0;
	/**
	 * An int representing the current score of the computer
	 */
	int computerScore = 0;
	/**
	 * A boolean representing whether or not a game is taking place right now
	 */
	boolean gameGoing = false;
	/**
	 * An array of spheres that represent the "lives left" of the human
	 */
	Sphere[] compPoints = new Sphere[5];
	/**
	 * An array of spheres that coordinate with the "lives left" of the computer
	 */
	Sphere[] humanPoints = new Sphere[5];
	/**
	 * The transform 3d which act as the coordinates to where the "human lives" will be left
	 */
	Transform3D[] cpTrans = new Transform3D[5];
	/**
	 * The transform 3d which acts as the coordinates to where the "computer lives" will be left
	 */
	Transform3D[] hpTrans = new Transform3D[5];
	/**
	 * An int representing the counter for number of games played
	 */
	int gameCount = 0;
	
	//constructor
	/**
	 * The Pong3d1pControl constructor
	 * Initializes timer, creates pong universe, adds buttons to canvas and sets ball bounce components to default
	 * @post creates a new Pong3d1pControl instance
	 */
	public Pong3d1pControl() {
		setLayout(new BorderLayout());
		//configure for 3d
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		//configure canvas to 3d
		Canvas3D canvas = new Canvas3D(config);
		//add it to the center of the control
		add("Center", canvas);
		//be able to listen for key presses
		canvas.addKeyListener(this);
		//how fast everything updates
		timer = new Timer(5,this); 
		//add panel
		Panel panel =new Panel();
		//add buttons
		panel.add(go);
		panel.add(test);
		//put it at the top, lulz north...
		add("North",panel);
		//add listeners
		go.addActionListener(this);
		go.addKeyListener(this);
		test.addActionListener(this);
		test.addKeyListener(this);
		// Create a simple scene and attach it to universe
		BranchGroup scene = createSceneGraph();
		//make universe and add canvas
		SimpleUniverse universe = new SimpleUniverse(canvas);
		//no special perspective being used
		universe.getViewingPlatform().setNominalViewingTransform();
		//add all the pong components
		universe.addBranchGraph(scene);
		//initialize everything to  default
		for(int i =0; i<size; i++){
			bBounce[i] = 1.0f;
		}

	}
	/**
	 * createSceneGraph()
	 * Will create all the pong objects and put them in the branchgroup to be set to the universe
	 * @return A BranchGroup containing all the pong universe components to be displayed
	 */
	public BranchGroup createSceneGraph() {
	   // Create the root of the branch graph
	   BranchGroup pongRoot = new BranchGroup();
	   //the primitive flags needed in order to alter any of the shape nodes during the game when they go "live"
	   int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
	   
	   //Allow any children of the branch group to have member variables set at any point in the game
	   pongRoot.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
	   
	   //all ball
	   //create the custom ball texture
	   BufferedImage bBall = null;
	   try{
		   bBall = ImageIO.read(new File("suntexture.jpg"));
	   }
	   catch (IOException e){
		   
	   }
	   TextureLoader tl = new TextureLoader(bBall);
	   Texture sun = tl.getTexture();
	   TextureAttributes ta = new TextureAttributes();
	   ta.setTextureMode(TextureAttributes.MODULATE);
	   //make the appearance the freshly created texture
	   Appearance ballA = new Appearance();
	   ballA.setTexture(sun);
	   ballA.setTextureAttributes(ta);
	   
	   //set material to the colors of the image to enhance the texture colors
	   Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
	   Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
	   Color3f orange = new Color3f(0.8f, 0.5f, 0.0f);
	   ballA.setMaterial(new Material (orange, white, orange, black, 1.0f));
	   
	   //create the flag according to the primitive flags and the appearance just created
	   Sphere ball = new Sphere(0.025f, primflags, ballA);
	   //initialize the transform group
	   ballTrans = new TransformGroup();
	   //set capabilities for the transformgroup so the ball can be altered during the game when it's "live"
	   ballTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	   ballTrans.setCapability(ball.ENABLE_APPEARANCE_MODIFY);
	   ballTrans.setCapability(ball.ENABLE_COLLISION_REPORTING);
	   //set the first position the ball will be placed in when initialization completes
	   Transform3D pos1 = new Transform3D();
	   //set the "coordinates to the defaults
	   pos1.setTranslation(new Vector3f(0.0f,ground,0.0f));
	   //add components to the transform group
	   ballTrans.setTransform(pos1);
	   ballTrans.addChild(ball);
	   //add the transform group to the branchgroup
	   pongRoot.addChild(ballTrans);
	   
	   
	   
	   // create the balls representing the "lives"
	   //each ball will have a different transform group, so make an array of them
	   TransformGroup[] pointCompTrans = new TransformGroup[5];
	   TransformGroup[] pointHumanTrans = new TransformGroup[5];
	   //initialize them all the same
	   for(int i = 0; i<5; i++){
		   //initialize the individual transform group
		   pointCompTrans[i] = new TransformGroup();
		   //make a new appearance
		   Appearance cpa = new Appearance();
		   //make new sphere, allow textures, normals, and ability to modify during "live" play
		   compPoints[i] = new Sphere(0.04f, Primitive.GENERATE_TEXTURE_COORDS + Primitive.GENERATE_NORMALS + Sphere.ENABLE_APPEARANCE_MODIFY, 20);
		   //set capabilities to allow modifications during "live" play
		   compPoints[i].setCapability(Sphere.ENABLE_APPEARANCE_MODIFY);
		   compPoints[i].setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
		   pointCompTrans[i].setCapability(compPoints[i].ENABLE_APPEARANCE_MODIFY);
		   pointCompTrans[i].setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		   //initialize transform3d for current sphere
		   cpTrans[i] = new Transform3D();
		   //set the coordinates according to where we are in the forloop
		   cpTrans[i].setTranslation(new Vector3f(-0.11f * i - 0.09f, 0.5f, 0.0f));
		   //add components to transform group
		   pointCompTrans[i].setTransform(cpTrans[i]);
		   pointCompTrans[i].addChild(compPoints[i]);
		   //add transform group to branch group
		   pongRoot.addChild(pointCompTrans[i]);
		   
		   //initialize individual transform group
		   pointHumanTrans[i] = new TransformGroup();
		   //make new sphere, allow textures, normals, and ability to modify during "live" play
		   humanPoints[i] = new Sphere(0.04f, Primitive.GENERATE_NORMALS + Sphere.ENABLE_APPEARANCE_MODIFY, 20);
		   humanPoints[i].setCapability(Sphere.ENABLE_APPEARANCE_MODIFY);
		   pointHumanTrans[i].setCapability(humanPoints[i].ENABLE_APPEARANCE_MODIFY);
		   pointHumanTrans[i].setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		   //initialize transform3d for current sphere
		   hpTrans[i] = new Transform3D();
		   hpTrans[i].setTranslation(new Vector3f(0.11f * i +0.09f, 0.5f, 0.0f));
		   //add components to transform group
		   pointHumanTrans[i].setTransform(hpTrans[i]);
		   pointHumanTrans[i].addChild(humanPoints[i]);
		   //ad transform group to the branch group
		   pongRoot.addChild(pointHumanTrans[i]);
		   
	   }
	   
	   
	   
	   
	   //human
	   //create texture loader for the paddle texture
	   TextureLoader tlp = new TextureLoader("paddletexture.jpg", "LUMINANCE", new Container());
	   Texture paddle = tlp.getTexture();
	   TextureAttributes tap = new TextureAttributes();
	   tap.setTextureMode(TextureAttributes.MODULATE);
	   //set appearance
	   Appearance a = new Appearance();
	   a.setTexture(paddle);
	   a.setTextureAttributes(tap);
	   
	   
	   
	   
	   //create color to enhance texture
	   Color3f ambientC = new Color3f(1.0f, 1.0f, 1.0f);
	   Color3f diffuseC = new Color3f(1.0f, 1.0f, 1.0f);
	   Color3f specularC = new Color3f(1.0f, 1.0f, 0.5f);
	   Color3f emissiveC = new Color3f(0.0f, 0.0f, 0.0f);
	   float shine = 0.0f;
	   Material m = new Material ();
	   m.setAmbientColor(ambientC);
	   m.setDiffuseColor(diffuseC);
	   m.setSpecularColor(specularC);
	   m.setShininess(shine);
	   a.setMaterial(m);
	   //make paddle slightly transparent to see ball coming through
	   TransparencyAttributes transpa = new TransparencyAttributes(TransparencyAttributes.BLENDED, 0.15f);
	   a.setTransparencyAttributes(transpa);
	   
	   //create new box (Box belongs to more than one library so have to be specific) using dimensions, previous primitive flags, and freshly constructed appearance
	   com.sun.j3d.utils.geometry.Box human = new com.sun.j3d.utils.geometry.Box(0.1f, 0.03f, 0.025f, primflags, a);
	   //initialize transformgroup
	   humanTrans = new TransformGroup();
	   //set capabilities for human paddle so it can be modified during "live" play
	   humanTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	   humanTrans.setCapability(human.ENABLE_APPEARANCE_MODIFY);
	   humanTrans.setCapability(human.ENABLE_COLLISION_REPORTING);
	   
	   //initialize transform 3d
	   Transform3D hpos = new Transform3D();
	   //set coordinates to the default
	   hpos.setTranslation(new Vector3f(hxloc, ground, z));
	   //add components to transform group
	   humanTrans.setTransform(hpos);
	   humanTrans.addChild(human);
	   //add transform group to branch group
	   pongRoot.addChild(humanTrans);
	   
	   //computer
	   //set appearance to same as human
	   Appearance a1 = new Appearance();
	   a1.setTexture(paddle);
	   a1.setTextureAttributes(tap);
	   
	 //create new box (Box belongs to more than one library so have to be specific) using dimensions, previous primitive flags, and appearance
	   com.sun.j3d.utils.geometry.Box computer = new com.sun.j3d.utils.geometry.Box(0.1f, 0.03f, 0.025f, primflags, a1);
	   computerTrans = new TransformGroup();
	   //set capabilities for computer paddle so it can be modified during "live" play
	   computerTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	   computerTrans.setCapability(computer.ENABLE_APPEARANCE_MODIFY);
	   computerTrans.setCapability(computer.ENABLE_COLLISION_REPORTING);
	   
	   //initialize transform 3d
	   Transform3D cpos = new Transform3D();
	   //set coordinates to the default
	   cpos.setTranslation(new Vector3f(0.0f, ground, computerZ));
	   //add components to transform group
	   computerTrans.setTransform(cpos);
	   computerTrans.addChild(computer);
	   //add transform group to branch group
	   pongRoot.addChild(computerTrans);
	   
	   //add a bounding box to limit the view of the universe
	   BoundingBox bounds = new BoundingBox(new Point3d(-0.5f,ground - 0.2f,computerZ-0.5f), new Point3d(0.5f, 1.0, z+0.5f)); //SEE IF YOU NEED BOUNDING BOX
	 
	   
	   //first light to light up the universe, making it blue green tint
	   Color3f light1Color = new Color3f(0.0f, 1.0f, 1.0f);
	   //make direction looking up, lighting bottom
	   Vector3f light1Direction = new Vector3f(0.0f, 5.0f, 1.0f);
	   //set light
	   DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
	   //set light bounds
	   light1.setInfluencingBounds(bounds);
	   //add to branch group
	   pongRoot.addChild(light1);
	   
	   //add second color, more blue
	   Color3f light2Color = new Color3f(0.5f, 0.6f, 0.88f);
	   //make direction looking down
	   Vector3f light2Direction = new Vector3f(1.0f, -2.0f, 0.0f);
	   //set light
	   DirectionalLight light2 = new DirectionalLight(light2Color, light2Direction);
	   //set light bounds
	   light2.setInfluencingBounds(bounds);
	   //add to branch group
	   pongRoot.addChild(light2);

	   // Set up the ambient light, overall color just make white to light the universe
	   Color3f ambientColor = new Color3f(1.0f, 1.0f, 1.0f);
	   //declare the ambient light
	   AmbientLight ambientLightNode = new AmbientLight(ambientColor);
	   //add bounds
	   ambientLightNode.setInfluencingBounds(bounds);
	   //add to branch group
	   pongRoot.addChild(ambientLightNode);
	   
	   //https://docs.oracle.com/javase/tutorial/2d/images/loadimage.html
	   //add bacground
	   BufferedImage bi = null;
	   try{
		   bi = ImageIO.read(new File("Background1.jpg"));
	   }
	   catch (IOException e){
		   
	   }
	   RenderedImage ri = bi;
	   
	   //make a imagecomponent2d
	   ImageComponent2D ic = new ImageComponent2D(ImageComponent2D.FORMAT_RGB, ri);
	   //ic.set(bi);
	  // ic.set(ri);
	   //make a new background using the image component 2d
	   Background bkgd = new Background(ic);
	   //set the bounds
	   bkgd.setApplicationBounds(bounds);
	   //make color darker, more space like
	   bkgd.setColor(0.1f, 0.1f, 0.1f);
	   //add background to branch group
	   pongRoot.addChild(bkgd);
	   
	   //FINALLY DONE ADDING EVERYTHING, RETURN THE BRANCHING GROUP
	   return pongRoot;

	}
	public void keyPressed(KeyEvent e) {
		//this will take over for human paddle
		
		if (e.getKeyChar()=='s'){
			if(hxloc < 0.45f){
				hxloc = hxloc + .015f;
			} else {
				hxloc = 0.45f;
			}
		}
		if (e.getKeyChar()=='a'){
			if(hxloc > -0.45f){
				hxloc = hxloc - .015f;
			} else {
				hxloc = -0.45f;
			}
		}

	}
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
	public void actionPerformed(ActionEvent e ) {
		if(e.getSource() == test){
			tester.test(this);
			humanScore = 5;
			newGame();
		}
		else{
		// start timer when button is pressed
			if (e.getSource()==go){
			
				if (!timer.isRunning() && !gameGoing) {
					if(gameCount > 0){
						newGame();
					}
					gameGoing = true;
					timer.start();
					gameCount ++;
				}
			} else {
				if(!isDelayed){
					update();
					cLogic.executionTime ++;
				} else {
					isDelayed = false;
					timer.setDelay(5);
				}
			
			}
		}
	}
	public void update(){
		updateSquish();
		checkScore();
		updateBall();
		updateHuman();
		updateComputer();

	}
	public void updateSquish(){
		if(depth > 2.0f){
			xMAX = 0.4f;
		} else {
			xMAX = 0.45f;
		}
		if(xloc >= xMAX){
			xloc = xMAX;
			dir = -1.0f;
			squish = 0.5;
				
		} else if(xloc<= -xMAX){
			xloc = -xMAX;
			dir = 1.0f;
			squish = 0.5;
		} else {
			if(squish < 1.0){
				squish += 0.02;
			} else {
				squish = 1.0;
			}
		}
			
	}
	public void checkScore(){
		if(depth >= z + .5f){
			
			//goal on human
			Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
			Appearance goalA = new Appearance();
			goalA.setCapability(Appearance.ALLOW_MATERIAL_WRITE);
			goalA.setMaterial(new Material (black, black, black, black, 1.0f));
			humanPoints[computerScore].setAppearance(goalA);
			computerScore++;
			reset();
				
		} else if (depth <= computerZ - .5f){
			Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
			Appearance goalA = new Appearance();
			goalA.setCapability(Appearance.ALLOW_MATERIAL_WRITE);
			goalA.setMaterial(new Material (black, black, black, black, 1.0f));
			compPoints[humanScore].setAppearance(goalA);
			//goal on computer
			humanScore++;
			reset();
			
				
		} else {
			//do ball collision logic			
			

		}
	}
	public void updateBall(){
		bBounce = bLogic.move(bCoords, hCoords, cCoords);
		sign = bBounce[1];
		depth += (.01 * sign) + bBounce[3];
		xloc += bBounce[4] * dir;
		bTrans.setScale(new Vector3d(squish, 1.0, 1.0));
		bTrans.setTranslation(new Vector3f(xloc,ground,depth));
		ballTrans.setTransform(bTrans);
		bCoords.setCoordinates(xloc, ground, depth);
	}
	public void updateHuman(){
		hTrans.setTranslation(new Vector3f(hxloc, ground, z));
		humanTrans.setTransform(hTrans);
		hCoords.setCoordinates(hxloc, ground, z);
	}
	
	public void updateComputer(){
		float nextLoc = cLogic.move(bCoords, cCoords);	
		computerX += nextLoc;
		if(computerX <= -0.45f){
			computerX = -0.45f;
		} else if(computerX >= 0.45f){
			computerX = 0.45f;
		}
		cTrans.setTranslation(new Vector3f(computerX, ground, computerZ));
		computerTrans.setTransform(cTrans);
		cCoords.setCoordinates(computerX, ground, computerZ);
	}
	public void reset(){
		xloc = 0.0f;
		depth = 0.0f;
		squish = 1.0;
		for(int i=0; i<4; i++){
			bBounce[i] = 1.0f;
		}
		hxloc =0.0f;
		computerX = 0.0f;
		bLogic.reset();
		timer.setDelay(2000);
		isDelayed = true;
		isGameOver();
		
	}
	public void isGameOver(){
		if(humanScore >=5){
			//display human won
			//prompt to play new game
			timer.stop();
		} else if (computerScore >= 5){
			//display computer won
			//prompt to play new game
			timer.stop();
		}
		
		gameGoing = false;
	}
	public void newGame(){
		cLogic.increaseDifficulty = 0.0f;
		cLogic.executionTime = 0;
		xloc = 0.0f;
		depth = 0.0f;
		squish = 1.0;
		for(int i=0; i<4; i++){
			bBounce[i] = 1.0f;
		}
		hxloc =0.0f;
		computerX = 0.0f;
		bLogic.reset();
		if(humanScore >= 5){
			for(int i = 0; i<5; i++){
			 compPoints[i].setAppearance(humanPoints[4].getAppearance());
			 humanPoints[i].setAppearance(humanPoints[4].getAppearance());
			}
		} else if(computerScore >=5){
			for(int i = 0; i<5; i++){
				 compPoints[i].setAppearance(compPoints[4].getAppearance());
				 humanPoints[i].setAppearance(compPoints[4].getAppearance());
			}
		}
		
		humanScore = 0;
		computerScore = 0;
	}

}
