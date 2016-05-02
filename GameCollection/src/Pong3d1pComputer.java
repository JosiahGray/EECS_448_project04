
public class Pong3d1pComputer {

	public Pong3d1pComputer(){

	}
	public float move(Coordinates ball, Coordinates comp){
		float xBpos = ball.x;
		float zBpos = ball.z;
		float xCpos = comp.x;
		float difference = 0.0f;
		difference = -(xCpos - xBpos);
		if(difference< -0.005f){
			difference = -0.0000005f;
		} else if(difference > 0 && difference > 0.005f){
			difference = 0.0000005f;
		}
		
		return difference;
		
	}

}
