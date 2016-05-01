
public class Pong3d1pComputer {

	public Pong3d1pComputer(){

	}
	/*
	 * int difference = -(y + (height/2) - yPosition); //determines difference
		if(difference < 0 && difference < -6){ //determines whether paddle will move up, down, or remain where it is
			difference = - 5;
		} else if(difference > 0 && difference > 6){
			difference = 5;
		}
		//move paddle
		y+= difference;
		ySpeed = 5;
		if(y < 0){ //if paddle is off the top
			y = 0;
			ySpeed = 0;
		} else if(y + height > 400){ //if paddle is off the bottom 
			y = 400-height;
			ySpeed = 0;
		}
	 */
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
