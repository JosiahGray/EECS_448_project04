import javax.swing.*;
import java.awt.event.*;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * A class that creates a Nim game.
 * @author Josiah Gray
 *
 */
public class Nim
{
	private JPanel gamePanel;
	private JLabel instrLabel;
	private JLabel stonesLabel;
	private JLabel movesLabel;
	private JLabel buttonLabel;
	private JPanel buttonPanel;
	private JButton button1;
	private JButton button2;
	private JButton button3;
	private JLabel difficultyLabel;
	private JPanel difficultyPanel;
	private JButton buttonEasy;
	private JButton buttonNormal;
	private JButton buttonHard;
	private JButton newGameButton;

	private int stones;
	private boolean gameOver;
	private boolean isPlayerTurn;
	private Random rand;
	private String difficulty;

	private String nextLastMove;
	private String lastMove;

	/**
	 * The constructor for the Nim class.
	 * Sets up the game and starts a new game.
	 */
	public Nim()
	{
		rand = new Random();

		//set up game panel
		setupGame();

		//start new game
		reset();
	}

	/**
	 * A method to get the content panel that holds the game.
	 * @return JPanel component that holds the game
	 */
	public Component getContent()
	{
		return gamePanel;
	}

	/**
	 * Sets up the frame the game is played in.
	 */
	private void setupGame()
	{
		//create and set up GUI components
		gamePanel = new JPanel(new GridLayout(0,1));
		buttonPanel = new JPanel(new GridLayout(1,3));
		difficultyPanel = new JPanel(new GridLayout(1,3));

		instrLabel = new JLabel("<html> Welcome to Nim!<br>"
				+ "You and the computer will take turns<br>"
				+ "taking between 1-3 stones from the pile.<br>"
				+ "Whoever takes the last stone loses!</html>");
		stonesLabel = new JLabel("");
		movesLabel = new JLabel("");
		
		buttonLabel = new JLabel("Pick a number of stones to take:");
		button1 = new JButton("1");
		button2 = new JButton("2");
		button3 = new JButton("3");
		
		difficulty = "Normal";
		difficultyLabel = new JLabel("Current difficulty level is: " + difficulty);
		buttonEasy = new JButton("Easy");
		buttonNormal = new JButton("Normal");
		buttonHard = new JButton("Hard");
		
		newGameButton = new JButton("New Game");

		//attach listeners to buttons
		button1.addActionListener(button1Listener());
		button2.addActionListener(button2Listener());
		button3.addActionListener(button3Listener());
		
		buttonEasy.addActionListener(buttonEasyListener());
		buttonNormal.addActionListener(buttonNormalListener());
		buttonHard.addActionListener(buttonHardListener());
		
		newGameButton.addActionListener(newGame());

		//add buttons to button panel
		buttonPanel.add(button1);
		buttonPanel.add(button2);
		buttonPanel.add(button3);
		
		//add dificulty buttons to a button panel
		difficultyPanel.add(buttonEasy);
		difficultyPanel.add(buttonNormal);
		difficultyPanel.add(buttonHard);

		//add components to game panel
		gamePanel.add(instrLabel);
		gamePanel.add(stonesLabel);
		gamePanel.add(movesLabel);
		gamePanel.add(buttonLabel);
		gamePanel.add(buttonPanel);
		gamePanel.add(difficultyLabel);
		gamePanel.add(difficultyPanel);
		gamePanel.add(newGameButton);
	}

	/**
	 * Resets the game, returning all variables to their initial state.
	 */
	private void reset()
	{
		//set game variables to initial values
		//(what they should look like at the start of the game)
		stones = rand.nextInt(5) + 21;
		gameOver = false;
		isPlayerTurn = true;

		nextLastMove = "";
		lastMove = "";

		stonesLabel.setText(stoneString(stones));
		updateMoves("You make the first move.");
	}

