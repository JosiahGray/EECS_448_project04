/**
 * A class that tests a Nim game.
 * @author Josiah Gray
 *
 */
public class TestNim
{
	/**
	 * main method to run tests on the Nim class
	 * @param args
	 */
	public static void main(String[] args)
	{
		int tStones = 0;
		String tDifficulty = "";
		boolean tGameOver = false;
		String message = "";
		
		//start testing
		System.out.println("****************************");
		System.out.println("Starting tests for Nim game:");
		System.out.println("****************************");
		System.out.println("");
		
		//create new nim game
		Nim testGame = new Nim();
		System.out.println("Test game successfully created and initialized.");
		System.out.println("");
		
		//test initial conditions
		System.out.println("****************************");
		System.out.println("Testing initial game conditions:");
		System.out.println("****************************");
		System.out.println("");
		
		tStones = testGame.getStones();
		tDifficulty = testGame.getDifficulty();
		tGameOver = testGame.isGameOver();
		
		System.out.println("Expected values:");
		System.out.println("number of stones: random number between 21 and 25");
		System.out.println("difficulty: Normal");
		System.out.println("isGameOver: false");
		System.out.println("");
		
		System.out.println("Actual values:");
		System.out.println("number of stones: " + tStones);
		System.out.println("difficulty: " + tDifficulty);
		System.out.println("isGameOver: " + tGameOver);
		System.out.println("");
		
		System.out.println("Results:");
		System.out.print("number of stones: ");
		if(tStones >= 21 && tStones <= 25)
		{
			System.out.println("PASS");
		}
		else
		{
			System.out.println("FAIL");
		}
		System.out.print("difficulty: ");
		if(tDifficulty.equals("Normal"))
		{
			System.out.println("PASS");
		}
		else
		{
			System.out.println("FAIL");
		}
		System.out.print("isGameOver: ");
		if(tGameOver == false)
		{
			System.out.println("PASS");
		}
		else
		{
			System.out.println("FAIL");
		}
		System.out.println("");
	}
}
