
public class Coordinates {
	/**
	 * float representing the x coordinate
	 */
	float x;
	/**
	 * float representing the y coordinate
	 */
	float y;
	/**
	 * float representing th z coordinate
	 */
	float z;
	/**
	 * Coordinates()
	 * @post creates a new set of coordinates of x y and z set to zero
	 * Creates a new instance of coordinates with default coordinates x,y,z = 0
	 */
	public Coordinates(){
		x = 0;
		y = 0; 
		z = 0;
	
	}
	/**
	 * void setCoordinates(float x1, float y1, float z1)
	 * @param x1 the coordinate to set x to
	 * @param y1 the coordinate to set y to
	 * @param z1 the coordinate to set z to
	 * @post sets the referred coordinates to the given coordinates in the parameters
	 * Sets the coordinates to the 3 values passed in the parameter
	 */
	public void  setCoordinates(float x1, float y1, float z1){
		x = x1;
		y = y1;
		z = z1;
	}

}
