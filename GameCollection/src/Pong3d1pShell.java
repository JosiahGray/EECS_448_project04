import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.WindowAdapter;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.Sphere;
import javax.swing.Timer;


public class Pong3d1pShell {
	/**
	 * Pong3d1pShell()
	 * @post constructs a new Pong3d1pShell instance with control and mainframe
	 * Creates the mainframe to display and control to implement for a new pong game
	 */
	public Pong3d1pShell(){
		
		   Pong3d1pControl control= new Pong3d1pControl();
		   control.addKeyListener(control);
		   MainFrame display = new MainFrame(control, 700, 500);
		   
		
	}

}
