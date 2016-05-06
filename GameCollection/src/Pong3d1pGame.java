
//importation information found on http://www.oxfordmathcenter.com/drupal7/node/44
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;

import javax.media.j3d.*;
import javax.vecmath.*;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.Sphere;

import javax.swing.Timer;

import java.util.TimerTask;


public class Pong3d1pGame {
	Pong3d1pBall ball;
	//Pong3d1pControl control;
	// Pong3d1pHuman human;
	Pong3d1pComputer computer;
	public Pong3d1pGame(){
		ball = new Pong3d1pBall();
		// human = new Pong3d1pHuman();
		computer = new Pong3d1pComputer();
		Pong3d1pShell shell = new Pong3d1pShell(this);
	}


}
