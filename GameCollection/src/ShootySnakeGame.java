/*
 *  @author Paul McElroy
 *
 *  Adapted from and built on code/ideas by Michael Birken
 *  in his GameTemplate Example found here:
 *   http://meatfighter.com/gametemplate/
 *
 */

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ShootySnakeGame extends JFrame {
  /**
  * WIDTH is the preferred width of the window
  */
  private static final int WIDTH = 640;
  /**
  * HEIGHT is the preferred height of the window
  */
  private static final int HEIGHT = 480;
  /**
  * The intended frame rate
  */
  private static final int FRAMES_PER_SECOND = 60;
  /**
  * The period of a frame
  */
  private static final long FRAME_PERIOD = 1000000000L / FRAMES_PER_SECOND;

  /**
  * Will contain Boolean values returning true if the keys, whose Constant Field Values correspond to the index of this array, are pressed.
  */
  private final boolean[] KEYS = new boolean[65535]; // pressed keys
  /**
  * The total number of enemy balls.
  */
  private final int BALLS = 200;
  /**
  * The maximum number of beams.
  */
  private final int BEAMS = 100;
  /**
  * Diameter of the enemy balls.
  */
  private final double BALL_SIZE = WIDTH / 40;
  /**
  * Diameter of the beam balls.
  */
  private final double BEAM_SIZE = BALL_SIZE/10;
  /**
  * Diameter of the player ball.
  */
  private final double PLAYER_SIZE = BALL_SIZE;
  /**
  * How fast the balls move.
  */
  private final double BALL_VELOCITY = 2;
  /**
  * Controls the tightness of the chain.  1 is the tightest. Higher is loose.
  */
  private final double DIV = 10;
  /**
  * How fast the player moves.
  */
  private final int PLAYER_VELOCITY = 10;
  /**
  * JPanel containing the Image image.
  */
  private JPanel panel;
  /**
  * Image containing the game graphics.
  */
  private Image image;
  /**
  * Contains the graphics of the game.
  */
  private Graphics imageGraphics;
  /**
  * Ball array containing all the enemy balls.
  */
  private Enemy[] balls = new Enemy[BALLS];
  /**
  * Ball array containing all of the beam Ball objects.
  */
  private Ball[] beams = new Ball[BEAMS];
  /**
  * The player Ball object.
  */
  private Ball player;
  /**
  * Contains the last nano second (according to nanoTime()) that a beam was fired.
  */
  private long lastShot;
  /**
  * The length of time the player should be invulnerable to harm and can't shoot beams after respawning.
  */
  private final long spawnInvulnerabilityCounter = 4000000000L;
  /**
  * Contains the nano second (according to nanoTime()) since a player respawned.
  */
  private long respawnStart;
  /**
  * The number of lives the player has left before they lose.  Getting struck by an enemy ball when the player has 0 will cause them to lose.
  */
  private int lives;
  /**
  * The gameOver flag tells the game to stop updating the game model and to place the corresponding win/lose message in the window.
  */
  private Boolean gameOver;
  /**
  * The playerWon flag, when gameOver is true, tells the game whether the player has won (true) or lost (false).
  */
  private Boolean playerWon;

  private volatile Boolean mouseDown = false;

  private volatile int mouseX;
  private volatile int mouseY;

  /**
  * Constructor for ShootySnakeGame.  Will initialize the frame, image and game model.
  * @post The game's frame, image and game model are initialized.
  */
  public ShootySnakeGame() {
    initFrame();
    initImage();
    initModel();
  }

  /**
  * executeGameLoop performs one frame of game activity and then sleeps the rest of the allotted time.
  * @post A frame of game activity has been performed and the model and graphics updated.
  */
  public void executeGameLoop() {
    if(!gameOver) {
      long nextFrameStart = System.nanoTime();
      do {
        updateModel();
        nextFrameStart += FRAME_PERIOD;
      } while(nextFrameStart < System.nanoTime());
      renderFrame();
      long remaining = nextFrameStart - System.nanoTime();
      if (remaining > 0) {
        try {
          Thread.sleep(remaining / 1000000);
        } catch(Throwable t) {
        }
      }
    }
  }

  /**
  * Updates the game model containing the enemy balls, player, beams and the win/lose state.
  * @post The game model is updated for the current frame.
  */
  private void updateModel() {

    // Move the balls
    moveBalls();
    // Check if all balls are disabled
    checkBalls(); //victory condition
    // Move the player
    if ((KEYS[KeyEvent.VK_LEFT] || KEYS[KeyEvent.VK_A]) && player.x > 0) {
      player.x -= PLAYER_VELOCITY;
    }
    if ((KEYS[KeyEvent.VK_RIGHT] || KEYS[KeyEvent.VK_D]) && player.x < WIDTH - player.radius*2) {
      player.x += PLAYER_VELOCITY;
    }
    if ((KEYS[KeyEvent.VK_UP] || KEYS[KeyEvent.VK_W]) && player.y > 0) {
      player.y -= PLAYER_VELOCITY;
    }
    if ((KEYS[KeyEvent.VK_DOWN] || KEYS[KeyEvent.VK_S]) && player.y < HEIGHT - player.radius*2) {
      player.y += PLAYER_VELOCITY;
    }
    if ((KEYS[KeyEvent.VK_SPACE] || mouseDown) && (System.nanoTime() - lastShot > 1000000000L/10) &&
        (System.nanoTime() - respawnStart > spawnInvulnerabilityCounter))
    {
      lastShot = System.nanoTime();
      mouseX = MouseInfo.getPointerInfo().getLocation().x - getLocationOnScreen().x - getComponent(0).getBounds().x;
      mouseY = MouseInfo.getPointerInfo().getLocation().y - getLocationOnScreen().y - getComponent(0).getBounds().y;
      addBeam(player.x + player.radius, player.y + player.radius, 4*player.normalX(mouseX,mouseY), 4*player.normalY(mouseX,mouseY));
    }
    moveBeams();
    findCollisions();
  }

  /**
  * Obtains information produced by the updateModel method to produce an image of each frame's state of play.
  * @post The graphics container is updated for the current frame.
  */
  private void renderFrame() {
    imageGraphics.setColor(Color.BLACK);
    imageGraphics.fillRect(0, 0, WIDTH, HEIGHT);
    if(!gameOver)
    {
      // Render balls
      for(int i = 0; i < BALLS; i++) {
        Enemy ball = balls[i];
        if(!ball.disabled)
        {
          imageGraphics.setColor(ball.color);
          imageGraphics.fillOval((int)(ball.x), (int)(ball.y), (int)ball.radius*2, (int)ball.radius*2);
        }
      }
      // Render player
      if(!(System.nanoTime() - respawnStart < spawnInvulnerabilityCounter))
      {
        imageGraphics.setColor(Color.GREEN);
      }
      else if(System.nanoTime() - respawnStart < spawnInvulnerabilityCounter/3)
        imageGraphics.setColor(Color.RED);
      else if(System.nanoTime() - respawnStart < spawnInvulnerabilityCounter*2/3)
        imageGraphics.setColor(Color.ORANGE);
      else
        imageGraphics.setColor(Color.YELLOW);
      imageGraphics.fillOval(
        (int)player.x, (int)player.y, (int)player.radius*2, (int)player.radius*2);

      // Render beams
      imageGraphics.setColor(Color.RED);
      for(int i = 0; i < BEAMS; i++)
      {
        if(!beams[i].disabled)
          imageGraphics.fillOval((int)(beams[i].x), (int)(beams[i].y), (int)beams[i].radius*2, (int)beams[i].radius*2);
      }

      //Render lives
      int fontSize = 20;
      imageGraphics.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
      imageGraphics.setColor(Color.GREEN);
      imageGraphics.drawString("LIVES " + lives, 10, 20);
    }
    else if(gameOver)
    {
      String message;
      if(!playerWon)
        message = "Game Over!";
      else
        message = "You Won!";
      int fontSize = 20;
      imageGraphics.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
      imageGraphics.setColor(Color.GREEN);
      imageGraphics.drawString(message, WIDTH/2, HEIGHT/2);
    }

    // Draw to screen
    Graphics panelGraphics = panel.getGraphics();
    if (panelGraphics != null) {
      panelGraphics.drawImage(image, 0, 0, null);
      panelGraphics.dispose();
    }
  }

  /**
  * Initializes the JFrame with proper naming, setting Resizeable to false, setting the size to WIDTH and HEIGHT and putting the window in the center.
  * @post The JFrame is initialized.
  */
  private void initFrame() {
    setTitle("Shooty Snake");
    setResizable(true);
    panel = (JPanel)getContentPane();
    panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    panel.setMaximumSize(new Dimension(WIDTH, HEIGHT));
    panel.setMinimumSize(new Dimension(WIDTH, HEIGHT));
    addMouseListener(
      new MouseAdapter()
      {
        public void mousePressed(MouseEvent e)
        {
          if (e.getButton() == MouseEvent.BUTTON1)
          {
            mouseDown = true;
          }
        }

        public void mouseReleased(MouseEvent e)
        {
          if (e.getButton() == MouseEvent.BUTTON1)
          {
            mouseDown = false;
          }
        }
      }
    );
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }

  /**
  * Initializes the Image image which will house the graphics that will be drawn to the JFrame.
  * @post The Image is initialized.
  */
  private void initImage() {
    image = createImage(WIDTH, HEIGHT);
    imageGraphics = image.getGraphics();
  }

  /**
  * Initializes the game model.  All values are default.
  * @post The game model is initialized.
  */
  private void initModel() {
    // Set lives
    lives = 3;
    gameOver = false;

    player = new Ball(WIDTH/4, HEIGHT/2, 0, 0, PLAYER_SIZE);
    for(double i = 0; i < BALLS; i++)
    {
      balls[(int)(BALLS - i - 1)] = new Enemy(
        (WIDTH - BALL_SIZE)/2 + i*BALL_SIZE,
        (HEIGHT - BALL_SIZE)/2,
        BALL_VELOCITY,
        BALL_VELOCITY,
        BALL_SIZE
      );
    }
    lastShot = System.nanoTime();
    for(int i = 0; i < BEAMS; i++)
    {
      beams[i] = new Ball(-100, -100, 0, 0, BEAM_SIZE);
      beams[i].disabled = true;
    }
    balls[199].becomeLeader();
  }

  /**
  * Overides JFrame's processKeyEvent so that it will keep track of key presses in the KEYS array.
  * @post A Boolean value has been assigned at a key's index in the KEYS array indicating if it has been pressed.
  */
  public void processKeyEvent(KeyEvent e) {
    KEYS[e.getKeyCode()] = e.getID() == 401;
  }



  /**
  * Moves the balls in the balls array according to their position, velocity and disabled status of their leading members.
  * @post The balls array's Ball objects have updated positions and velocities.
  */
  private void moveBalls()
  {
    for(int i = 0; i < BALLS; i++)
    {
      Ball ball = balls[i];

      if(i != BALLS - 1 && !balls[i+1].disabled)
      {
        ball.vx = (balls[i+1].x - ball.x);
        ball.vy = (balls[i+1].y - ball.y);
        ball.x += ball.vx/DIV;
        ball.y += ball.vy/DIV;
      }
      else
      {
        if (ball.x >= WIDTH - ball.radius*2) {
          ball.vx = -ball.vx;
        } else if (ball.x <= 0) {
          ball.vx = -ball.vx;
        }
        if (ball.y >= HEIGHT - ball.radius*2) {
          ball.vy = -ball.vy;
        } else if (ball.y <= 0) {
          ball.vy = -ball.vy;
        }
        if(i == BALLS-1)
        {
          ball.x += ball.vx;
          ball.y += ball.vy;
        }
        else
        {
          ball.x += ball.vx/DIV;
          ball.y += ball.vy/DIV;
        }
      }
    }
  }

  /**
  * Checks for the victory condition that all enemy balls have been hit with beams so that they are all disabled.
  * @post  If all balls are disabled, the player wins, else nothing.
  */
  private void checkBalls()
  {
    int count = 0;
    for(int i = 0; i < BALLS; i++)
    {
      if(balls[i].disabled)
        count++;
    }
    if(count >= BALLS)
    {
      win();
    }
  }

  /**
  * Removes the oldest beam object from the end of the array and adding a newly created beam object to its beginning.
  * @param  x The x coordinate of the new beam object.
  * @param  y The t coordinate of the new beam object.
  * @param  vx The horizontal component of velocity of the new beam object.
  * @param  vy The vertical component of velocity of the new beam object.
  * @post A new Beam is added to the front and an old Beam is removed from the back of the beams array.
  */
  private void addBeam(double x, double y, double vx, double vy) {
    for(int i = 0; i < BEAMS-1; i++){
      beams[i] = beams[i+1];
    }
    beams[BEAMS-1] = new Ball(x,y,vx,vy, BEAM_SIZE*2);
  }

  /**
  * Moves the beams along their trajectory, given their velocity and position.
  * @post Beam locations are updated.
  */
  private void moveBeams()
  {
    for(int i = 0; i < BEAMS; i++)
    {
      Ball beam = beams[i];
      beam.x += beam.vx;
      beam.y += beam.vy;
    }
  }

  /**
  * Call functions to check for collisions and take appropriate actions amongst the actors.
  * @post Runs ballHitsPlayer and beamHitsBall.
  */
  private void findCollisions()
  {
      ballHitsPlayer();
      beamHitsBall();
  }

  /**
  * Checks if any enabled balls in the balls array have hit the player this frame.
  * @post If the player is hit, they are respawned or lose, else nothing happens.
  */
  private void ballHitsPlayer()
  {
    if(System.nanoTime() - respawnStart > spawnInvulnerabilityCounter)
      for(int i = 0; i < BALLS; i++)
      {
        if(circlesIntersect(balls[i], player, BALL_SIZE/2, PLAYER_SIZE/2) && !balls[i].disabled)
        {
          if(lives > 0)
            respawn();
          else
            lose();
        }
      }
  }

  /**
  * Checks if any enabled balls in the balls array have hit any enabled beams in the beams array.
  * @post Any hit balls are disabled.
  */
  private void beamHitsBall()
  {
    for(int i = 0; i < BEAMS; i++)
    {
      for(int j = 0; j < BALLS; j++)
      {
        if(circlesIntersect(beams[i], balls[j], BEAM_SIZE/2, BALL_SIZE) && !beams[i].disabled && !balls[j].disabled)
        {
          // if(balls[j].x > getComponent(0).getBounds().x && balls[j].x < WIDTH - balls[j].radius*2 - getComponent(0).getBounds().x)
          // {
            beams[i].disabled = true;
            balls[j].hit(player.x, player.y);
            if(balls[j].disabled && j > 0)
            {
              balls[j-1].becomeLeader();
            }
          // }
        }
      }
    }
  }

  /**
  * Checks for intersection between two circles.
  * @param b1 The first Ball object to check.
  * @param b2 The second Ball object to check.
  * @param size1 The radius of the first Ball object.
  * @param size2 The radius of the second Ball object.
  * @return True if the Ball objects are intersecting, False otherwise.
  */
  private Boolean circlesIntersect(Ball b1, Ball b2, double size1, double size2)
  {
    return java.awt.geom.Point2D.distance(b1.x + size1, b1.y + size1, b2.x + size2, b2.y + size2) < size1 + size2;
  }

  /**
  * Respawns the player in the center of the screen with a cooldown period until they can be hit again and before they can fire again.
  * @post Number of lives reduced by 1, the beams array is reset to default.  The player is placed in the center of the screen and the timer for respawn invulnerability is set.
  */
  private void respawn()
  {
    lives--;
    respawnStart = System.nanoTime();
    lastShot = System.nanoTime();
    for(int i = 0; i < BEAMS; i++)
    {
      beams[i] = new Ball(-100, -100, 0, 0, BEAM_SIZE);
      beams[i].disabled = true;
    }
    player.x = WIDTH/2;
    player.y = HEIGHT/2;
  }

  /**
  * Sets the flags indicating end game lose.
  * @post The game ends and the lose screen is brought up.
  */
  private void lose()
  {
    gameOver = true;
    playerWon = false;
  }

  /**
  * Sets the flags indicating end game victory.
  * @post The game ends and the victory screen is brought up.
  */
  private void win()
  {
    gameOver = true;
    playerWon = true;
  }
}

class Ball {
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
  public Ball(double mx, double my, double mvx, double mvy, double mradius)
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
    double temp = xDiff/java.lang.Math.sqrt(xDiff*xDiff + yDiff*yDiff);
    return temp;
  }

  public double normalY(double x0, double y0)
  {
    double xDiff = x0 - (x + radius);
    double yDiff = y0 - (y + radius);
    double temp = yDiff/java.lang.Math.sqrt(xDiff*xDiff + yDiff*yDiff);
    return temp;
  }
}


class Enemy extends Ball{
  public Boolean leader;
  public int hitpoints;
  public java.awt.Color color;

  public Enemy(double mx, double my, double mvx, double mvy, double mradius)
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
      disabled = true;
    else if(leader)
    {
      vx = 25*normalX(x0, y0);
      vy = 25*normalY(x0, y0);
      // if(vx*vx + vy*vy > 2000)
      // {
      //   vx = 2000*normalX(0,0);
      //   vy = 2000*normalY(0,0);
      // }
      // if(vx*vx + vy*vy < 100)
      // {
      //   vx = 100*normalX(0,0);
      //   vy = 100*normalY(0,0);
      // }
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
