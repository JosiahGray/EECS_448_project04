public class ShootySnakeTester {
    //public Random rn = new Random();
    public ShootySnakeTester(){}

    public void test(ShootySnakeGame game) {
      if(test1())
        System.out.println("Result of normalX method: PASS");
      else
        System.out.println("Result of normalX method: FAIL");
      if(test2())
        System.out.println("Result of normalY method: PASS");
      else
        System.out.println("Result of normalY method: FAIL");
      if(test3(game))
        System.out.println("Result of initFrame(): PASS");
      else
        System.out.println("Result of initFrame(): FAIL");
      if(test4(game))
        System.out.println("Result of initModel(): PASS");
      else
        System.out.println("Result of initModel(): FAIL");
      if(test5())
        System.out.println("Result of hit(): PASS");
      else
        System.out.println("Result of hit(): FAIL");

    }
    //test for public double normalX(double x0, double y0)
    public Boolean test1() {
      double testDir1 = 1;
      double testDir2 = -1;
      double testDir3 = 0;
      ShootySnakeBall ball1 = new ShootySnakeBall(100, 100, 0, 0, 0);
      ShootySnakeBall ball2 = new ShootySnakeBall(-100, -100, 0, 0, 0);
      System.out.println("Test X component of normalized vector between ball at (100,100) and point at (200, 100)");
      double result1 = ball1.normalX(200,100);
      System.out.println("X Component of vector:" + result1);
      System.out.println("Expected: " + testDir1);
      System.out.println("Test X component of normalized vector between balls at (-100,-100) and point at (-200, -100)");
      double result2 = ball2.normalX(-200,-100);
      System.out.println("X Component of vector:" + result2);
      System.out.println("Expected: " + testDir2);
      System.out.println("Test X component of normalized vector between balls at (100,100) and point at (100, 100)");
      double result3 = ball1.normalX(100,100);
      System.out.println("X Component of vector:" + result3);
      System.out.println("Expected: " + testDir3);
      return(result1 == testDir1 && result2 == testDir2 && result3 == testDir3);
    }
    public Boolean test2() {
      double testDir1 = 1;
      double testDir2 = -1;
      double testDir3 = 0;
      ShootySnakeBall ball1 = new ShootySnakeBall(100, 100, 0, 0, 0);
      ShootySnakeBall ball2 = new ShootySnakeBall(-100, -100, 0, 0, 0);
      System.out.println("Test Y component of normalized vector between ball at (100,100) and point at (100, 200)");
      double result1 = ball1.normalY(100,200);
      System.out.println("Y Component of vector:" + result1);
      System.out.println("Expected: " + testDir1);
      System.out.println("Test Y component of normalized vector between balls at (-100,-100) and point at (-100, -200)");
      double result2 = ball2.normalY(-100,-200);
      System.out.println("Y Component of vector:" + result2);
      System.out.println("Expected: " + testDir2);
      System.out.println("Test Y component of normalized vector between balls at (100,100) and point at (100, 100)");
      double result3 = ball1.normalY(100,100);
      System.out.println("Y Component of vector:" + result3);
      System.out.println("Expected: " + testDir3);
      return(result1 == testDir1 && result2 == testDir2 && result3 == testDir3);
    }
    public Boolean test3(ShootySnakeGame game) {
      Boolean isVisible = game.isVisible();
      System.out.println("Test Frame Initialization:");
      if(isVisible)
        System.out.println("Frame visible: TRUE");
      else
        System.out.println("Frame visible: FALSE");
      double ratio = ((double)(java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height + 40.0))/720.0;
      int dimx = (int)(game.getComponent(0).getPreferredSize().getWidth()/ratio + 1);
      int dimy = (int)(game.getComponent(0).getPreferredSize().getHeight()/ratio + 1);
      Boolean dimTest = (dimx == 640) && (dimy == 480);
      if(dimTest)
        System.out.println("Frame dimensions are 640x480: TRUE");
      else
        System.out.println("Frame dimensions are 640x480: FALSE");
      game.setVisible(false);
      return isVisible && dimTest;
    }
    //test initModel
    public Boolean test4(ShootySnakeGame game) {
      Boolean testLives = game.getLives() == 3;
      Boolean testGO = !game.getGameOver();
      System.out.println("Test initModel()");
      if(testLives)
        System.out.println("Variable 'lives' set to 3: TRUE");
      else
        System.out.println("Variable 'lives' set to 3: FALSE");
      if(testGO)
        System.out.println("Variable 'gameOver' set to false: TRUE");
      else
        System.out.println("Variable 'gameOver' set to false: FALSE");
      game.setVisible(false);
      return testLives && testGO;
    }
    public Boolean test5() {
      ShootySnakeEnemy enemy = new ShootySnakeEnemy(1.0, 1.0, 1.0, 1.0, 0.0);
      enemy.leader = true;

      int hitpoints0 = enemy.hitpoints;
      Boolean hp0C = hitpoints0 == 2;
      Boolean disabled0 = enemy.disabled;
      Boolean db0C = !disabled0;
      double vx0 = enemy.vx;
      Boolean vx0C = vx0 == 1.0;
      double vy0 = enemy.vy;
      Boolean vy0C = vy0 == 1.0;

      int points1 = enemy.hit(5, 4, 1);
      Boolean pnts1C = points1 == 0;
      int hitpoints1 = enemy.hitpoints;
      Boolean hp1C = hitpoints1 == 1;
      Boolean disabled1 = enemy.disabled;
      Boolean db1C = !disabled1;
      double vx1 = enemy.vx;
      Boolean vx1C = vx1 == 20.0;
      double vy1 = enemy.vy;
      Boolean vy1C = vy1 == 15.0;

      int points2= enemy.hit(5, 4, 1);
      Boolean pnts2C = points2 == 2;
      int hitpoints2 = enemy.hitpoints;
      Boolean hp2C = hitpoints2 == 0;
      Boolean disabled2 = enemy.disabled;
      Boolean db2C = disabled2;
      double vx2 = enemy.vx;
      Boolean vx2C = vx2 == 20;
      double vy2 = enemy.vy;
      Boolean vy2C = vy2 == 15.0;


      System.out.println("Testing hit() method on ShootySnakeEnemy with on Leader enemy with 2 Total HP at (1,1) from (5,4)");
      System.out.println("Before hit:");
      System.out.println("Initial hitpoints: " + hitpoints0 + ", should be 2");
      System.out.println("Disabled: " + disabled0 + ", should be false");
      System.out.println("Initial vx: " + vx0 + ", should be 1.0");
      System.out.println("Initial vy: " + vy0 + ", should be 1.0");
      System.out.println("One hit:");
      System.out.println("Points gained: " + points1 + ", should be 0");
      System.out.println("Hitpoints left: " + hitpoints1 + ", should be 1");
      System.out.println("Disabled: " + disabled1 + ", should be false");
      System.out.println("1st vx: " + vx1 + ", should be 20.0");
      System.out.println("1st vy: " + vy1 + ", should be 15.0");
      System.out.println("2nd hit:");
      System.out.println("Points gained: " + points2 + ", should be 2");
      System.out.println("Hitpoints left: " + hitpoints2 + ", should be 0");
      System.out.println("Disabled: " + disabled2 + ", should be true");
      System.out.println("2st vx: " + vx2 + ", should be 20.0");
      System.out.println("2nd vy: " + vy2 + ", should be 15.0");
      return (hp0C && db0C && vx0C && vy0C &&
        pnts1C && hp1C && db1C && vx1C && vy1C &&
        pnts2C && hp2C && db2C && vx2C && vy2C);
    }
}
