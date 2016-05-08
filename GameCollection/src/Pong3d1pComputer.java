import java.util.Random;
public class Pong3d1pComputer {
	int executionTime;
	float increaseDifficulty; 

	public Pong3d1pComputer(){
		executionTime = 0;
		increaseDifficulty = 0.0f;
	}
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
