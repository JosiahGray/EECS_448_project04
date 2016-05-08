import java.awt.*;

public class ShootySnakeEnemy extends ShootySnakeBall{
  public Boolean leader;
  public int hitpoints;
  public java.awt.Color color;

  public ShootySnakeEnemy(double mx, double my, double mvx, double mvy, double mradius)
  {
    super(mx, my, mvx, mvy, mradius);
    leader = false;
    hitpoints = 2;
    color = Color.BLUE;
  }

  public void hit(double x0, double y0)
  {
    hitpoints--;
    color = color.darker();
    if(hitpoints <= 0)
    {
      disabled = true;
    }
    else if(leader)
    {
      vx = 25*normalX(x0, y0);
      vy = 25*normalY(x0, y0);
    }
  }

  public void becomeLeader()
  {
    if(!disabled)
    {
      leader = true;
      hitpoints = 3 - (2 - hitpoints);
      color = Color.CYAN;
      for(int i = 0; i < 3 - hitpoints; i++)
      {
        color = Color.CYAN.darker();
      }
    }
  }
}
