
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
	/**
	 * float representing the radius of the ball in the game
	 */
	float radius;
	/**
	 * float representing the x location of the ball in the game
	 */
	float xLoc;
	/**
	 * float representing the y location of the ball in the game
	 */
	float yLoc;
	/**
	 * float representing the z location of the ball in the game
	 */
	float zLoc;
	/**
	 * float representing the paddle length of the 2 players
	 */
	float paddleLength;
	/**
	 * float representing the paddle width of the 2 players
	 */
	float paddleWidth;
	/**
	 * an array of floats representing the different components of a ball bounce
	 */
	float[] bBounce;
	/** 
	 * int size of the bBounce array
	 */
	int size =5;
	/**
	 * Pong3d1pBall()
	 * @post constructs a new Pong3d1pBall ball logic instance
	 * The constructor for the Pong3d1pBall ball logic
	 */
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
	
	/**
	 * float[] move(Coordinates bCoords, Coordinates hCoords, Coordinates cCoords)
	 * @param bCoords Coordinates representing the location of the ball
	 * @param hCoords Coordinates representing the location of the human
	 * @param cCoords Coordinates representing the location of the computer
	 * @return a float array with the bounce instructions for the ball
	 * Determines the next location of the ball, checks for collision with either paddle and sets the direction
	 * the ball will move in the z direction
	 */
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
	/**
	 * reset()
	 * @post resets float bBounce array to default values
	 * Resets float bBounce array to default values 
	 */
	public void reset(){
		for(int i=0; i< size; i++){
			bBounce[i]=1.0f;
		}
		bBounce[4] = 0.0f;
	}
	

}