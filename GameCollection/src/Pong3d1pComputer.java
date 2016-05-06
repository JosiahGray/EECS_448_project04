import java.util.Random;
public class Pong3d1pComputer {

	public Pong3d1pComputer(){

	}
	public float move(Coordinates ball, Coordinates comp){
		float xBpos = ball.x;
		float zBpos = ball.z;
		float xCpos = comp.x;
		float difference = 0.0f;
		float xdifference = -(xCpos - xBpos);
		if(xdifference<= -0.05f){
			difference = -0.001f;
		} else if(xdifference >= 0.05f){
			difference = 0.001f;
		}
		
		return difference;
		
	}

}
