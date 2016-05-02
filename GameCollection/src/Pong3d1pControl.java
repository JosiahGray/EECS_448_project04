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

	Button go = new Button("New Game");
	TransformGroup ballTrans;
	TransformGroup humanTrans;
	TransformGroup computerTrans;
	Transform3D bTrans = new Transform3D(); //ball transforms
	Transform3D hTrans = new Transform3D(); //human transforms
	Transform3D cTrans = new Transform3D();
	float depth=0.0f;
	float sign = 1.0f; // going up or down
	float dir = 1.0f; //going left or right
	Timer timer;
	float xloc=0.0f;
	float hxloc = 0.0f;
	float ground = -0.2f;
	float xMAX = 0.45f;
	double squish = 1.0;
	Coordinates bCoords = new Coordinates();
	Coordinates hCoords = new Coordinates();
	Coordinates cCoords = new Coordinates();
	Pong3d1pComputer cLogic = new Pong3d1pComputer();
	Pong3d1pBall bLogic = new Pong3d1pBall();
	int size = 5;
	float[] bBounce = new float[size];
	
	float z = 1.5f;
	float computerZ = -2.5f;
	float computerX = 0.0f;
	boolean isDelayed = false;
	
	int humanScore = 0;
	int computerScore = 0;
	boolean gameGoing = false;
	
	//constructor
	public Pong3d1pControl() {
		setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		//configure for 3d
		Canvas3D canvas = new Canvas3D(config);
		//add it to the center of the control
		add("Center", canvas);
		canvas.addKeyListener(this);
		timer = new Timer(5,this); //how fast everything updates
		//timer.start();
		Panel panel =new Panel();
		panel.add(go);
		add("North",panel);
		go.addActionListener(this);
		go.addKeyListener(this);
		// Create a simple scene and attach it to universe
		BranchGroup scene = createSceneGraph();
		SimpleUniverse universe = new SimpleUniverse(canvas);
		universe.getViewingPlatform().setNominalViewingTransform();
		universe.addBranchGraph(scene);
		for(int i =0; i<size; i++){
			bBounce[i] = 1.0f;
		}

	}
	public BranchGroup createSceneGraph() {
	   // Create the root of the branch graph
	   BranchGroup pongRoot = new BranchGroup();
	   
	   //all ball
	   BufferedImage bBall = null;
	   try{
		   bBall = ImageIO.read(new File("suntexture.jpg"));
	   }
	   catch (IOException e){
		   
	   }
	   int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
	   
	   TextureLoader tl = new TextureLoader(bBall);
	   Texture sun = tl.getTexture();
	   TextureAttributes ta = new TextureAttributes();
	   ta.setTextureMode(TextureAttributes.MODULATE);
	   Appearance ballA = new Appearance();
	   ballA.setTexture(sun);
	   ballA.setTextureAttributes(ta);
	   
	   Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
	   Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
	   Color3f orange = new Color3f(0.8f, 0.5f, 0.0f);
	   ballA.setMaterial(new Material (orange, white, orange, black, 1.0f));
	   
	   Sphere ball = new Sphere(0.025f, primflags, ballA);
	   ballTrans = new TransformGroup();
	   ballTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	   ballTrans.setCapability(ball.ENABLE_APPEARANCE_MODIFY);
	   ballTrans.setCapability(ball.ENABLE_COLLISION_REPORTING);
	   Transform3D pos1 = new Transform3D();
	   pos1.setTranslation(new Vector3f(0.0f,0.0f,0.0f));
	   ballTrans.setTransform(pos1);
	   ballTrans.addChild(ball);
	   pongRoot.addChild(ballTrans);
	   
	   //human
	   
	   TextureLoader tlp = new TextureLoader("paddletexture.jpg", "LUMINANCE", new Container());
	   Texture paddle = tlp.getTexture();
	   TextureAttributes tap = new TextureAttributes();
	   tap.setTextureMode(TextureAttributes.MODULATE);
	   Appearance a = new Appearance();
	   a.setTexture(paddle);
	   a.setTextureAttributes(tap);
	   
	   
	   
	   
	   
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
	   TransparencyAttributes transpa = new TransparencyAttributes(TransparencyAttributes.BLENDED, 0.15f);
	   a.setTransparencyAttributes(transpa);
	   
	  
	   com.sun.j3d.utils.geometry.Box human = new com.sun.j3d.utils.geometry.Box(0.1f, 0.03f, 0.025f, primflags, a);
	   humanTrans = new TransformGroup();
	   humanTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	   humanTrans.setCapability(human.ENABLE_APPEARANCE_MODIFY);
	   humanTrans.setCapability(human.ENABLE_COLLISION_REPORTING);
	   
	   Transform3D hpos = new Transform3D();
	   hpos.setTranslation(new Vector3f(0.5f, ground, z));
	   humanTrans.setTransform(hpos);
	   humanTrans.addChild(human);
	   pongRoot.addChild(humanTrans);
	   
	   //computer
	   Appearance a1 = new Appearance();
	   a1.setTexture(paddle);
	   a1.setTextureAttributes(tap);
	   
	   
	   com.sun.j3d.utils.geometry.Box computer = new com.sun.j3d.utils.geometry.Box(0.1f, 0.03f, 0.025f, primflags, a1);
	   computerTrans = new TransformGroup();
	   computerTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	   computerTrans.setCapability(computer.ENABLE_APPEARANCE_MODIFY);
	   computerTrans.setCapability(computer.ENABLE_COLLISION_REPORTING);
	   
	   Transform3D cpos = new Transform3D();
	   cpos.setTranslation(new Vector3f(0.0f, ground, computerZ));
	   computerTrans.setTransform(cpos);
	   computerTrans.addChild(computer);
	   pongRoot.addChild(computerTrans);
	   
	   
	   
	   
	   
	  
	   BoundingBox bounds = new BoundingBox(new Point3d(-0.5f,ground - 0.2f,computerZ-0.5f), new Point3d(0.5f, 1.0, z+0.5f)); //SEE IF YOU NEED BOUNDING BOX
	 
	   
	   
	   Color3f light1Color = new Color3f(0.0f, 1.0f, 1.0f);
	   Vector3f light1Direction = new Vector3f(0.0f, 2.0f, 0.0f);
	   DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
	   light1.setInfluencingBounds(bounds);
	   pongRoot.addChild(light1);

	   // Set up the ambient light
	   Color3f ambientColor = new Color3f(0.9f, 0.9f, 0.9f);
	   AmbientLight ambientLightNode = new AmbientLight(ambientColor);
	   ambientLightNode.setInfluencingBounds(bounds);
	   pongRoot.addChild(ambientLightNode);
	   
	   //https://docs.oracle.com/javase/tutorial/2d/images/loadimage.html
	   BufferedImage bi = null;
	   try{
		   bi = ImageIO.read(new File("Background1.jpg"));
	   }
	   catch (IOException e){
		   
	   }
	   RenderedImage ri = bi;
	   
	   ImageComponent2D ic = new ImageComponent2D(ImageComponent2D.FORMAT_RGB, ri);
	   //ic.set(bi);
	  // ic.set(ri);
	   Background bkgd = new Background(ic);
	   bkgd.setApplicationBounds(bounds);
	   bkgd.setColor(0.1f, 0.1f, 0.1f);
	   pongRoot.addChild(bkgd);
	   return pongRoot;

	}
	public void keyPressed(KeyEvent e) {
		//this will take over for human paddle
		
		if (e.getKeyChar()=='s'){
			hxloc = hxloc + .015f;
		}
		if (e.getKeyChar()=='a'){
			hxloc = hxloc - .015f;
		}

	}
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
	public void actionPerformed(ActionEvent e ) {
		// start timer when button is pressed
		if (e.getSource()==go){
			
			if (!timer.isRunning() && !gameGoing) {
				humanScore = 0;
				computerScore = 0;
				gameGoing = true;
				timer.start();
			}
		} else {
			if(!isDelayed){
				update();
			} else {
				isDelayed = false;
				timer.setDelay(5);
			}
			
		}
	}
	public void update(){
		if(xloc >= xMAX){
			xloc = xMAX;
			dir = -1.0f;
			squish = 0.85;
				
		} else if(xloc<= -xMAX){
			xloc = -xMAX;
			dir = 1.0f;
			squish = 0.85;
		} else {
			if(squish < 1.0){
				squish += 0.01;
			} else {
				squish = 1.0;
			}
		}
			
		if(depth >= z + .5f){
			
			//goal on human
			computerScore++;
			reset();
				
		} else if (depth <= computerZ - .5f){
			//goal on computer
			humanScore++;
			reset();
			
				
		} else {
			//do ball collision logic			
			bBounce = bLogic.move(bCoords, hCoords, cCoords);

		}
		sign = bBounce[1];
		depth += (.01 * sign) + bBounce[3];
		xloc += bBounce[4] * dir;
		bTrans.setScale(new Vector3d(squish, 1.0, 1.0));
		
		
		bTrans.setTranslation(new Vector3f(xloc,ground,depth));
		ballTrans.setTransform(bTrans);
		bCoords.setCoordinates(xloc, ground, depth);
			
			
		hTrans.setTranslation(new Vector3f(hxloc, ground, z));
		humanTrans.setTransform(hTrans);
		hCoords.setCoordinates(hxloc, ground, z);
		
		
		float nextLoc = cLogic.move(bCoords, cCoords);	
		computerX += nextLoc;
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

}
