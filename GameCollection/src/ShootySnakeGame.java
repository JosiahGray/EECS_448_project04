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
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ShootySnakeGame extends JFrame {
  /**
  * ratio keeps track of a rough estimate of the scaling factor needed to adapt to different resolutions.  Height is used as the limiting factor.
  */
  private static final double ratio = ((double)(java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height + 40.0))/720.0;
  /**
  * WIDTH is the preferred width of the window
  */
  private static final int WIDTH = (int)(640*ratio);
  /**
  * HEIGHT is the preferred height of the window
  */
  private static final int HEIGHT = (int)(480*ratio);
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
  private final double BALL_VELOCITY = 2*ratio;
  /**
  * Controls the tightness of the chain.  1 is the tightest. Higher is loose.
  */
  private final double DIV = 10.0;
  /**
  * How fast the player moves.
  */
  private final double PLAYER_VELOCITY = 10*ratio;
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
  * The length of time the player should be invulnerable to harm and can't shoot beams after spawning.
  */
  private final long spawnInvulnerabilityCounter = 2000000000L;
  /**
  * Contains the nano second (according to nanoTime()) since a player respawned.
  */
  private long respawnStart;
  /**
  * Contains the nano second (according to nanoTime()) since the level transition period began.
  */
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
  /**
  * Contains a true/false value depending on the state of the left mouse button.
  */
  private volatile Boolean mouseDown = false;
  /**
  * Contains the current x position of the mouse on the screen.
  */
  private volatile int mouseX;
  /**
  * Contains the current y position of the mouse on the screen.
  */
  private volatile int mouseY;
  /**
  * Contains the number of active chains on the screen, including disabled chains.
  */
  private int activeChains = 0;
  /**
  * Contains the number of active chains that have been disabled.
  */
  private int disabledChains = 0;
  /**
  * True if a new level should be transitioned to. False else.
  */
  private Boolean newLevel = true;
  /**
  * Contains the integer ID of the current level (These are currently: 1, 2, 3, 4)
  */
  private int level = 1;
  /**
  * Contains and integer counting of the current player's point total.
  */
  private int points = 0;
  /**
  * Contains a list of even strings, alternating between a player's name and their highscore (as a string)
  */
  private String[] highscores;
  /**
  * Contains an often used font size
  */
  private final int fontsize = (int)(20*ratio);
  /**
  * Contains an often used Font object.
  */
  private final Font font = new Font("TimesRoman", Font.PLAIN, fontsize);
  /**
  * Contains an often used FontMeterics.
  */
  private final FontMetrics fontmetrics = getFontMetrics(font);

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
  * updateModel Updates the game model containing the enemy balls, player, beams, levels and the win/lose state.
  * @post The game model is updated for the current frame.
  */
  private void updateModel() {

    // Move the balls
    moveBalls();
    // Check if level is disabled
    if(newLevel)
    {
      levelTransition(WIDTH/2, HEIGHT/2);
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
      addBeam(player.x + player.radius, player.y + player.radius, 4*player.normalX(mouseX,mouseY)*ratio, 4*player.normalY(mouseX,mouseY)*ratio);
    }
    moveBeams();
    findCollisions();
  }

  /**
  * renderFrame Obtains information produced by the updateModel method to produce an image of each frame's state of play. Also handles level screens and the game over screen with the highscores.
  * @post The graphics container is updated for the current frame.
  */
  private void renderFrame() {
    imageGraphics.setColor(Color.BLACK);
    imageGraphics.fillRect(0, 0, WIDTH, HEIGHT);
    String message = "";
    if(newLevel && System.nanoTime() - levelTransitionStart < spawnInvulnerabilityCounter)
    {
      message = "Level " + level;
      message(message, WIDTH/2 - fontmetrics.stringWidth(message)/2, HEIGHT/2, fontsize);
    }
    else if(newLevel && System.nanoTime() - levelTransitionStart >= spawnInvulnerabilityCounter)
    {
      newLevel = false;
      levelTransition(WIDTH/2, HEIGHT/2);
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
      message = "LIVES " + lives;
      message(message, (int)(10.0*ratio), (int)(20.0*ratio), fontsize);
      message = "Points " + points;
      message(message, (int)(WIDTH - 100.0*ratio), (int)(20*ratio), fontsize);

    }
    else if(gameOver)
    {
      message = "";
      if(!playerWon)
        message = "Game Over!";
      else
        message = "You Won!";

      message(message, WIDTH/2 - fontmetrics.stringWidth(message)/2, HEIGHT/2 - fontsize*5, fontsize);
      message = "High Scores";
      message(message, WIDTH/2 - fontmetrics.stringWidth(message)/2, HEIGHT/2 - fontsize*4, fontsize);
      //Do file IO, if can't make a file, then skip and just display score
      try {
        highscores = readHighScores();
      } catch (IOException x) {
        ;
      }
      if(isNewHighScore(points, highscores))
      {
        String name = JOptionPane.showInputDialog(panel, "Enter name:", "New High Score!", JOptionPane.PLAIN_MESSAGE);
        highscores = setHighScore(name + " ", points);
      }
      printHighScores(highscores);

      try {
        writeHighScores();
      } catch (IOException x) {
        System.out.println("Can't write highscores to file!");
      }
    }

    // Draw to screen
    Graphics panelGraphics = panel.getGraphics();
    if (panelGraphics != null) {
      panelGraphics.drawImage(image, 0, 0, null);
      panelGraphics.dispose();
    }
  }

  /**
  * initFrame Initializes the JFrame with proper naming, setting Resizeable to false, setting the size to WIDTH and HEIGHT and putting the window in the center.
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
  * initImage Initializes the Image image which will house the graphics that will be drawn to the JFrame.
  * @post The Image is initialized.
  */
  private void initImage() {
    image = createImage(WIDTH, HEIGHT);
    imageGraphics = image.getGraphics();
  }

  /**
  * initModel Initializes the game model.  All values are default.
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
  * moveBalls Moves the balls in the balls array according to their position, velocity and disabled status of their leading members.
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
          while(ball.vx*ball.vx + ball.vy*ball.vy < 4*ratio*ratio)
          {
            ball.vx *= 1.10;
            ball.vy *= 1.10;
          }
          while(ball.vx*ball.vx + ball.vy*ball.vy > 800*ratio*ratio)
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
          if(i == chain[k].numberOfBalls-1 && chain[k].ball(i).hitpoints == 3)
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
  * addBeam Removes the oldest beam object from the end of the array and adding a newly created beam object to its beginning.
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
  * moveBeams Moves the beams along their trajectory, given their velocity and position.
  * @post Beam locations are updated.
  */
  private void moveBeams() {
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
  private void findCollisions() {
      ballHitsPlayer();
      beamHitsBall();
  }

  /**
  * Checks if any enabled balls in the balls array have hit the player this frame.
  * @post If the player is hit, they are respawned or lose, else nothing happens.
  */
  private void ballHitsPlayer() {
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
  * beamHitsBall Checks if any enabled balls in the balls array have hit any enabled beams in the beams array.
  * @post Any hit balls are disabled.
  */
  private void beamHitsBall() {
    for(int i = 0; i < BEAMS; i++)
    {
      for(int k = disabledChains; k < activeChains; k++)
      {
        for(int j = 0; j < chain[k].numberOfBalls; j++)
        {
          if(circlesIntersect(beams[i], chain[k].ball(j), BEAM_SIZE/2, BALL_SIZE) && !beams[i].disabled && !chain[k].ball(j).disabled)
          {
            beams[i].disabled = true;
            points += chain[k].ball(j).hit(player.x, player.y, ratio);
            if(chain[k].ball(j).disabled && j > 0)
            {
              chain[k].ball(j-1).becomeLeader();
              chain[k].ball(j-1).neighbors--;
              if(chain[k].ball(j-1).neighbors == 0)
                chain[k].ball(j-1).becomeLoneWolf();
              if(j < chain[k].numberOfBalls - 1)
              {
                chain[k].ball(j+1).neighbors--;
                if(chain[k].ball(j+1).neighbors == 0)
                  chain[k].ball(j+1).becomeLoneWolf();
              }
            }
            else if(chain[k].ball(j).disabled && j == 0)
            {
              chain[k].ball(j + 1).neighbors--;
              if(chain[k].ball(j + 1).neighbors == 0)
                chain[k].ball(j + 1).becomeLoneWolf();
            }
          }
        }
      }
    }

  }

  /**
  * circlesIntersect Checks for intersection between two circles.
  * @param b1 The first Ball object to check.
  * @param b2 The second Ball object to check.
  * @param size1 The radius of the first Ball object.
  * @param size2 The radius of the second Ball object.
  * @return True if the Ball objects are intersecting, False otherwise.
  */
  private Boolean circlesIntersect(ShootySnakeBall b1, ShootySnakeBall b2, double size1, double size2) {
    return java.awt.geom.Point2D.distance(b1.x + size1, b1.y + size1, b2.x + size2, b2.y + size2) < size1 + size2;
  }

  /**
  * respawn Respawns the player in the center of the screen with a cooldown period until they can be hit again and before they can fire again.
  * @post Number of lives reduced by 1, the beams array is reset to default.  The player is placed in the center of the screen and the timer for respawn invulnerability is set.
  */
  private void respawn() {
    if(System.nanoTime() - respawnStart > spawnInvulnerabilityCounter)
    {
      lives--;
    }
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
  * lose Sets the flags indicating end game lose.
  * @post The game ends and the lose screen is brought up.
  */
  private void lose() {
    gameOver = true;
    playerWon = false;
  }

  /**
  * win Sets the flags indicating end game victory.
  * @post The game ends and the victory screen is brought up.
  */
  private void win() {
    gameOver = true;
    playerWon = true;
  }

  /**
  * initLevels fills the chain array chain
  * with ShootySnakeChains of ShootySnakeEnemy's.
  * The chains are used by index in the updateLevels
  * method to construct the levels.
  * @post The chain array is filled with the necessary ShootySnakeChain objects to make the levels.
  */
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

  /**
  * updateLevels Keeps track of which level the player should be on, moving
  * to the next and activating the next collection of chains when appropriate
  * conditions are met.
  * @post If the current level's chains are disabled, then the next level is activated.
  */
  private void updateLevels() {
    if(System.nanoTime() - levelTransitionStart > spawnInvulnerabilityCounter)
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

  /**
  * message will create a GREEN colored, TimesRoman fonted message of size msize at the coordinates x and y on the screen.
  * @param message The String to draw to the screen.
  * @param x The x coordinate to draw the string at.
  * @param y The y coordinate to draw the string at.
  * @param msize The size of the text the string will be drawn.
  */
  public void message(String message, int x, int y, int msize) {
    int mfontSize = msize;
    imageGraphics.setFont(new Font("TimesRoman", Font.PLAIN, mfontSize));
    imageGraphics.setColor(Color.GREEN);
    imageGraphics.drawString(message, x, y);
  }

  /**
  * levelTransition When updateLevels calls for a new level, levelTransition
  * will reposition the player and activate the invulnerability timer when appropriate.
  * @param x The x coordinate to reposition the player at (default is WIDTH/2)
  * @param y The y coordinate to reposition the player at (default is HEIGHT/2)
  * @post The player is repositioned to the coordinates (x,y) The player's beams are reset and he is invulnerable for a time.
  */
  public void levelTransition(int x, int y) {
    if(System.nanoTime() - levelTransitionStart > spawnInvulnerabilityCounter && !newLevel)
    {
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
      newLevel = false;
  }

  /**
  * readHighScores  Reads in the "highscores.txt" file, if available, from the src directory to obtain the current highscores list.
  * @return A String array with names and scores from file. Can handle empty, but not oddly lined files.
  */
  private String[] readHighScores() throws IOException {
    File file = new File("highscores.txt");
    String[] hs;
    if(file.exists())
    {
      BufferedReader br = new BufferedReader(new FileReader("highscores.txt"));

      //count file contents
      int count = 0;
      while((br.readLine()) != null)
        count++;
      hs = new String[count];
      br.close();

      //read file
      br = new BufferedReader(new FileReader("highscores.txt"));
      for(int i = 0; i < count; i++)
        hs[i] = br.readLine();
      br.close();
    }
    else
      hs = new String[0];
    return hs; //return string array with file contents
  }

  /**
  * isNewHighScore Determines if the passed in score, mpoints, belongs in the top five positions of the passed in String array, hs, of highscores.
  * @pre hs contains an even number of lines where every other line is a positive integer
  * @return True if mpoints belongs in the top five highscores.  False else.
  */
  private Boolean isNewHighScore(int mpoints, String[] hs) {
    int count = hs.length;
    if(count > 10)
      count = 10;
    Boolean addScore = false;
    for(int i = 0; i < count; i++)
    {
      i++;
      if(mpoints > Integer.parseInt(hs[i]))
        addScore = true;
    }
    return addScore;
  }

  /**
  * setHighScore Places mname and mpoints in the appropriate spots the class's highscores variable.
  * @pre mpoints belongs among the top five positions of the highscores class variable array.
  * @return Returns an updated highscores String array, which can then be stored in the highscores member array.
  */
  private String[] setHighScore(String mname, int mpoints) {
    String[] temp = new String[highscores.length + 2];
    int offset = 0;
    Boolean foundPlacement = false;
    if(highscores.length > 0)
    {
      for(int i = 0; i < highscores.length; i = i + 2)
      {
        if(mpoints >= Integer.parseInt(highscores[i + 1]) && !foundPlacement)
        {
          temp[i] = mname;
          temp[i + 1] = mpoints + "";
          temp[i + 2] = highscores[i];
          temp[i + 3] = highscores[i + 1];
          offset = 2;
          foundPlacement = true;
        }
        else
        {
          temp[i + offset] = highscores[i];
          temp[i + 1 + offset] = highscores[i + 1];
        }
      }
    }
    else
    {
      temp[0] = mname;
      temp[1] = mpoints + "";
    }
    return temp;
  }

  /**
  * printHighScores Places the High Scores information from the String array member variable, highscores, in a formatted table on the member image.
  * @post The High Scores are drawn to the screen.
  */
  private void printHighScores(String[] hs) {
    String message = "Name:";
    message(message, WIDTH*1/4 - fontmetrics.stringWidth(message)/2, HEIGHT/2 - fontsize*3, fontsize);
    message = "Score:";
    message(message, WIDTH*3/4 - fontmetrics.stringWidth(message)/2, HEIGHT/2 - fontsize*3, fontsize);
    int topFive = 10;
    String tempName = "";
    String tempSpace = "";
    if(highscores.length < 10)
      topFive = highscores.length;
    for(int i = 0; i < topFive; i = i + 2)
    {
      message = (i + 2)/2 + ". " + hs[i];
      message(message, WIDTH*1/4 - fontmetrics.stringWidth("Name:")/2, HEIGHT/2 - fontsize*2 + (i + 1)*fontsize, fontsize);
      message = hs[i+1];
      message(message, WIDTH*3/4 - fontmetrics.stringWidth(message)/2, HEIGHT/2 - fontsize*2 + (i + 1)*fontsize, fontsize);
    }
  }

  /**
  * writeHighScores Writes the current String array highscores to the file "highscores.txt" in the local directory.
  * @post The current highscores array is stored in a local file called "highscores.txt"
  */
  private void writeHighScores() throws IOException {
    File file = new File("highscores.txt");
    if(file.exists())
      file.delete();
    FileWriter fw = new FileWriter(file);
    for(int i = 0; i < highscores.length - 1; i++)
    {
      fw.write(highscores[i]);
      fw.write(System.lineSeparator());
    }
    fw.write(highscores[highscores.length - 1]);
    fw.close();
  }

  //getters for code testing:
  /**
  * getLives Gets the current value of the lives member variable
  * @return The current value of the lives member variable, an integer.
  */
  public int getLives()
  {
    return lives;
  }

  /**
  * getGameOver Gets the current value of the gameOver member variable
  * @return The current value of the gameOver member variable, a Boolean.
  */
  public Boolean getGameOver()
  {
    return gameOver;
  }

  // public double getRatio()
  // {
  //   return ratio;
  // }
  //
  // public int getWidth()
  // {
  //   return WIDTH;
  // }
  // public int getHeight()
  // {
  //   return HEIGHT;
  // }
  // public int getFPS()
  // {
  //   return FRAMES_PER_SECOND;
  // }
  // public long getFP()
  // {
  //   return FRAME_PERIOD;
  // }
  // public boolean[] getKeys()
  // {
  //   return KEYS;
  // }
  // public int getBALLS()
  // {
  //   return BALLS;
  // }
  // public int getBEAMS()
  // {
  //   return BEAMS;
  // }
  // public double getBallSize()
  // {
  //   return BALL_SIZE;
  // }
  // public double getBeamSize()
  // {
  //   return BEAM_SIZE;
  // }
  // public double getPlayerSize()
  // {
  //   return PLAYER_SIZE;
  // }
  // public double getDIV()
  // {
  //   return DIV;
  // }
  // public double getPV()
  // {
  //   return PLAYER_VELOCITY;
  // }
  // public JPanel getPanel()
  // {
  //   return panel;
  // }
  // public Image getImage()
  // {
  //   return image;
  // }
  // public Graphics getImageGraphics()
  // {
  //   return imageGraphics;
  // }
  // public ShootySnakeChain[] getChain()
  // {
  //   return chain;
  // }
  // public ShootySnakeBall[] getBeamArray()
  // {
  //   return beams;
  // }
  // public ShootySnakeBall getPlayer()
  // {
  //   return player;
  // }
  // public long getLastShot()
  // {
  //   return lastShot;
  // }
  // public long getSIC()
  // {
  //   return spawnInvulnerabilityCounter;
  // }
  // public long getRespawnStart()
  // {
  //   return respawnStart;
  // }
  // public long getLevelTransitionStart()
  // {
  //   return levelTransitionStart;
  // }

  // public Boolean getPlayerWon()
  // {
  //   return playerWon;
  // }
  // public Boolean getMouseDown()
  // {
  //   return mouseDown;
  // }
  // public int getMouseX()
  // {
  //   return mouseX;
  // }
  // public int getMouseY()
  // {
  //   return mouseY;
  // }
  // public int getActiveChains()
  // {
  //   return activeChains;
  // }
  // public int getDisabledChains()
  // {
  //   return disabledChains;
  // }
  // public Boolean getNewLevel()
  // {
  //   return newLevel;
  // }
  // public int getLevel()
  // {
  //   return level;
  // }
  // public int getPoints()
  // {
  //   return points;
  // }
  // public String[] getHighScores()
  // {
  //   return highscores;
  // }
  // public int getFontSize()
  // {
  //   return fontsize;
  // }
  // public Font getFONT()
  // {
  //   return font;
  // }
  // public FontMetrics getFONTM()
  // {
  //   return fontmetrics;
  // }
}