	/**
	 * Updates the moves played.
	 * The last move is moved to the second to last move
	 * and the last move is set equal to the newMove parameter.
	 * @param newMove
	 */
	private void updateMoves(String newMove)
	{
		//set second to last move equal to last move
		nextLastMove = lastMove;

		//set last move equal to the newMove parameter
		lastMove = newMove;

		//display updated moves
		movesLabel.setText("<html>" + nextLastMove + "<br>" + lastMove + "</html>");
	}

	/**
	 * Generates and returns a string telling the number of remaining stones
	 * and an ascii representation of the pile of remaining stones.
	 * @param n
	 * @return string representing the number of remaining stones
	 */
	private String stoneString(int n)
	{
		//add number of remaining stones to display string
		String display = "Remaining stones: " + n + "<br>";

		//generate ascii art representing the pile of remaining stones
		for(int i = 1; i <= n; i++)
		{
			display += "@";

			if((i%5) == 0)
			{
				display += "<br>";
			}
		}

		//add html tags to allow panel to display multiple lines
		display = "<html> " + display + " </html>";
		return display;
	}

	/**
	 * Subtracts the number of stones n from the total pile of stones.
	 * @param n
	 * @return false if game is over (number of stones == 0), true otherwise
	 */
	private boolean takeStones(int n)
	{
		stones = stones - n;

		//returns a boolean: true ~ game over; false ~ game is still going
		if(stones <= 0)
		{
			//game over
			stones = 0;

			//update stone pile
			stonesLabel.setText(stoneString(stones));
			return true;
		}
		else
		{
			//game still going
			//update stone pile
			stonesLabel.setText(stoneString(stones));
			return false;
		}
	}
	
	/**
	 * Delays the game by the ammount of time passed as a paremeter (in milliseconds)
	 * @param time
	 */
	private void delay(int time)
	{
		//delay for the length of time parameter in milliseconds
		try
		{
			TimeUnit.MILLISECONDS.sleep(time);
			//Thread.sleep(time);
		}
		catch (InterruptedException e)
		{
			//Handle exception
		}
	}

	/**
	 * Executes a single round of the game (when button is pressed to call this function).
	 * playerChoice is the number of stones the player intends to take this round.
	 * @param playerChoice
	 */
	private void playRound(int playerChoice)
	{
		//execute player's turn if game is not over
		if(!gameOver)
		{
			if(isPlayerTurn)
			{
				playersTurn(playerChoice);
				
				//execute computer's turn if game is not over
				if(!gameOver)
				{
					computersTurn();
				}
			}
		}
	}
	
	/**
	 * Executes player's turn. Takes stones from the pile, updates moves, and determines if game is over.
	 * @param playerStones
	 */
	private void playersTurn(int playerStones)
	{
		//player's turn to take stones
		gameOver = takeStones(playerStones);
		updateMoves("You took " + playerStones + " stones.");

		//add a delay between turns
		//delay(1000);

		//check if player lost this turn
		if(gameOver)
		{
			updateMoves("You took the last stone.");
			updateMoves("You Lose...");
		}
		
		isPlayerTurn = false;
	}
	
