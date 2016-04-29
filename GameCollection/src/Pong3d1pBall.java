
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.universe.*;

import javax.media.j3d.*;
import javax.vecmath.*;

import com.sun.j3d.utils.geometry.Sphere;

import javax.swing.Timer;

public class Pong3d1pBall{
	float radius;
	float xLoc;
	float yLoc;
	float zLoc;
	
	
	
	public Pong3d1pBall(Pong3d1pGame theGame){
		//get this from Pong Control
		radius = 0.03f; 
	}
	
	//MAKE THIS AN ARRAY SO THAT BALL CAN SQUISH
	//array = (vector x, vector z, x squish, z squish
	public void move(Coordinates bCoords, Coordinates hCoords){
		float[] bBounce = new float[4];
		for(int i =0; i< 4; i++){
			bBounce[i] = 0.0f;
		}
		//put in bounds for where ball is located
		xLoc = bCoords.x;
		yLoc = bCoords.y;
		zLoc = bCoords.z;
		//where all ball extends to
		float xLeft = xLoc + radius;
		float xRight = xLoc - radius;
		//--------Not necessary---------------------
		float yTop = yLoc + radius;
		float yBottom = yLoc - radius;
		//------------------------------------------
		float zFor = zLoc + radius;
		float zBack = zLoc - radius;
		
		//compare
		//if it bounces off x bounds
		if( xLeft < -1.0f ){
			
		}
		
		
	
	}
	

}