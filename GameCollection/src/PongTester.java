
public class PongTester {
	//lists of methods
	//updatesquish
	//checkscore
	//reset
	//isgameover
	//newgame
	/**
	 * PongTester()
	 * @post constructs a new pongTester instance
	 */
	public PongTester(){
		Pong3d1pControl game = new Pong3d1pControl();
		test(game);
	}
	/**
	 * test(Pong3d1pControl)
	 * @param control the Pong3d1pControl to be referenced in the testing
	 * @post tests the Pong3d game
	 * Runs a test on the pong game testing for logical accuracies
	 */
	public void test(Pong3d1pControl control){
		boolean overAll = true;
		if(test1(control)){
			System.out.println("Test 1: PASS");
		} else {
			System.out.println("Test 1: FAIL");
			overAll = false;
		}
		System.out.println("");
		if(test2(control)){
			System.out.println("Test 2: PASS");
		} else {
			System.out.println("Test 2: FAIL");
			overAll = false;
		}
		System.out.println("");
		if(test3(control)){
			System.out.println("Test 3: PASS");
		} else {
			System.out.println("Test 3: FAIL");
			overAll = false;
		}
		System.out.println("");
		if(test4(control)){
			System.out.println("Test 4: PASS");
		} else {
			System.out.println("Test 4: FAIL");
			overAll = false;
		}
		System.out.println("");
		if(test5(control)){
			System.out.println("Test 5: PASS");
		} else {
			System.out.println("Test 5: FAIL");
			overAll = false;
		}
		System.out.println("");
		if(test6(control)){
			System.out.println("Test 6: PASS");
		} else {
			System.out.println("Test 6: FAIL");
			overAll = false;
		}
		System.out.println("");
		if(test7(control)){
			System.out.println("Test 7: PASS");
		} else {
			System.out.println("Test 7: FAIL");
			overAll = false;
		}
		System.out.println("");
		if(test8(control)){
			System.out.println("Test 8: PASS");
		} else {
			System.out.println("Test 8: FAIL");
			overAll = false;
		}
		System.out.println("");
		if(test9(control)){
			System.out.println("Test 9: PASS");
		} else {
			System.out.println("Test 9: FAIL");
			overAll = false;
		}
		System.out.println("");
		if(test10(control)){
			System.out.println("Test 10: PASS");
		} else {
			System.out.println("Test 10: FAIL");
			overAll = false;
		}
		System.out.println("");
		if(test11(control)){
			System.out.println("Test 11: PASS");
		} else {
			System.out.println("Test 11: FAIL");
			overAll = false;
		}
		System.out.println("");
		if(test12(control)){
			System.out.println("Test 12: PASS");
		} else {
			System.out.println("Test 12: FAIL");
			overAll = false;
		}
		System.out.println("");
		if(test13(control)){
			System.out.println("Test 13: PASS");
		} else {
			System.out.println("Test 13: FAIL");
			overAll = false;
		}
		System.out.println("");
		if(test14(control)){
			System.out.println("Test 14: PASS");
		} else {
			System.out.println("Test 14: FAIL");
			overAll = false;
		}
		System.out.println("");
		if(test15(control)){
			System.out.println("Test 15: PASS");
		} else {
			System.out.println("Test 15: FAIL");
			overAll = false;
		}
		System.out.println("");
		if(test16(control)){
			System.out.println("Test 16: PASS");
		} else {
			System.out.println("Test 16: FAIL");
			overAll = false;
		}
		
		System.out.println("");
		if(overAll){
			System.out.println("Overall test results: PASS");
		} else {
			System.out.println("Overall test results: FAIL");
		}
		System.out.println("");
		System.out.println("");
		System.out.println("Testing complete. Go back to pong to run test again or to play a game.");
		System.out.println("");

	}

