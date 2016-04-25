import java.awt.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.*;
import java.awt.Frame;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

public class Pong3d1pScene extends JPanel{
	int width;
	int height;
	Dimension d;
	int bound;
	Point3d initPt;
	Point3d refPt;
	SimpleUniverse universe;
	BranchGroup bg;
	BoundingSphere bs;
	public Pong3d1pScene(){
		//set variables
		width = 600;
		height = 600;
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
		//ready to create scene
		createScene();
		
		universe.addBranchGraph(bg);
		
	}
	public void createScene(){
		bg  = new BranchGroup();
		bs = new BoundingSphere(initPt, bound);
		
		lightScene();
		addBackground();
		bg.compile();
	}
	public void lightScene(){
		
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
	public void addBackground(){
		Background bkgd = new Background();
		bkgd.setApplicationBounds(bs);
		bkgd.setColor(0.4f, 0.4f, 0.4f);
		bg.addChild(bkgd);
	}

}
