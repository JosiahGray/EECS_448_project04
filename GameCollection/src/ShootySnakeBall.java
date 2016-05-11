public class ShootySnakeBall {
  /**
  * The x coordinate of a Ball object.
  */
  public double x;
  /**
  * The y coordinate of a Ball object.
  */
  public double y;
  /**
  * The horizontal component of velocity of a Ball object.
  */
  public double vx;
  /**
  * The vertical component of velocity of a Ball object.
  */
  public double vy;
  /**
  * The disabled state of a Ball object.
  */
  public Boolean disabled;
  /**
  * The radius of the Ball object.
  */
  public double radius;

  /**
  * Constructor for a Ball object.
  * @param mx The x coordinate for the new ball object.
  * @param my The y coordinate for the new ball object.
  * @param mvx The horizontal component of velocity for the new ball object.
  * @param mvy The vertical component of velocity for the new ball object.
  */
  public ShootySnakeBall(double mx, double my, double mvx, double mvy, double mradius)
  {
    x = mx;
    y = my;
    vx = mvx;
    vy = mvy;
    disabled = false;
    radius = mradius;
  }

  /*
  * normalX takes the location of the player and gives the x component of a unit length vector pointing from the ball to the player
  * @param x0 The x component of the player's coordinates
  * @param y0 The y component of the player's coordinates
  * @return The x component of the unit length vector between the Ball and the Player
  */
  public double normalX(double x0, double y0)
  {
    double xDiff = x0 - (x + radius);
    double yDiff = y0 - (y + radius);
    double temp = 0.0;
    if(xDiff != 0.0)
      temp = xDiff/java.lang.Math.sqrt(xDiff*xDiff + yDiff*yDiff);
    else
      temp = 0.0;
    return temp;
  }
  /*
  * normalY takes the location of the player and gives the y component of a unit length vector pointing from the ball to the player
  * @param x0 The x component of the player's coordinates
  * @param y0 The y component of the player's coordinates
  * @return The y component of the unit length vector between the Ball and the Player
  */
  public double normalY(double x0, double y0)
  {
    double xDiff = x0 - (x + radius);
    double yDiff = y0 - (y + radius);
    double temp = 0.0;
    if(yDiff != 0.0)
      temp = yDiff/java.lang.Math.sqrt(xDiff*xDiff + yDiff*yDiff);
    else
      temp = 0.0;
    return temp;
  }
}
