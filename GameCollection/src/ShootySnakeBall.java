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