	//test 1
	//ball is set up where opponent scores
	//check opponent score
	/**
	 * test1(Pong3d1pControl control)
	 * @param control The Pong3d1pControl being refered to for this game
	 * @return true for pass, false for fail
	 * Tests logic where computer should score
	 */
	public boolean test1(Pong3d1pControl control){
		System.out.println("Running test 1...");
		System.out.println(" Description: ");
		System.out.println("  Testing when ball is positioned to where computer scores");
		control.depth = 2.1f;
		System.out.println("   Computer Score before: " + control.computerScore);
		control.checkScore();
		System.out.println("   Anicipated Computer Score: 1");
		System.out.println("   Actual Computer Score: " + control.computerScore);
		return(control.computerScore == 1);
	}
	//test 2
	//ball is set up where player scores
	//check player score
	/**
	 * test2(Pong3d1pControl control)
	 * @param control The Pong3d1pControl being refered to for this game
	 * @return true for pass, false for fail
	 * Tests logic where human should score
	 */
	public boolean test2(Pong3d1pControl control){
		System.out.println("Running test 2...");
		System.out.println(" Description: ");
		System.out.println("  Testing when ball is positioned to where human scores");
		control.depth = -3.1f;
		System.out.println("   Human Score before: " + control.humanScore);
		control.checkScore();
		System.out.println("   Anicipated Human Score: 1");
		System.out.println("   Actual Human Score: " + control.humanScore);
		return(control.humanScore == 1);
	}
	//test3
	//ball is up against wall
	//check to see if direction changes
	/**
	 * test3(Pong3d1pControl control)
	 * @param control The Pong3d1pControl being refered to for this game
	 * @return true for pass, false for fail
	 * Tests logic where ball should switch direction
	 */
	public boolean test3(Pong3d1pControl control){
		System.out.println("Running test 3...");
		System.out.println(" Description: ");
		System.out.println("  Place ball up against right wall, update, check if direction switches");
		control.xloc = control.xMAX;
		float direction = control.dir;
		System.out.println("   Ball direction before: " + direction);
		control.updateSquish();
		float dirAft = control.dir;
		System.out.println("   Ball direction after: " + dirAft);

		return (!(direction == dirAft));
	}
	//test4
	//ball is up against other wall
	//time delay, check to see if ball is off wall
	/**
	 * test4(Pong3d1pControl control)
	 * @param control The Pong3d1pControl being refered to for this game
	 * @return true for pass, false for fail
	 * Tests logic where ball should switch direction (other wall)
	 */
	public boolean test4(Pong3d1pControl control){
		System.out.println("Running test 4...");
		System.out.println(" Description: ");
		System.out.println("  Place ball up against left wall, update, check if direction switches");
		control.xloc = -control.xMAX;
		float direction = control.dir;
		System.out.println("   Ball direction before: " + direction);
		control.updateSquish();
		float dirAft = control.dir;
		System.out.println("   Ball direction after: " + dirAft);

		return (!(direction == dirAft));
	}
	//test5
	//reset
	//check to see if positioning have been adjusted
	/**
	 * test5(Pong3d1pControl control)
	 * @param control The Pong3d1pControl being refered to for this game
	 * @return true for pass, false for fail
	 * Tests logic where ball position should reset to beginning
	 */
	public boolean test5(Pong3d1pControl control){
		boolean allCorrect = true;
		System.out.println("Running test 5...");
		System.out.println(" Description: ");
		System.out.println("  Put in arbitrary locations. Call reset and see if positioning  is adjusted");
		System.out.println("");
		//arbitrary locations
		control.xloc = 1.0f;
		control.depth = 0.5f;
		control.squish = 0.2;
		control.hxloc = 0.2f;
		control.computerX = 0.2f;
		control.reset();
		System.out.println("   Ball x location before: 1.0");
		System.out.println("   Anticipated ball x location: 0.0");
		System.out.println("   Actual ball x location: " + control.xloc);
		if(control.xloc != 0.0f){
			allCorrect = false;
		}
		System.out.println("   Ball z location before: 0.5");
		System.out.println("   Anticipated ball z location: 0.0");
		System.out.println("   Actual ball z location: " + control.depth);
		if(control.depth != 0.0f){
			allCorrect = false;
		}
		System.out.println("   Squish amount before: 0.2");
		System.out.println("   Anticipated squish amount: 1.0");
		System.out.println("   Actual squish amount: " + control.squish);
		if(control.squish != 1.0){
			allCorrect = false;
		}
		System.out.println("   Human x location before: 0.2");
		System.out.println("   Anticipated human x location: 0.0");
		System.out.println("   Actual human x location: " + control.hxloc);
		if(control.hxloc != 0.0f){
			allCorrect = false;
		}
		System.out.println("   Computer x location before: 0.2");
		System.out.println("   Anticpated computer x location: 0.0");
		System.out.println("   Actual computer x location: " + control.computerX);
		if(control.computerX != 0.0f){
			allCorrect = false;
		}
		System.out.println();

		return allCorrect;
	}
	//test6
	//put ball out of bounds, update,  see if ball stays in bounds
	/**
	 * test6(Pong3d1pControl control)
	 * @param control The Pong3d1pControl being refered to for this game
	 * @return true for pass, false for fail
	 * Tests logic where ball is placed out of x axis bounds and returns to max x axis
	 */
	public boolean test6(Pong3d1pControl control){
		System.out.println("Running test 6...");
		System.out.println(" Description: ");
		System.out.println("  Place ball out of x axis bounds, see if ball returns to bounds");
		//arbitrary x location
		control.xloc = 0.5f;
		System.out.println("   Ball x location before: 0.5");
		control.updateSquish();
		System.out.println("   Anticipated ball location: 0.45");
		System.out.println("   Actual ball location: " + control.xloc);

		return (control.xloc == 0.45f);

	}
	//test7
	//see if game discontinues after a score of 5
	/**
	 * test7(Pong3d1pControl control)
	 * @param control The Pong3d1pControl being refered to for this game
	 * @return true for pass, false for fail
	 * Tests logic where score of 5 ends the game
	 */
	public boolean test7(Pong3d1pControl control){
		System.out.println("Running test 7...");
		System.out.println(" Description: ");
		System.out.println("  Put score at humanScore = 5, check if game is over");
		control.humanScore = 5;
		control.isGameOver();
		System.out.println("   Anticipated gameGoing truth value: false");
		System.out.println("   Actual gameGoing truth value: " + control.gameGoing);
		return (!control.gameGoing);

	}
	//test8
	//check newGame method
	/**
	 * test8(Pong3d1pControl control)
	 * @param control The Pong3d1pControl being refered to for this game
	 * @return true for pass, false for fail
	 * Tests logic where paddles and ball return to the newGame intended position
	 */
	public boolean test8(Pong3d1pControl control){
		boolean allCorrect = true;
		System.out.println("Running test 8...");
		System.out.println(" Description: ");
		System.out.println(" Put in arbitrary locations. Call newGame and see if positioning is adjusted to beginning values");
		System.out.println("");
		//arbitrary locations
		control.xloc = 1.0f;
		control.depth = 0.5f;
		control.squish = 0.2;
		control.hxloc = 0.2f;
		control.computerX = 0.2f;
		control.newGame();
		System.out.println("   Ball x location before: 1.0");
		System.out.println("   Anticipated ball x location: 0.0");
		System.out.println("   Actual ball x location: " + control.xloc);
		if(control.xloc != 0.0f){
			allCorrect = false;
		}
		System.out.println("   Ball z location before: 0.5");
		System.out.println("   Anticipated ball z location: 0.0");
		System.out.println("   Actual ball z location: " + control.depth);
		if(control.depth != 0.0f){
			allCorrect = false;
		}
		System.out.println("   Squish amount before: 0.2");
		System.out.println("   Anticipated squish amount: 1.0");
		System.out.println("   Actual squish amount: " + control.squish);
		if(control.squish != 1.0){
			allCorrect = false;
		}
		System.out.println("   Human x location before: 0.2");
		System.out.println("   Anticipated human x location: 0.0");
		System.out.println("   Actual human x location: " + control.hxloc);
		if(control.hxloc != 0.0f){
			allCorrect = false;
		}
		System.out.println("   Computer x location before: 0.2");
		System.out.println("   Anticpated computer x location: 0.0");
		System.out.println("   Actual computer x location: " + control.computerX);
		if(control.computerX != 0.0f){
			allCorrect = false;
		}
		System.out.println();

		return allCorrect;
	}
	//test9
	//put ball up against wall
	//check squish
	/**
	 * test9(Pong3d1pControl control)
	 * @param control The Pong3d1pControl being refered to for this game
	 * @return true for pass, false for fail
	 * Tests logic where ball is positioned up against wall and squishes
	 */
	public boolean test9(Pong3d1pControl control){
		System.out.println("Running test 9...");
		System.out.println(" Description: ");
		System.out.println("  Position ball up against wall and check to see it squishes");
		control.xloc = control.xMAX;
		System.out.println("   Squish before: " + control.squish);
		control.updateSquish();
		System.out.println("   Anticipated squish: 0.5");
		System.out.println("   Actual squish: " + control.squish);


		return (control.squish == 0.5f);
	}
	/**
	 * test10(Pong3d1pControl control)
	 * @param control The Pong3d1pControl being refered to for this game
	 * @return true for pass, false for fail
	 * Tests logic where computer increaseDifficulty should increase
	 */
	public boolean test10(Pong3d1pControl control){
		System.out.println("Running test 10...");
		System.out.println(" Description: ");
		System.out.println("  Set computer's  execution time to a mod-able integer,");
		System.out.println("  see if increaseDifficulty increases");
		control.cLogic.executionTime = 10000;
		float before = control.cLogic.increaseDifficulty;
		System.out.println("   increaseDifficulty before: " +before);
		control.cLogic.move(control.bCoords, control.cCoords);
		System.out.println("   Anticipated increaseDifficulty: " + (before + .0005f) );
		float after = control.cLogic.increaseDifficulty;
		System.out.println("   increaseDifficulty after: "  +after);

		return(after == (before + .0005f));

	}
	/**
	 * test11(Pong3d1pControl control)
	 * @param control The Pong3d1pControl being refered to for this game
	 * @return true for pass, false for fail
	 * Tests logic where computer's increaseDifficulty and executionTime reset to zero after newGame is called
	 */
	public boolean test11(Pong3d1pControl control){
		System.out.println("Running test 11...");
		System.out.println(" Description: ");
		System.out.println("  Set computer's increaseDifficulty and execution time to");
		System.out.println("  arbitrary numbers, call newGame, see if they reset");
		control.cLogic.executionTime = 10023;
		control.cLogic.increaseDifficulty = 0.005f;
		System.out.println("   executionTime before: 10023");
		System.out.println("   increaseDifficulty before: 0.005");
		System.out.println("   Anticipated executionTime: 0");
		System.out.println("   Anticipated increaseDifficulty: 0.0");
		control.newGame();
		int afterET = control.cLogic.executionTime;
		float afterID = control.cLogic.increaseDifficulty;
		System.out.println("   executionTime after: " + afterET);
		System.out.println("   increaseDifficulty after: " + afterID );

		return ((afterET == 0) && (afterID == 0.0f));

	}
	public boolean test12(Pong3d1pControl control){
		System.out.println("Running test 12...");
		System.out.println(" Description: ");
		System.out.println("  Set computer to winner, check message at top.");
		control.computerScore = 5;
		control.isGameOver();
		System.out.println("   Anticipated string: you lose... (-_-)");
		System.out.println("   Actual string: " + control.winner.getText());
		return(control.winner.getText() == "you lose... (-_-)");
	}
	public boolean test13(Pong3d1pControl control){
		System.out.println("Running test 13...");
		System.out.println(" Description: ");
		System.out.println("  Set human to winner, check message at top.");
		control.humanScore = 5;
		control.isGameOver();
		System.out.println("   Anticipated string: YOU WIN!!! d^_^b");
		System.out.println("   Actual string: " + control.winner.getText());
		return(control.winner.getText() == "YOU WIN!!! d^_^b");
	}
	//test coordinates
	public boolean test14(Pong3d1pControl control){
		System.out.println("Running test 14...");
		System.out.println(" Description: ");
		System.out.println("  Set ball coordinates, test to see if coordinates were set properly.");
		control.bCoords.setCoordinates(1.0f, 2.3f, 4.7f);
		System.out.println("   Setting coordinates to: 1.0, 2.3, 4.7");
		System.out.println("   Ball x coordinate: " + control.bCoords.x);
		System.out.println("   Ball y coordinate: " + control.bCoords.y);
		System.out.println("   Ball z coordinate: " + control.bCoords.z);
		return(control.bCoords.x == 1.0f && control.bCoords.y == 2.3f && control.bCoords.z == 4.7f);
	}
	//test coordinates
	public boolean test15(Pong3d1pControl control){
		System.out.println("Running test 15...");
		System.out.println(" Description: ");
		System.out.println("  Set human coordinates, test to see if coordinates were set properly.");
		control.hCoords.setCoordinates(1.0f, 2.3f, 4.7f);
		System.out.println("   Setting coordinates to: 1.0, 2.3, 4.7");
		System.out.println("   Human x coordinate: " + control.hCoords.x);
		System.out.println("   Human y coordinate: " + control.hCoords.y);
		System.out.println("   Human z coordinate: " + control.hCoords.z);
		return(control.hCoords.x == 1.0f && control.hCoords.y == 2.3f && control.hCoords.z == 4.7f);
	}
	//test coordinates
	public boolean test16(Pong3d1pControl control){
		System.out.println("Running test 16...");
		System.out.println(" Description: ");
		System.out.println("  Set computer coordinates, test to see if coordinates were set properly.");
		control.cCoords.setCoordinates(1.0f, 2.3f, 4.7f);
		System.out.println("   Setting coordinates to: 1.0, 2.3, 4.7");
		System.out.println("   Computer x coordinate: " + control.cCoords.x);
		System.out.println("   Computer y coordinate: " + control.cCoords.y);
		System.out.println("   Computer z coordinate: " + control.cCoords.z);
		return(control.cCoords.x == 1.0f && control.cCoords.y == 2.3f && control.cCoords.z == 4.7f);
	}
	//test ball logic
	public boolean test17(Pong3d1pControl control){
		return true;
	}
	//test ball reset
	public boolean test18(Pong3d1pControl control){
		boolean correct = true;
		System.out.println("Running test 18...");
		System.out.println(" Description: ");
		System.out.println("  Set ball bounce coordinates randomly, call reset, check if they get reset to default values");
		for(int i = 0; i < 5; i++){
			control.bLogic.bBounce[i] = 2.5f;
		}
		System.out.println("   All bBounce components set to 2.5");
		System.out.println("   Anticipated 1.0, 1.0, 1.0, 1.0, 0.0");
		control.bLogic.reset();
		for(int i =0; i<5; i++){
			System.out.println("   bLogic at "+ i+ " = "+ control.bLogic.bBounce[i]);
			if(i!=4 && control.bLogic.bBounce[i]!= 1.0f){
				correct = false;
			}
		}
		if(control.bLogic.bBounce[4] != 0.0f){
			correct = false;
		}
	
		return correct;
	}

}
