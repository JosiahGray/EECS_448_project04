/*
 *  @author Paul McElroy
 *
 *  Contains the ShootySnakeChain class that holds many enemy balls together and
 *  has helper functions for dealing with the various things that are asked of
 *  chain object often.
 */

public class ShootySnakeChain {
  /**
  * Contains the number of enemy balls in the chain
  */
  public int numberOfBalls;
  /**
  * Contains the initial x coordinate of the leader enemy Ball in the chain
  */
  public double initialX;
  /**
  * Contains the initial y coordinate of the leader enemy Ball in the chain
  */
  public double initialY;
  /**
  * Contains the initial x velocity component of the leader enemy Ball in the chain
  */
  public double initialVX;
  /**
  * Contains the initial y velocity component of the leader enemy Ball in the chain
  */
  public double initialVY;
  /**
  * Contains the WIDTH of the window the Chain was designed for
  */
  public double width;
  /**
  * Contains the HEIGHT of the window the Chain was designed for
  */
  public double height;
  /**
  * Contains the actual array of enemy balls, which is also referred to as a chain
  */
  public ShootySnakeEnemy[] chain;
  /**
  * Contains the radius of the enemy balls in the Chain.
  */
  public double radius;
  /**
  * ShootySnakeChain Constructor for the ShootySnakeChain class.
  * @post All default values are set, including the number of neighbors of each enemy Ball.  The last Ball is the leader.
  */
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
    for(double i = 0; i < numberOfBalls; i++)
    {
      if(i == 0 || i == numberOfBalls - 1)
        chain[(int)i].neighbors = 1;
      else
        chain[(int)i].neighbors = 2;
    }
    chain[numberOfBalls - 1].becomeLeader();
  }

  /*
  * ball Getter method for getting the num'th ball in the chain.
  * @return The ball in the chain array of index num
  */
  public ShootySnakeEnemy ball(int num)
  {
    return chain[num];
  }

  /*
  * checkDisabled Returns true if all the balls in the chain array are disabled.  False else.
  * @return Returns TRUE if all the balls in chain are disabled; FALSE else.
  */
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
