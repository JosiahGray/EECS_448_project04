/*
 * 	@author Liia Butler
 * 
 */
import java.util.Timer;

import javax.swing.*;

import java.awt.event.*;
/**
 * The main class of Pong, starts the timer, and timer task in pong game runs the program
 */
public class Pong {
	/**
	 * The "main method" of pong class. Acts as gateway into the pong universe
	 * @post new timer instance is created and scheduled new timer task PongGame executes every 10 ms
	 */
	public Pong(){ 
		createAndDisplayGUI();
		//Timer myTimer = new Timer();
		//myTimer.schedule(new PongGame(), 0, 10);
	}
	private static void createAndDisplayGUI()
	{
		//declare components
		JFrame frame = new JFrame("Pong Selection");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		JMenuBar menuBar;
		JMenu menu;
		JMenuItem menuPongReg;
		JMenuItem menuPong3D;

		//create menu components
		menuBar = new JMenuBar();
		menu = new JMenu("Pongs");

		menuPongReg = new JMenuItem("RegularPong");
		menuPongReg.addActionListener(pongRegListener());

		menuPong3D = new JMenuItem("3D Pong");
		menuPong3D.addActionListener(pong3DListener());

		//build menu
		menu.add(menuPongReg);
		menu.add(menuPong3D);
		menuBar.add(menu);

		//create empty components for spacing
		JPanel emptyPanel;
		JLabel emptyLabel;
		emptyPanel = new JPanel();
		emptyLabel = new JLabel("                                        ");
		emptyPanel.add(emptyLabel);

		//add components to frame
		frame.setJMenuBar(menuBar);
		frame.getContentPane().add(emptyPanel);

		frame.pack();
		frame.setVisible(true);
	}
	private static ActionListener pongRegListener()
	{
		ActionListener listener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//set up Pong game
				Timer myTimer = new Timer();
				myTimer.schedule(new PongGame(), 0, 10);
			}
		};

		return listener;
	}
	private static ActionListener pong3DListener()
	{
		ActionListener listener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//set up Pong game
				//Pong pongGame = new Pong();
				//Timer myTimer = new Timer();
				//myTimer.schedule(new Pong3d1pGame(), 0, 100);
				Pong3d1pGame pong3d = new Pong3d1pGame();
			}
		};

		return listener;
	}
}
