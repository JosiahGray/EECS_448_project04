import javax.swing.*;
import javax.media.j3d.*;
import java.awt.event.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.*;
//import java.awt.Component;
//import java.awt.GridLayout;
/**
 * GameCollection Sources:
 * @source http://www.java2s.com/Code/Java/Swing-JFC/Panelwithbackgroundimage.htm
 * Pong Sources:
 * @source https://docs.oracle.com/javase/tutorial/uiswing/events/keylistener.html
 * @source  http://www.oxfordmathcenter.com/drupal7/node/44
 * @source https://docs.oracle.com/javase/tutorial/2d/images/loadimage.html
 * @source http://www.java3d.org/starting.html
 * @source https://community.oracle.com/thread/1275874?start=0&tstart=0
 * @source Java3D Documentation
 * @source http://download.java.net/media/java3d/javadoc/1.3.2/com/sun/j3d/utils/applet/MainFrame.html
 * @source https://docs.oracle.com/javase/7/docs/api/java/awt/Panel.html
 *
 */

public class GameCollection {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		createAndDisplayGUI();
	}

	/**
	 * Creates and displays user interface, implements menu items for each game on a menu and a frame
	 */
	private static void createAndDisplayGUI()
	{
		//declare components

		JFrame frame = new JFrame("Game Collection");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		JMenuBar menuBar;
		JMenu menu;
		JMenuItem menuNim;
		JMenuItem menuPong;
		JMenuItem menuShooty;

		//create menu components
		menuBar = new JMenuBar();
		menu = new JMenu("Games");

		menuNim = new JMenuItem("Nim");
		menuNim.addActionListener(nimListener());

		menuPong = new JMenuItem("Pong");
		menuPong.addActionListener(pongListener());

		menuShooty = new JMenuItem("Shooty Snake");
		menuShooty.addActionListener(shootyListener());

		//build menu
		menu.add(menuNim);
		menu.add(menuPong);
		menu.add(menuShooty);
		menuBar.add(menu);



		//create empty components for spacing
		JPanel emptyPanel;
		//JLabel emptyLabel;
		//emptyPanel = new JPanel();
		//emptyLabel = new JLabel("                                        ");
		//emptyPanel.add(emptyLabel);

		frame.setLayout(new BorderLayout());
		frame.setContentPane(new JLabel(new ImageIcon("GameCollectionBackground1.jpg")));
		frame.setLayout(new FlowLayout());

		
		frame.setSize(200, 300);


		//add components to frame
		frame.add("Center", menuBar);
		//frame.setJMenuBar(menuBar);
		//frame.getContentPane().add(emptyPanel);

		frame.pack();
		frame.setVisible(true);
	}
	/**
	 * Waits for action to implement nim game
	 * @return Action listener listener
	 */

	private static ActionListener nimListener()
	{
		ActionListener listener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//set up Nim game
				JFrame nimFrame = new JFrame("Nim");
				Nim nimGame = new Nim();

				nimFrame.getContentPane().add(nimGame.getContent());
				nimFrame.pack();
				nimFrame.setLocationRelativeTo(null);
				nimFrame.setVisible(true);

			}
		};

		return listener;
	}
	/**
	 * Waits for action to implement pong game
	 * @return Action listener listener
	 */
	private static ActionListener pongListener()
	{
		ActionListener listener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//set up Pong game
				Pong3d1pShell game = new Pong3d1pShell();
			}
		};

		return listener;
	}
	/**
	 * Waits for action to implement shooty snake game
	 * @return Actionlistener listener
	 */

	private static ActionListener shootyListener()
	{
		ActionListener listener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//set up Shooty Snake game
				ShootySnake shootyGame = new ShootySnake();
			}
		};

		return listener;
	}
}
