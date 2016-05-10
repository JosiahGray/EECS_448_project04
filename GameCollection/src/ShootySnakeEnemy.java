import java.awt.*;

public class ShootySnakeEnemy extends ShootySnakeBall{
  public Boolean leader;
  public int hitpoints;
  public int totalHitpoints;
  public int neighbors;
  public java.awt.Color color;

  public ShootySnakeEnemy(double mx, double my, double mvx, double mvy, double mradius)
  {
    super(mx, my, mvx, mvy, mradius);
    leader = false;
    hitpoints = 2;
    totalHitpoints = 2;
    neighbors = 0;
    color = Color.BLUE;
  }

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
