/**
 * A class that tests a Nim game.
 * @author Josiah Gray
 *
 */
public class TestNim
{
	/**
	 * Main method to run tests on the Nim class.
	 * These tests are meant to only test the underlying mechanics of the game,
	 * any visuals must be judged by human testers during runtime.
	 * @param args
	 */
	public TestNim() {
		int tStones = 0;
		String tDifficulty = "";
		boolean tGameOver = false;
		String message = "";

		////////////////////////////////////////
		//start testing
		System.out.println("****************************");
		System.out.println("Starting tests for Nim game:");
		System.out.println("****************************");
		System.out.println("");

		////////////////////////////////////////
		//create new nim game
		Nim testGame = new Nim();
		System.out.println("Test game successfully created and initialized.");
		System.out.println("");

		////////////////////////////////////////
		//test initial conditions
		System.out.println("********************************");
		System.out.println("Testing initial game conditions (and getter methods):");
		System.out.println("********************************");
		System.out.println("");

		tStones = testGame.getStones();
		tDifficulty = testGame.getDifficulty();
		tGameOver = testGame.isGameOver();

		System.out.println("Expected values:");
		System.out.println("number of stones: random number between 21 and 25 (inclusive)");
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

		////////////////////////////////////////
		//test setStones and getStones methods
		System.out.println("****************************************");
		System.out.println("Testing setStones and getStones methods:");
		System.out.println("****************************************");
		System.out.println("");

		////////////////////////////////////////
		//test set function for positive integer
		System.out.println("Setting number of stones to 30.");
		testGame.setStones(30);
		System.out.println("Retrieving number of stones.");
		System.out.println("");

		tStones = testGame.getStones();
		tDifficulty = testGame.getDifficulty();
		tGameOver = testGame.isGameOver();

		System.out.println("Expected values:");
		System.out.println("number of stones: 30");
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
		if(tStones == 30)
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

		////////////////////////////////////////
		//test set function for negative integer
		System.out.println("Setting number of stones to -20.");
		System.out.println("(should actually set stones to 0, since negative numbers are not allowed)");
		testGame.setStones(-20);
		System.out.println("Retrieving number of stones.");
		System.out.println("");

		tStones = testGame.getStones();
		tDifficulty = testGame.getDifficulty();
		tGameOver = testGame.isGameOver();

		System.out.println("Expected values:");
		System.out.println("number of stones: 0");
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
		if(tStones == 0)
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

		////////////////////////////////////////
		//test setDifficulty method
		System.out.println("****************************************");
		System.out.println("Testing setDifficulty method:");
		System.out.println("****************************************");
		System.out.println("");

		////////////////////////////////////////
		//test easy setting
		System.out.println("Setting difficulty to Easy.");
		testGame.setDifficulty("Easy");
		System.out.println("Retrieving difficulty.");
		System.out.println("");

		tDifficulty = testGame.getDifficulty();

		System.out.println("Expected values:");
		System.out.println("difficulty: Easy");
		System.out.println("");

		System.out.println("Actual values:");
		System.out.println("difficulty: " + tDifficulty);
		System.out.println("");

		System.out.println("Results:");
		System.out.print("difficulty: ");
		if(tDifficulty.equals("Easy"))
		{
			System.out.println("PASS");
		}
		else
		{
			System.out.println("FAIL");
		}
		System.out.println("");

		////////////////////////////////////////
		//test normal setting
		System.out.println("Setting difficulty to Normal.");
		testGame.setDifficulty("Normal");
		System.out.println("Retrieving difficulty.");
		System.out.println("");

		tDifficulty = testGame.getDifficulty();

		System.out.println("Expected values:");
		System.out.println("difficulty: Normal");
		System.out.println("");

		System.out.println("Actual values:");
		System.out.println("difficulty: " + tDifficulty);
		System.out.println("");

		System.out.println("Results:");
		System.out.print("difficulty: ");
		if(tDifficulty.equals("Normal"))
		{
			System.out.println("PASS");
		}
		else
		{
			System.out.println("FAIL");
		}
		System.out.println("");

		////////////////////////////////////////
		//test hard setting
		System.out.println("Setting difficulty to Hard.");
		testGame.setDifficulty("Hard");
		System.out.println("Retrieving difficulty.");
		System.out.println("");

		tDifficulty = testGame.getDifficulty();

		System.out.println("Expected values:");
		System.out.println("difficulty: Hard");
		System.out.println("");

		System.out.println("Actual values:");
		System.out.println("difficulty: " + tDifficulty);
		System.out.println("");

		System.out.println("Results:");
		System.out.print("difficulty: ");
		if(tDifficulty.equals("Hard"))
		{
			System.out.println("PASS");
		}
		else
		{
			System.out.println("FAIL");
		}
		System.out.println("");

		////////////////////////////////////////
		//test invalid setting
		System.out.println("Setting difficulty to Potato.");
		System.out.println("(should instead set invalid options to Easy)");
		testGame.setDifficulty("Potato");
		System.out.println("Retrieving difficulty.");
		System.out.println("");

		tDifficulty = testGame.getDifficulty();

		System.out.println("Expected values:");
		System.out.println("difficulty: Easy");
		System.out.println("");

		System.out.println("Actual values:");
		System.out.println("difficulty: " + tDifficulty);
		System.out.println("");

		System.out.println("Results:");
		System.out.print("difficulty: ");
		if(tDifficulty.equals("Easy"))
		{
			System.out.println("PASS");
		}
		else
		{
			System.out.println("FAIL");
		}
		System.out.println("");

		////////////////////////////////////////
		//test reset method
		System.out.println("****************************************");
		System.out.println("Testing reset method:");
		System.out.println("****************************************");
		System.out.println("");

		System.out.println("Resetting game.");
		testGame.reset();
		System.out.println("Game reset.");
		System.out.println("");

		tStones = testGame.getStones();
		tDifficulty = testGame.getDifficulty();
		tGameOver = testGame.isGameOver();

		System.out.println("Expected values:");
		System.out.println("number of stones: random number between 21 and 25 (inclusive)");
		System.out.println("difficulty: Easy (difficulty should not change when resetting game)");
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
		if(tDifficulty.equals("Easy"))
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

		////////////////////////////////////////
		//test takeStones method
		System.out.println("****************************************");
		System.out.println("Testing takeStones method:");
		System.out.println("****************************************");
		System.out.println("");

		//leave a few stones remaining
		System.out.println("Setting number of stones to 30.");
		testGame.setStones(30);
		System.out.println("Taking 5 stones.");
		testGame.takeStones(5);
		System.out.println("");

		tStones = testGame.getStones();
		tDifficulty = testGame.getDifficulty();
		tGameOver = testGame.isGameOver();

		System.out.println("Expected values:");
		System.out.println("number of stones: 25");
		System.out.println("difficulty: Easy");
		System.out.println("isGameOver: false");
		System.out.println("");

		System.out.println("Actual values:");
		System.out.println("number of stones: " + tStones);
		System.out.println("difficulty: " + tDifficulty);
		System.out.println("isGameOver: " + tGameOver);
		System.out.println("");

		System.out.println("Results:");
		System.out.print("number of stones: ");
		if(tStones == 25)
		{
			System.out.println("PASS");
		}
		else
		{
			System.out.println("FAIL");
		}
		System.out.print("difficulty: ");
		if(tDifficulty.equals("Easy"))
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

		//try taking more stones than there are
		System.out.println("Setting number of stones to 8.");
		testGame.setStones(8);
		System.out.println("Taking 10 stones.");
		testGame.takeStones(10);
		System.out.println("");

		tStones = testGame.getStones();
		tDifficulty = testGame.getDifficulty();
		tGameOver = testGame.isGameOver();

		System.out.println("Expected values:");
		System.out.println("number of stones: 0");
		System.out.println("difficulty: Easy");
		System.out.println("isGameOver: true");
		System.out.println("");

		System.out.println("Actual values:");
		System.out.println("number of stones: " + tStones);
		System.out.println("difficulty: " + tDifficulty);
		System.out.println("isGameOver: " + tGameOver);
		System.out.println("");

		System.out.println("Results:");
		System.out.print("number of stones: ");
		if(tStones == 0)
		{
			System.out.println("PASS");
		}
		else
		{
			System.out.println("FAIL");
		}
		System.out.print("difficulty: ");
		if(tDifficulty.equals("Easy"))
		{
			System.out.println("PASS");
		}
		else
		{
			System.out.println("FAIL");
		}
		System.out.print("isGameOver: ");
		if(tGameOver == true)
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
