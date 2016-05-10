import java.util.Random;
public class Pong3d1pComputer {
	/**
	 * Integer representing the number of times Timer has executed
	 */
	int executionTime;
	/**
	 * float representing the difficulty bonus to the computer, the longer you play
	 */
	float increaseDifficulty; 
	/**
	 * Pong3d1pComputer()
	 * @post constructs new Pong3d1pComputer instance
	 * Constructor for the Pong3d1pComputer which dictates the AI logic
	 */
	public Pong3d1pComputer(){
		executionTime = 0;
		increaseDifficulty = 0.0f;
	}
	/**
	 * float move(Coordinates ball, Coordinates comp)
	 * @param ball The coordinates instance representing the location of the ball
	 * @param comp The coordinates instance representing the location of the computer
	 * @return float of the allowed movement of the computer to try and strike the ball
	 * Determines how far the computer is allowed to move towards the ball, accounts for 
	 * how long the game has been taking place in allowing the distance to move.
	 */
	public float move(Coordinates ball, Coordinates comp){
		if(increaseDifficulty < 0.05f){
			if(executionTime % 10000 ==  0){
				increaseDifficulty += 0.0005f;

			}
		}
		float xBpos = ball.x;
		float zBpos = ball.z;
		float xCpos = comp.x;
		float difference = 0.0f;
		float xdifference = -(xCpos - xBpos);
		if(xdifference<= -0.05f){
			difference = -0.001f - increaseDifficulty ;
		} else if(xdifference >= 0.05f){
			difference = 0.001f + increaseDifficulty;
		}
		
		return difference;
		
	}

}
