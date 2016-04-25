import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Component;
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

	Button go = new Button("Go");
	TransformGroup ballTrans;
	TransformGroup humanTrans;
	TransformGroup computerTrans;
	Transform3D bTrans = new Transform3D();
	Transform3D hTrans = new Transform3D();
	float depth=0.0f;
	float sign = 1.0f; // going up or down
	Timer timer;
	float xloc=0.0f;
	float hxloc = 0.0f;

	//constructor
	public Pong3d1pControl() {
		setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		//configure for 3d
		Canvas3D canvas = new Canvas3D(config);
		//add it to the center of the control
		add("Center", canvas);
		canvas.addKeyListener(this);
		timer = new Timer(10,this); //how fast everything updates
		//timer.start();
		Panel panel =new Panel();
		panel.add(go);
		add("North",panel);
		go.addActionListener(this);
		go.addKeyListener(this);
		// Create a simple scene and attach it to the virtual universe
		BranchGroup scene = createSceneGraph();
		SimpleUniverse universe = new SimpleUniverse(canvas);
		universe.getViewingPlatform().setNominalViewingTransform();
		universe.addBranchGraph(scene);

	}
	public BranchGroup createSceneGraph() {
	   // Create the root of the branch graph
	   BranchGroup pongRoot = new BranchGroup();
	   
	   //all ball
	   Sphere ball = new Sphere(0.1f);
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
	   Color3f ambientC = new Color3f(0.2f, 0.2f, 0.9f);
	   Color3f diffuseC = new Color3f(1.0f, 1.0f, 0.5f);
	   Color3f specularC = new Color3f(1.0f, 1.0f, 0.5f);
	   Color3f emissiveC = new Color3f(0.0f, 0.0f, 0.0f);
	   float shine = 128.0f;
	   Material m = new Material ();
	   Appearance a = new Appearance(); 
	   m.setAmbientColor(ambientC);
	   m.setDiffuseColor(diffuseC);
	   m.setSpecularColor(specularC);
	   m.setShininess(shine);
	   a.setMaterial(m);
	   com.sun.j3d.utils.geometry.Box human = new com.sun.j3d.utils.geometry.Box(0.1f, 0.05f, 0.025f, a);
	   humanTrans = new TransformGroup();
	   humanTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	   humanTrans.setCapability(human.ENABLE_APPEARANCE_MODIFY);
	   humanTrans.setCapability(human.ENABLE_COLLISION_REPORTING);
	   
	   Transform3D hpos = new Transform3D();
	   hpos.setTranslation(new Vector3f(0.5f, -0.3f, 1.0f));
	   humanTrans.setTransform(hpos);
	   humanTrans.addChild(human);
	   pongRoot.addChild(humanTrans);
	   
	  
	   BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0); //SEE IF YOU NEED BOUNDING BOX
	   
	   Color3f light1Color = new Color3f(0.9f, 0.9f, 0.9f);

	   Vector3f light1Direction = new Vector3f(-1.0f, -1.0f, -1.0f);

	   DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);

	   light1.setInfluencingBounds(bounds);

	   pongRoot.addChild(light1);

	   // Set up the ambient light

	   Color3f ambientColor = new Color3f(0.9f, 0.9f, 0.9f);

	   AmbientLight ambientLightNode = new AmbientLight(ambientColor);

	   ambientLightNode.setInfluencingBounds(bounds);

	   pongRoot.addChild(ambientLightNode);
	   
	   Background bkgd = new Background();
	   bkgd.setApplicationBounds(bounds);
	   bkgd.setColor(0.4f, 0.4f, 0.4f);
	   pongRoot.addChild(bkgd);
	   return pongRoot;

	}
	public void keyPressed(KeyEvent e) {
		//this will take over for human paddle
		
		if (e.getKeyChar()=='s'){
			hxloc = hxloc + .01f;
		}

		if (e.getKeyChar()=='a'){
			hxloc = hxloc - .01f;
		}

	}
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
	public void actionPerformed(ActionEvent e ) {
		// start timer when button is pressed
		if (e.getSource()==go){
			
			if (!timer.isRunning()) {
				timer.start();
			}
		} else {

			depth += .01 * sign;

			if (depth >= 1 || depth<= -1 ) {
				sign = -1.0f * sign;
			}
			
			if (depth<-0.9f) {
				bTrans.setScale(new Vector3d(1.0, 1.0, 0.8));
			}
			else if(depth< 0.9f){
				bTrans.setScale(new Vector3d(1.0, 1.0, 1.5));
			}
			else {
				bTrans.setScale(new Vector3d(1.0, 1.0, 1.0));
			}
			bTrans.setTranslation(new Vector3f(xloc,0.0f,depth));
			ballTrans.setTransform(bTrans);
			hTrans.setTranslation(new Vector3f(hxloc, 0.0f, 1.0f));
			humanTrans.setTransform(hTrans);
			

		}

	}

}
