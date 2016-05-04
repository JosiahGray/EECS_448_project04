
public class PongTester {
	//lists of methods 
	//updatesquish
	//checkscore
	//updateball
	//updatehuman
	//updatecomputer
	//reset
	//isgameover
	//newgame
	
	public PongTester(){}
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
	public boolean test1(Pong3d1pControl control){
		System.out.println("Running test 1...");
		System.out.println(" Description: ");
		System.out.println("  Testing when ball is positioned to where computer scores");
		control.depth = 2.1f;
		control.checkScore();
		System.out.println("   Anicipated Computer Score: 1");
		System.out.println("   Actual Computer Score: " + control.computerScore);
		if(control.computerScore != 1){
			return false;
		}
		else{
			return true;
		}
	}
	//test 2
	//ball is set up where player scores
	//check player score
	public boolean test2(Pong3d1pControl control){
		System.out.println("Running test 2...");
		System.out.println(" Description: ");
		System.out.println("  Testing when ball is positioned to where human scores");
		control.depth = -3.1f;
		control.checkScore();
		System.out.println("   Anicipated Human Score: 1");
		System.out.println("   Actual Human Score: " + control.humanScore);
		if(control.humanScore != 1){
			return false;
		}
		else{
			return true;
		}
	}
	//test3
	//ball is up against wall
	//check to see if direction changes
	public boolean test3(Pong3d1pControl control){
		System.out.println("Running test 3...");
		System.out.println(" Description: ");
		System.out.println("  Place ball up against wall, update, check if direction switches");
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
	public boolean test4(Pong3d1pControl control){
		System.out.println("Running test 4...");
		System.out.println(" Description: ");
		System.out.println("  Place ball up against wall, update, check if direction switches");
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
	public boolean test5(Pong3d1pControl control){
		boolean allCorrect = true;
		System.out.println("Running test 5...");
		System.out.println(" Description: ");
		System.out.println("  Call reset and see if positioning  is adjusted");
		System.out.println("");
		//arbitrary locations
		control.xloc = 1.0f;
		control.depth = 0.5f;
		control.squish = 0.2;
		control.hxloc = 0.2f;
		control.computerX = 0.2f;
		control.reset();
		System.out.println("   Anticipated ball x location: 0.0f");
		System.out.println("   Actual ball x location: " + control.xloc);
		if(control.xloc != 0.0f){
			allCorrect = false;
		}
		System.out.println("   Anticipated ball z location: 0.0f");
		System.out.println("   Actual ball z location: " + control.depth);
		if(control.depth != 0.0f){
			allCorrect = false;
		}
		System.out.println("   Anticipated squish amount: 1.0");
		System.out.println("   Actual squish amount: " + control.squish);
		if(control.squish != 1.0){
			allCorrect = false;
		}
		System.out.println("   Anticipated human x location: 0.0f");
		System.out.println("   Actual human x location: " + control.hxloc);
		if(control.hxloc != 0.0f){
			allCorrect = false;
		}
		System.out.println("   Anticpated computer x location: 0.0f");
		System.out.println("   Actual computer x location: " + control.computerX);
		if(control.computerX != 0.0f){
			allCorrect = false;
		}
		System.out.println();
		
		return allCorrect;
	}
	//test6
	//put ball out of bounds, wait one execution cycle, see if ball stays in bounds
	public boolean test6(Pong3d1pControl control){
		System.out.println("Running test 6...");
		System.out.println("Description: ");
		return true;
	}
	//test7 
	//put score at 4-4
	//see if 5 stops game
	public boolean test7(Pong3d1pControl control){
		System.out.println("Running test 7...");
		System.out.println("Description: ");
		return true;
	}
	//test8 
	//check newGame method
	public boolean test8(Pong3d1pControl control){
		System.out.println("Running test 8...");
		System.out.println("Description: ");
		return true;
	}
	//test9
	//put ball up against wall
	//check squish
	public boolean test9(Pong3d1pControl control){
		System.out.println("Running test 9...");
		System.out.println("Description: ");
		return true;
	}
	//test10
	//call new game
	//check if everything is set accordingly
	public boolean test10(Pong3d1pControl control){
		System.out.println("Running test 10...");
		System.out.println("Description: ");
		return true;
	}
	//test11
	public boolean test11(Pong3d1pControl control){
		System.out.println("Running test 11...");
		System.out.println("Description: ");
		return true;
	}

}
