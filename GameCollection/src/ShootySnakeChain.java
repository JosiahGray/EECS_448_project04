public class ShootySnakeChain {
  public int numberOfBalls;
  public double initialX;
  public double initialY;
  public double initialVX;
  public double initialVY;
  public double width;
  public double height;
  public ShootySnakeEnemy[] chain;
  public double radius;

  public ShootySnakeChain(double mx, double  my, double  mvx, double  mvy, double r, int num, double w, double h)
  {
    numberOfBalls = num;
    initialX = mx;
    initialY = my;
    initialVX = mvx;
    initialVY = mvy;
    width = w;
    height = h;
    radius = r;
    chain = new ShootySnakeEnemy[num];
    for(double i = 0; i < numberOfBalls; i++)
    {
      chain[(int)(numberOfBalls - i - 1)] = new ShootySnakeEnemy(
        initialX + i*radius,
        initialY,
        initialVX,
        initialVY,
        radius
      );
    }
    chain[numberOfBalls - 1].becomeLeader();
  }

  public ShootySnakeEnemy ball(int num)
  {
    return chain[num];
  }

  public Boolean checkDisabled()
  {
    Boolean chainDisabled = true;
    for(int i = 0; i < numberOfBalls; i++)
    {
      if(!chain[i].disabled)
        chainDisabled = false;
    }
    return chainDisabled;
  }
}
