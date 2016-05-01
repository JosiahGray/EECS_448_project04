
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
	
	float paddleLength;
	float paddleWidth;
	float[] bBounce;
	int size =5;
	public Pong3d1pBall(){
		//get this from Pong Control
		radius = 0.025f; 
		//get this from pong control
		paddleLength = 0.1f; 
		paddleWidth = 0.025f;
		bBounce = new float[size];
		for(int i=0; i< size; i++){
			bBounce[i]=1.0f;
		}
		bBounce[4] = 0.0f;
	}
	
	//MAKE THIS AN ARRAY SO THAT BALL CAN SQUISH
	//array = (vector x, vector z, x squish, z squish, speed up ball)
	public float[] move(Coordinates bCoords, Coordinates hCoords, Coordinates cCoords){
		bBounce[3] = 0.0f;
		//put in bounds for where ball is located
		xLoc = bCoords.x;
		yLoc = bCoords.y;
		zLoc = bCoords.z;
		if((zLoc < (hCoords.z + paddleWidth)) && (zLoc > (hCoords.z)) && xLoc <= (hCoords.x + paddleLength + radius) && xLoc >= (hCoords.x - paddleLength-radius)){// && (xLeft < (hCoords.x + paddleLength)) && (xRight > (hCoords.x)) ){
			
			//squish front
			bBounce[0] = -1.0f;
			bBounce[1] = -1.0f;
			bBounce[3] = -0.01f;
			if(xLoc >= 0.0f){
				bBounce[4] = (xLoc - hCoords.x + (hCoords.x)/3)/50;
			} else{
				bBounce[4] = -(xLoc - hCoords.x + (hCoords.x)/3)/50;
			}
			
		}
		if(zLoc < (cCoords.z + paddleWidth/2) && (zLoc > cCoords.z) && xLoc <= (cCoords.x + paddleLength + radius) && xLoc >= (cCoords.x - paddleLength)){
			bBounce[0] = 1.0f;
			bBounce[1] = 1.0f;
			bBounce[3] = 0.01f;
			if(xLoc >= 0.0f){
				bBounce[4] = (xLoc -cCoords.x + (cCoords.x)/3)/50;
			} else {
				bBounce[4] = -(xLoc -cCoords.x + (cCoords.x)/3)/50;
			}
		}

		return bBounce;
	}
	public void reset(){
		for(int i=0; i< size; i++){
			bBounce[i]=1.0f;
		}
		bBounce[4] = 0.0f;
	}
	

}