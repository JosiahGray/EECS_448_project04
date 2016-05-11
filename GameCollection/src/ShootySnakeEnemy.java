/*
 *  @author Paul McElroy
 *
 *  Contains the ShootySnakeEnemy class which extends ShootySnakeBall.
 *  As a specialization of the Ball class, this class has special methods to
 *  make manipulating enemy units in code easier.
 */
import java.awt.*;

public class ShootySnakeEnemy extends ShootySnakeBall{
  /*
  * Boolean value used to keep track of the leader status of any given enemy ball.
  */
  public Boolean leader;
  /*
  * Integer value used to keep track of the hitpoints any given enemy ball has remaining.
  */
  public int hitpoints;
  /*
  * Integer value used to keep track of the maximum hitpoints of any given enemy ball.
  */
  public int totalHitpoints;
  /*
  * Integer value used to keep track of the number of non-disabled neighbors of any given enemy ball.
  */
  public int neighbors;
  /*
  * java.awt.Color value used to keep track of the particular color of any given enemy ball.
  */
  public java.awt.Color color;

  /*
  * ShootySnakeEnemy Constructor for the ShootySnakeEnemy class.  Sets all values those passed in and the subclass defaults.
  * @post A ShootySnakeEnemy is instantiated.
  */
  public ShootySnakeEnemy(double mx, double my, double mvx, double mvy, double mradius)
  {
    super(mx, my, mvx, mvy, mradius);
    leader = false;
    hitpoints = 2;
    totalHitpoints = 2;
    neighbors = 0;
    color = Color.BLUE;
  }

  /*
  * hit A very important function designed to direct the behavior and hitpoints of hit enemy balls.
  * @post A hit leader that isn't disabled will orient towards the player.  If the hitpoints are zero or less the ball is disabled.
  * @return If the ShootySnakeEnemy is disabled, will give points equal to the number of shots needed to disable it finally.
  */
  public int hit(double x0, double y0, double mratio)
  {
    int points = 0;
    hitpoints--;
    color = color.darker();
    if(hitpoints <= 0 && !disabled)
    {
      disabled = true;
      points = totalHitpoints;
    }
    else if(leader)
    {
      vx = 25*mratio*normalX(x0, y0);
      vy = 25*mratio*normalY(x0, y0);
    }
    return points;
  }

  /*
  * Creates a leader ball from any regular non-disabled ball, increasing it's total life by 1 and setting its color to CYAN
  * @post The enemy ball is now a leader with +1 health and is CYAN and will follow the player when hit
  */
  public void becomeLeader()
  {
    if(!disabled && totalHitpoints == 2)
    {
      leader = true;
      totalHitpoints = 3;
      hitpoints++;
      color = Color.CYAN;
      for(int i = 0; i < totalHitpoints - hitpoints; i++)
      {
        color = Color.CYAN.darker();
      }
    }
  }

  /*
  * becomeLoneWolf If a enemy ball's two neighbors are disabled, it will become a fast magenta enemy with 4 total hitpoints
  * @post Lone wolves gain a temporary increase to speed and take one additional hit to disable
  */
  public void becomeLoneWolf()
  {
    if(!disabled && totalHitpoints < 4)
    {
      becomeLeader();
      totalHitpoints = 4;
      hitpoints += 2;
      color = Color.MAGENTA;
      for(int i = 0; i < totalHitpoints - hitpoints; i++)
      {
        color = Color.MAGENTA.darker();
      }
      vx *= 2;
      vy *= 2;
    }
  }
}