	/**
	 * Executes computer's turn. Takes stones from the pile, updates moves, and determines if game is over.
	 * Determines number of stones to take based on difficulty level
	 */
	private void computersTurn()
	{
		//decide number of stones the computer will take based on difficulty
		int compStones = 0;
		
		if(difficulty.equals("Easy"))
		{
			//computer only takes 1 stone
			compStones = 1;
		}
		else if(difficulty.equals("Normal"))
		{
			//computer takes random number of stones (1-3)
			compStones = rand.nextInt(3) + 1;
		}
		else if(difficulty.equals("Hard"))
		{
			//computer will take the optimum number of stones to put the player at a disadvantage.
			//The turn player is in a "losing state" if the number of stones in the pile at the start of their turn is n, where
			// n = (z*4)+1, for z = 0,1,2,... 
			switch((stones - 1) % 4)
			{
			case 1:
				compStones = 1;
				break;
			case 2:
				compStones = 2;
				break;
			case 3:
				compStones = 3;
				break;
			default:
				//computer takes random number of stones (1-3)
				compStones = rand.nextInt(3) + 1;
				break;
			}
		}
		else
		{
			//if difficulty is ever NOT one of the above,
			//set difficulty to Easy and call computersTurn again
			difficulty = "Easy";
			difficultyLabel.setText("Current difficulty level is: " + difficulty);
			computersTurn();
		}
		
		gameOver = takeStones(compStones);
		updateMoves("The computer took " + compStones + " stones.");

		//add a delay between turns
		//delay(1000);

		//check if computer just lost
		if(gameOver)
		{	
			updateMoves("The computer took the last stone.");
			updateMoves("You Win!");
		}
		
		isPlayerTurn = true;
	}
	
/* Older method for executing player's turn and then computer's turn (called from button listeners)
	/**
	 * Execute player's turn and then follow with the computer's turn if the game is still going.
	 * @param stonesTaken
	 */
/*	private void playerTurn(int stonesTaken)
	{
		//only active if game is not over and it's the player's turn
		if(!gameOver && isPlayerTurn)
		{
			//player's turn to take stones
			gameOver = takeStones(stonesTaken);
			updateMoves("You took " + stonesTaken + " stones.");

			//add a delay between turns
			//delay(1000);

			//check if player lost this turn
			if(gameOver)
			{
				isPlayerTurn = false;
				updateMoves("You took the last stone.");
				updateMoves("You Lose...");
			}
			else
			{
				//computer's turn
				isPlayerTurn = false;

				//computer takes random number of stones (1-3)
				int compTake = rand.nextInt(3) + 1;
				gameOver = takeStones(compTake);
				updateMoves("The computer took " + compTake + " stones.");

				//add a delay between turns
				//delay(1000);

				//check if computer just lost
				if(gameOver)
				{
					isPlayerTurn = false;
					updateMoves("The computer took the last stone.");
					updateMoves("You Win!");
				}
				else
				{
					//if computer did not lose this turn, game continues
					isPlayerTurn = true;
				}
			}
		}
	}
*/	
	
	/**
	 * Sets up the action listener for button1
	 * @return action listener for button1
	 */
	private ActionListener button1Listener()
	{
		ActionListener listener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				playRound(1);
			}
		};

		return listener;
	}

	/**
	 * Sets up the action listener for button2
	 * @return action listener for button2
	 */
	private ActionListener button2Listener()
	{
		ActionListener listener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				playRound(2);
			}
		};

		return listener;
	}

	/**
	 * Sets up the action listener for button3
	 * @return action listener for button3
	 */
	private ActionListener button3Listener()
	{
		ActionListener listener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				playRound(3);
			}
		};

		return listener;
	}
	
	/**
	 * Sets up the action listener for buttonEasy
	 * @return action listener for buttonEasy
	 */
	private ActionListener buttonEasyListener()
	{
		ActionListener listener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//change difficulty variable and display change in label
				difficulty = "Easy";
				difficultyLabel.setText("Current difficulty level is: " + difficulty);
			}
		};

		return listener;
	}
	
	/**
	 * Sets up the action listener for buttonNormal
	 * @return action listener for buttonNormal
	 */
	private ActionListener buttonNormalListener()
	{
		ActionListener listener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//change difficulty variable and display change in label
				difficulty = "Normal";
				difficultyLabel.setText("Current difficulty level is: " + difficulty);
			}
		};

		return listener;
	}
	
	/**
	 * Sets up the action listener for buttonHard
	 * @return action listener for buttonHard
	 */
	private ActionListener buttonHardListener()
	{
		ActionListener listener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//change difficulty variable and display change in label
				difficulty = "Hard";
				difficultyLabel.setText("Current difficulty level is: " + difficulty);
			}
		};

		return listener;
	}

	/**
	 * Sets up the action listener for new game button
	 * @return action listener for new game button
	 */
	private ActionListener newGame()
	{
		ActionListener listener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				reset();
			}
		};

		return listener;
	}
}
