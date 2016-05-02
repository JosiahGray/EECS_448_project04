import java.awt.*;

import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.*;

import java.awt.Frame;

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

import com.sun.j3d.utils.geometry.*;

import javax.swing.Timer;

public class Pong3d1pScene extends JPanel{
	//height and width of panel
	/*int width;
	int height;
	//dimensions to be set by height and width
	Dimension d;
	int bound;
	//initial point for how the 3d will be perceived
	Point3d initPt;
	//the reference point for how the 3d will be perceived at that instance
	Point3d refPt;
	//the universe where everything takes place (j3d standard)
	SimpleUniverse universe;
	//the limiting factor of all 3d components
	Appearance a;
	int i;
	
	public Pong3d1pScene(Pong3d1pGame theGame){
		//set variables
		width = 700;
		height = 500;
		bound = 150; //LOOK INTO THIS
		initPt = new Point3d(0,0,0);
		refPt = new Point3d(0,10,30); //make this the reference point
		d = new Dimension(width, height);
		setLayout(new BorderLayout());
		setOpaque(false);
		setPreferredSize(d);
		
		GraphicsConfiguration gConfig = SimpleUniverse.getPreferredConfiguration();
		Canvas3D canvas = new Canvas3D(gConfig);
		
		add("Center", canvas);
		canvas.setFocusable(true);
		canvas.requestFocus();
		
		universe = new SimpleUniverse(canvas);
		universe.getViewingPlatform().setNominalViewingTransform();
		draw(theGame);
		//ready to create scene
		//createScene(theGame);
		
		//bg.compile();
		//universe.getViewingPlatform().setNominalViewingTransform();
		//universe.addBranchGraph(bg);
		
	}
	public void draw(Pong3d1pGame theGame){
		BranchGroup bg = new BranchGroup();
		BoundingSphere bs = new BoundingSphere(initPt, bound);
		lightScene(bg, bs);
		addBackground(bg, bs);
		Vector3f movingVec = new Vector3f(0.1f * 2*i, 0.1f * i, 0.1f * i);
		Transform3D trans = new Transform3D();
		trans.setTranslation(movingVec);
		TransformGroup balltg = new TransformGroup();//theGame.ball.trans);
		balltg.setTransform(trans);
		balltg.addChild(theGame.ball.draw());
		bg.addChild(balltg);
		bg.compile();
		universe.addBranchGraph(bg);
		i++;
	}
	public void clear(Pong3d1pGame theGame){
		theGame.ball.clear();
		universe.removeAllLocales();
		universe.cleanup();
	}
	public void lightScene(BranchGroup bg, BoundingSphere bs){
		
		Color3f white = new Color3f(0.9f, 0.9f, 0.9f);
		AmbientLight alNode = new AmbientLight(white);
		alNode.setInfluencingBounds(bs);
		bg.addChild(alNode);
		
		//Directional light
		Vector3f l1Direction = new Vector3f(-1.0f, -1.0f, -1.0f); //left, down, backwards
		DirectionalLight l1 = new DirectionalLight(white, l1Direction);
		l1.setInfluencingBounds(bs);
		bg.addChild(l1);
		
		Vector3f l2Direction = new Vector3f(1.0f, -1.0f, 1.0f); //right, down, forwards
		DirectionalLight l2 = new DirectionalLight(white, l2Direction);
		l2.setInfluencingBounds(bs);
		bg.addChild(l2);
	}
	public void addBackground(BranchGroup bg, BoundingSphere bs){
		Background bkgd = new Background();
		bkgd.setApplicationBounds(bs);
		bkgd.setColor(0.4f, 0.4f, 0.4f);
		*/

}
