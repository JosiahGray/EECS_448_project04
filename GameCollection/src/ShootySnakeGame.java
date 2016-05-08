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
  //private ShootySnakeEnemy[] balls = new ShootySnakeEnemy[BALLS];
  private ShootySnakeChain[] chain = new ShootySnakeChain[9];
  /**
  * Ball array containing all of the beam Ball objects.
  */
  private ShootySnakeBall[] beams = new ShootySnakeBall[BEAMS];
  /**
  * The player Ball object.
  */
  private ShootySnakeBall player;
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

  private long levelTransitionStart;
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
  private int activeChains = 0;
  private int disabledChains = 0;

  private Boolean newLevel = true;
  private int level = 1;

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
        if(!newLevel)
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
    // Check if level is disabled
    if(newLevel)
    {
      levelTransition(level, WIDTH/2, HEIGHT/2);
    }
    else
    {
      updateLevels();
    }
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
    if(newLevel && System.nanoTime() - levelTransitionStart < 4000000000L)
    {
      message("Level " + level, WIDTH/2, HEIGHT/2, 20);
    }
    else if(newLevel && System.nanoTime() - levelTransitionStart >= 4000000000L)
    {
      System.out.println("thing 3");
      newLevel = false;
      levelTransition(level, WIDTH/2, HEIGHT/2);
    }

    if(!gameOver && !newLevel)
    {
      // Render balls
      int count = 0;
      for(int k = disabledChains; k < activeChains; k++)
      {
        for(int i = 0; i < chain[k].numberOfBalls; i++) {
          ShootySnakeEnemy ball = chain[k].ball(i);
          if(!ball.disabled)
          {
            imageGraphics.setColor(ball.color);
            imageGraphics.fillOval((int)(ball.x), (int)(ball.y), (int)ball.radius*2, (int)ball.radius*2);
          }
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
      message("LIVES " + lives, 10, 20, 20);

    }
    else if(gameOver)
    {
      String message;
      if(!playerWon)
        message = "Game Over!";
      else
        message = "You Won!";

      message(message, WIDTH/2, HEIGHT/2, 20);
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
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
    level = 1;
    newLevel = true;
    levelTransitionStart = System.nanoTime();

    player = new ShootySnakeBall(WIDTH/4, HEIGHT/2, 0, 0, PLAYER_SIZE);
    initLevels();
    //balls = chain[k].chain;
    lastShot = System.nanoTime();
    for(int i = 0; i < BEAMS; i++)
    {
      beams[i] = new ShootySnakeBall(-100, -100, 0, 0, BEAM_SIZE);
      beams[i].disabled = true;
    }
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
    for(int k = disabledChains; k < activeChains; k++)
    {
      for(int i = 0; i < chain[k].numberOfBalls; i++)
      {
        ShootySnakeEnemy ball = chain[k].ball(i);
        if(ball.leader)
        {
          while(ball.vx*ball.vx + ball.vy*ball.vy < 4)
          {
            ball.vx *= 1.10;
            ball.vy *= 1.10;
          }
          while(ball.vx*ball.vx + ball.vy*ball.vy > 800)
          {
            ball.vx *= .90;
            ball.vy *= .90;
          }
        }
        if(i != chain[k].numberOfBalls - 1 && !chain[k].ball(i+1).disabled)
        {
          ball.vx = (chain[k].ball(i+1).x - ball.x);
          ball.vy = (chain[k].ball(i+1).y - ball.y);
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
          if(i == chain[k].numberOfBalls-1 && chain[k].ball(chain[k].numberOfBalls-1).hitpoints == 3)
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
  }

  /**
  * Checks for the victory condition that all enemy balls have been hit with beams so that they are all disabled.
  * @post  If all balls are disabled, the player wins, else nothing.
  */
  private void checkBalls()
  {
    // for(int k = disabledChains; k < activeChains; k++)
    // {
    //   int count = 0;
    //   for(int i = 0; i < BALLS; i++)
    //   {
    //     if(chain[k].ball(i).disabled)
    //       count++;
    //   }
    //   if(count >= BALLS)
    //   {
    //     win();
    //   }
    // }
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
    beams[BEAMS-1] = new ShootySnakeBall(x,y,vx,vy, BEAM_SIZE*2);
  }

  /**
  * Moves the beams along their trajectory, given their velocity and position.
  * @post Beam locations are updated.
  */
  private void moveBeams()
  {
    for(int i = 0; i < BEAMS; i++)
    {
      ShootySnakeBall beam = beams[i];
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
      for(int k = disabledChains; k < activeChains; k++)
      {
        for(int i = 0; i < chain[k].numberOfBalls; i++)
        {
          if(circlesIntersect(chain[k].ball(i), player, BALL_SIZE, PLAYER_SIZE) && !chain[k].ball(i).disabled)
          {
            if(lives > 0)
              respawn();
            else
              lose();
          }
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
      for(int k = disabledChains; k < activeChains; k++)
      {
        for(int j = 0; j < chain[k].numberOfBalls; j++)
        {
          if(circlesIntersect(beams[i], chain[k].ball(j), BEAM_SIZE/2, BALL_SIZE) && !beams[i].disabled && !chain[k].ball(j).disabled)
          {
            beams[i].disabled = true;
            chain[k].ball(j).hit(player.x, player.y);
            if(chain[k].ball(j).disabled && j > 0)
            {
              chain[k].ball(j-1).becomeLeader();
            }
          }
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
  private Boolean circlesIntersect(ShootySnakeBall b1, ShootySnakeBall b2, double size1, double size2)
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
      beams[i] = new ShootySnakeBall(-100, -100, 0, 0, BEAM_SIZE);
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

  private void initLevels()
  {
    chain[0] = new ShootySnakeChain(
      (WIDTH - BALL_SIZE)*2/3,
      (HEIGHT - BALL_SIZE)/2,
      BALL_VELOCITY, BALL_VELOCITY,
      BALL_SIZE,
      25,
      WIDTH,
      HEIGHT);
    chain[1] = new ShootySnakeChain(
      (WIDTH - BALL_SIZE)/2,
      (HEIGHT - BALL_SIZE)/3,
      -BALL_VELOCITY, -BALL_VELOCITY,
      BALL_SIZE,
      25,
      WIDTH,
      HEIGHT);
    chain[2] = new ShootySnakeChain(
      (WIDTH - BALL_SIZE)/2,
      (HEIGHT - BALL_SIZE)*2/3,
      -BALL_VELOCITY, BALL_VELOCITY,
      BALL_SIZE,
      25,
      WIDTH,
      HEIGHT);
    chain[3] = new ShootySnakeChain(
      (WIDTH - BALL_SIZE)/2,
      (HEIGHT - BALL_SIZE)/2,
      BALL_VELOCITY, BALL_VELOCITY,
      BALL_SIZE,
      25,
      WIDTH,
      HEIGHT);
    chain[4] = new ShootySnakeChain(
      (WIDTH - BALL_SIZE)/2,
      (HEIGHT - BALL_SIZE)/2,
      -BALL_VELOCITY, BALL_VELOCITY,
      BALL_SIZE,
      25,
      WIDTH,
      HEIGHT);
    chain[5] = new ShootySnakeChain(
      (WIDTH - BALL_SIZE)/2,
      (HEIGHT - BALL_SIZE)/2,
      BALL_VELOCITY, -BALL_VELOCITY,
      BALL_SIZE,
      25,
      WIDTH,
      HEIGHT);
    chain[6] = new ShootySnakeChain(
      (WIDTH - BALL_SIZE)/2,
      (HEIGHT - BALL_SIZE)/2,
      -BALL_VELOCITY, -BALL_VELOCITY,
      BALL_SIZE,
      25,
      WIDTH,
      HEIGHT);
    chain[7] = new ShootySnakeChain(
      (WIDTH - BALL_SIZE)*2/3,
      (HEIGHT - BALL_SIZE)*2/3,
      BALL_VELOCITY, -BALL_VELOCITY,
      BALL_SIZE,
      100,
      WIDTH,
      HEIGHT);
    chain[8] = new ShootySnakeChain(
      (WIDTH - BALL_SIZE)/3,
      (HEIGHT - BALL_SIZE)/3,
      -BALL_VELOCITY, BALL_VELOCITY,
      BALL_SIZE,
      100,
      WIDTH,
      HEIGHT);
  }

  private void updateLevels()
  {
    if(System.nanoTime() - levelTransitionStart > 4000000000L)
    {
      if(activeChains == 0 && level == 1)
      {
        activeChains = 1;//begin game
        disabledChains = 0;
      }
      else
      {
        if(chain[0].checkDisabled() && level == 1)
        {
          newLevel = true;
          level = 2;
          levelTransitionStart = System.nanoTime();
          System.out.println("entered");
          activeChains = 3;//activate 1 and 2
          disabledChains = 1;
        }
        if(chain[1].checkDisabled() && chain[2].checkDisabled() && level == 2)
        {
          newLevel = true;
          level = 3;
          levelTransitionStart = System.nanoTime();

          activeChains = 7;//activate 3, 4, 5, 6 and 7
          disabledChains = 3;
        }
        if(chain[3].checkDisabled() && chain[4].checkDisabled() && chain[5].checkDisabled() && chain[6].checkDisabled() && level == 3)
        {
          newLevel = true;
          level = 4;
          levelTransitionStart = System.nanoTime();

          activeChains = 9;//activate 8 and 9
          disabledChains = 7;
        }
        if(chain[7].checkDisabled() && chain[8].checkDisabled()  && level == 4)
        {
          activeChains = 9;//win
          disabledChains = 9;
          win();
        }
      }
    }
  }

  public void message(String message, int x, int y, int size)
  {
    int fontSize = size;
    imageGraphics.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
    imageGraphics.setColor(Color.GREEN);
    imageGraphics.drawString(message, x, y);
  }

  public void levelTransition(int num, int x, int y)
  {
    System.out.println("levelTransition: 1");
    if(System.nanoTime() - levelTransitionStart > 4000000000L && !newLevel)
    {
      System.out.println("levelTransition: 2");
      respawnStart = System.nanoTime();
      lastShot = System.nanoTime();
      for(int i = 0; i < BEAMS; i++)
      {
        beams[i] = new ShootySnakeBall(-100, -100, 0, 0, BEAM_SIZE);
        beams[i].disabled = true;
      }
      player.x = x;
      player.y = y;
    }
    else
    {
      newLevel = false;
      System.out.println("levelTransition: 3");
    }

  }
}
