/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4262.30c9ffc7c modeling language!*/

package ca.mcgill.ecse223.block.model;
import java.io.Serializable;
import java.awt.geom.*;
import ca.mcgill.ecse223.block.model.BouncePoint.BounceDirection;
import java.util.*;

// line 25 "../../../../../Block223PlayMode.ump"
// line 121 "../../../../../Block223Persistence.ump"
// line 1 "../../../../../Block223States.ump"
public class PlayedGame implements Serializable
{

  //------------------------
  // STATIC VARIABLES
  //------------------------


  /**
   * at design time, the initial wait time may be adjusted as seen fit
   */
  public static final int INITIAL_WAIT_TIME = 10;
  private static int nextId = 1;
  public static final int NR_LIVES = 3;

  /**
   * the PlayedBall and PlayedPaddle are not in a separate class to avoid the bug in Umple that occurred for the second constructor of Game
   * no direct link to Ball, because the ball can be found by navigating to PlayedGame, Game, and then Ball
   */
  public static final int BALL_INITIAL_X = Game.PLAY_AREA_SIDE / 2;
  public static final int BALL_INITIAL_Y = Game.PLAY_AREA_SIDE / 2;

  /**
   * no direct link to Paddle, because the paddle can be found by navigating to PlayedGame, Game, and then Paddle
   * pixels moved when right arrow key is pressed
   */
  public static final int PADDLE_MOVE_RIGHT = 5;

  /**
   * pixels moved when left arrow key is pressed
   */
  public static final int PADDLE_MOVE_LEFT = -5;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PlayedGame Attributes
  private int score;
  private int lives;
  private int currentLevel;
  private double waitTime;
  private String playername;
  private double ballDirectionX;
  private double ballDirectionY;
  private double currentBallX;
  private double currentBallY;
  private double currentPaddleLength;
  private double currentPaddleX;
  private double currentPaddleY;

  //Autounique Attributes
  private int id;

  //PlayedGame State Machines
  public enum PlayStatus { Ready, Moving, Paused, GameOver }
  private PlayStatus playStatus;

  //PlayedGame Associations
  private Player player;
  private Game game;
  private List<PlayedBlockAssignment> blocks;
  private BouncePoint bounce;
  private Block223 block223;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PlayedGame(String aPlayername, Game aGame, Block223 aBlock223)
  {
    // line 61 "../../../../../Block223PlayMode.ump"
    boolean didAddGameResult = setGame(aGame);
          if (!didAddGameResult)
          {
             throw new RuntimeException("Unable to create playedGame due to game");
          }
    // END OF UMPLE BEFORE INJECTION
    score = 0;
    lives = NR_LIVES;
    currentLevel = 1;
    waitTime = INITIAL_WAIT_TIME;
    playername = aPlayername;
    resetBallDirectionX();
    resetBallDirectionY();
    resetCurrentBallX();
    resetCurrentBallY();
    currentPaddleLength = getGame().getPaddle().getMaxPaddleLength();
    resetCurrentPaddleX();
    currentPaddleY = Game.PLAY_AREA_SIDE - Paddle.VERTICAL_DISTANCE - Paddle.PADDLE_WIDTH;
    id = nextId++;
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create playedGame due to game");
    }
    blocks = new ArrayList<PlayedBlockAssignment>();
    boolean didAddBlock223 = setBlock223(aBlock223);
    if (!didAddBlock223)
    {
      throw new RuntimeException("Unable to create playedGame due to block223");
    }
    setPlayStatus(PlayStatus.Ready);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setScore(int aScore)
  {
    boolean wasSet = false;
    score = aScore;
    wasSet = true;
    return wasSet;
  }

  public boolean setLives(int aLives)
  {
    boolean wasSet = false;
    lives = aLives;
    wasSet = true;
    return wasSet;
  }

  public boolean setCurrentLevel(int aCurrentLevel)
  {
    boolean wasSet = false;
    currentLevel = aCurrentLevel;
    wasSet = true;
    return wasSet;
  }

  public boolean setWaitTime(double aWaitTime)
  {
    boolean wasSet = false;
    waitTime = aWaitTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setPlayername(String aPlayername)
  {
    boolean wasSet = false;
    playername = aPlayername;
    wasSet = true;
    return wasSet;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setBallDirectionX(double aBallDirectionX)
  {
    boolean wasSet = false;
    ballDirectionX = aBallDirectionX;
    wasSet = true;
    return wasSet;
  }

  public boolean resetBallDirectionX()
  {
    boolean wasReset = false;
    ballDirectionX = getDefaultBallDirectionX();
    wasReset = true;
    return wasReset;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setBallDirectionY(double aBallDirectionY)
  {
    boolean wasSet = false;
    ballDirectionY = aBallDirectionY;
    wasSet = true;
    return wasSet;
  }

  public boolean resetBallDirectionY()
  {
    boolean wasReset = false;
    ballDirectionY = getDefaultBallDirectionY();
    wasReset = true;
    return wasReset;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setCurrentBallX(double aCurrentBallX)
  {
    boolean wasSet = false;
    currentBallX = aCurrentBallX;
    wasSet = true;
    return wasSet;
  }

  public boolean resetCurrentBallX()
  {
    boolean wasReset = false;
    currentBallX = getDefaultCurrentBallX();
    wasReset = true;
    return wasReset;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setCurrentBallY(double aCurrentBallY)
  {
    boolean wasSet = false;
    currentBallY = aCurrentBallY;
    wasSet = true;
    return wasSet;
  }

  public boolean resetCurrentBallY()
  {
    boolean wasReset = false;
    currentBallY = getDefaultCurrentBallY();
    wasReset = true;
    return wasReset;
  }

  public boolean setCurrentPaddleLength(double aCurrentPaddleLength)
  {
    boolean wasSet = false;
    currentPaddleLength = aCurrentPaddleLength;
    wasSet = true;
    return wasSet;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setCurrentPaddleX(double aCurrentPaddleX)
  {
    boolean wasSet = false;
    currentPaddleX = aCurrentPaddleX;
    wasSet = true;
    return wasSet;
  }

  public boolean resetCurrentPaddleX()
  {
    boolean wasReset = false;
    currentPaddleX = getDefaultCurrentPaddleX();
    wasReset = true;
    return wasReset;
  }

  public int getScore()
  {
    return score;
  }

  public int getLives()
  {
    return lives;
  }

  public int getCurrentLevel()
  {
    return currentLevel;
  }

  public double getWaitTime()
  {
    return waitTime;
  }

  /**
   * added here so that it only needs to be determined once
   */
  public String getPlayername()
  {
    return playername;
  }

  /**
   * 0/0 is the top left corner of the play area, i.e., a directionX/Y of 0/1 moves the ball down in a straight line
   */
  public double getBallDirectionX()
  {
    return ballDirectionX;
  }
  /* Code from template attribute_GetDefaulted */
  public double getDefaultBallDirectionX()
  {
    return getGame().getBall().getMinBallSpeedX();
  }

  public double getBallDirectionY()
  {
    return ballDirectionY;
  }
  /* Code from template attribute_GetDefaulted */
  public double getDefaultBallDirectionY()
  {
    return getGame().getBall().getMinBallSpeedY();
  }

  /**
   * the position of the ball is at the center of the ball
   */
  public double getCurrentBallX()
  {
    return currentBallX;
  }
  /* Code from template attribute_GetDefaulted */
  public double getDefaultCurrentBallX()
  {
    return BALL_INITIAL_X;
  }

  public double getCurrentBallY()
  {
    return currentBallY;
  }
  /* Code from template attribute_GetDefaulted */
  public double getDefaultCurrentBallY()
  {
    return BALL_INITIAL_Y;
  }

  public double getCurrentPaddleLength()
  {
    return currentPaddleLength;
  }

  /**
   * the position of the paddle is at its top right corner
   */
  public double getCurrentPaddleX()
  {
    return currentPaddleX;
  }
  /* Code from template attribute_GetDefaulted */
  public double getDefaultCurrentPaddleX()
  {
    return (Game.PLAY_AREA_SIDE - currentPaddleLength) / 2;
  }

  public double getCurrentPaddleY()
  {
    return currentPaddleY;
  }

  public int getId()
  {
    return id;
  }

  public String getPlayStatusFullName()
  {
    String answer = playStatus.toString();
    return answer;
  }

  public PlayStatus getPlayStatus()
  {
    return playStatus;
  }

  public boolean play()
  {
    boolean wasEventProcessed = false;
    
    PlayStatus aPlayStatus = playStatus;
    switch (aPlayStatus)
    {
      case Ready:
        setPlayStatus(PlayStatus.Moving);
        wasEventProcessed = true;
        break;
      case Paused:
        setPlayStatus(PlayStatus.Moving);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean pause()
  {
    boolean wasEventProcessed = false;
    
    PlayStatus aPlayStatus = playStatus;
    switch (aPlayStatus)
    {
      case Moving:
        setPlayStatus(PlayStatus.Paused);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean move()
  {
    boolean wasEventProcessed = false;
    
    PlayStatus aPlayStatus = playStatus;
    switch (aPlayStatus)
    {
      case Moving:
        if (hitPaddle())
        {
        // line 13 "../../../../../Block223States.ump"
          doHitPaddleOrWall();
          setPlayStatus(PlayStatus.Moving);
          wasEventProcessed = true;
          break;
        }
        if (isOutOfBoundsAndLastLife())
        {
        // line 14 "../../../../../Block223States.ump"
          doOutOfBounds();
          setPlayStatus(PlayStatus.GameOver);
          wasEventProcessed = true;
          break;
        }
        if (isOutOfBounds())
        {
        // line 15 "../../../../../Block223States.ump"
          doOutOfBounds();
          setPlayStatus(PlayStatus.Paused);
          wasEventProcessed = true;
          break;
        }
        if (hitLastBlockAndLastLevel())
        {
        // line 16 "../../../../../Block223States.ump"
          doHitBlock();
          setPlayStatus(PlayStatus.GameOver);
          wasEventProcessed = true;
          break;
        }
        if (hitLastBlock())
        {
        // line 17 "../../../../../Block223States.ump"
          doHitBlockNextLevel();
          setPlayStatus(PlayStatus.Ready);
          wasEventProcessed = true;
          break;
        }
        if (hitBlock())
        {
        // line 18 "../../../../../Block223States.ump"
          doHitBlock();
          setPlayStatus(PlayStatus.Moving);
          wasEventProcessed = true;
          break;
        }
        if (hitWall())
        {
        // line 19 "../../../../../Block223States.ump"
          doHitPaddleOrWall();
          setPlayStatus(PlayStatus.Moving);
          wasEventProcessed = true;
          break;
        }
        // line 20 "../../../../../Block223States.ump"
        doHitNothingAndNotOutOfBounds();
        setPlayStatus(PlayStatus.Moving);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void setPlayStatus(PlayStatus aPlayStatus)
  {
    playStatus = aPlayStatus;

    // entry actions and do activities
    switch(playStatus)
    {
      case Ready:
        // line 8 "../../../../../Block223States.ump"
        doSetup();
        break;
      case GameOver:
        // line 26 "../../../../../Block223States.ump"
        doGameOver();
        break;
    }
  }
  /* Code from template association_GetOne */
  public Player getPlayer()
  {
    return player;
  }

  public boolean hasPlayer()
  {
    boolean has = player != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_GetMany */
  public PlayedBlockAssignment getBlock(int index)
  {
    PlayedBlockAssignment aBlock = blocks.get(index);
    return aBlock;
  }

  public List<PlayedBlockAssignment> getBlocks()
  {
    List<PlayedBlockAssignment> newBlocks = Collections.unmodifiableList(blocks);
    return newBlocks;
  }

  public int numberOfBlocks()
  {
    int number = blocks.size();
    return number;
  }

  public boolean hasBlocks()
  {
    boolean has = blocks.size() > 0;
    return has;
  }

  public int indexOfBlock(PlayedBlockAssignment aBlock)
  {
    int index = blocks.indexOf(aBlock);
    return index;
  }
  /* Code from template association_GetOne */
  public BouncePoint getBounce()
  {
    return bounce;
  }

  public boolean hasBounce()
  {
    boolean has = bounce != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Block223 getBlock223()
  {
    return block223;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setPlayer(Player aPlayer)
  {
    boolean wasSet = false;
    Player existingPlayer = player;
    player = aPlayer;
    if (existingPlayer != null && !existingPlayer.equals(aPlayer))
    {
      existingPlayer.removePlayedGame(this);
    }
    if (aPlayer != null)
    {
      aPlayer.addPlayedGame(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    if (aGame == null)
    {
      return wasSet;
    }

    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame))
    {
      existingGame.removePlayedGame(this);
    }
    game.addPlayedGame(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBlocks()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public PlayedBlockAssignment addBlock(int aX, int aY, Block aBlock)
  {
    return new PlayedBlockAssignment(aX, aY, aBlock, this);
  }

  public boolean addBlock(PlayedBlockAssignment aBlock)
  {
    boolean wasAdded = false;
    if (blocks.contains(aBlock)) { return false; }
    PlayedGame existingPlayedGame = aBlock.getPlayedGame();
    boolean isNewPlayedGame = existingPlayedGame != null && !this.equals(existingPlayedGame);
    if (isNewPlayedGame)
    {
      aBlock.setPlayedGame(this);
    }
    else
    {
      blocks.add(aBlock);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBlock(PlayedBlockAssignment aBlock)
  {
    boolean wasRemoved = false;
    //Unable to remove aBlock, as it must always have a playedGame
    if (!this.equals(aBlock.getPlayedGame()))
    {
      blocks.remove(aBlock);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBlockAt(PlayedBlockAssignment aBlock, int index)
  {  
    boolean wasAdded = false;
    if(addBlock(aBlock))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBlocks()) { index = numberOfBlocks() - 1; }
      blocks.remove(aBlock);
      blocks.add(index, aBlock);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBlockAt(PlayedBlockAssignment aBlock, int index)
  {
    boolean wasAdded = false;
    if(blocks.contains(aBlock))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBlocks()) { index = numberOfBlocks() - 1; }
      blocks.remove(aBlock);
      blocks.add(index, aBlock);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBlockAt(aBlock, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setBounce(BouncePoint aNewBounce)
  {
    boolean wasSet = false;
    bounce = aNewBounce;
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setBlock223(Block223 aBlock223)
  {
    boolean wasSet = false;
    if (aBlock223 == null)
    {
      return wasSet;
    }

    Block223 existingBlock223 = block223;
    block223 = aBlock223;
    if (existingBlock223 != null && !existingBlock223.equals(aBlock223))
    {
      existingBlock223.removePlayedGame(this);
    }
    block223.addPlayedGame(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    if (player != null)
    {
      Player placeholderPlayer = player;
      this.player = null;
      placeholderPlayer.removePlayedGame(this);
    }
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removePlayedGame(this);
    }
    while (blocks.size() > 0)
    {
      PlayedBlockAssignment aBlock = blocks.get(blocks.size() - 1);
      aBlock.delete();
      blocks.remove(aBlock);
    }
    
    bounce = null;
    Block223 placeholderBlock223 = block223;
    this.block223 = null;
    if(placeholderBlock223 != null)
    {
      placeholderBlock223.removePlayedGame(this);
    }
  }

  // line 127 "../../../../../Block223Persistence.ump"
   public static  void reinitializeAutouniqueId(List<PlayedGame> games){
    nextId = 0;
    for (PlayedGame g : games) {
        if (g.getId() > nextId) {
          nextId = g.getId();
        }
    }
    nextId++;
    System.out.println("Next block ID = " + nextId);
  }


  /**
   * Guards
   */
  // line 33 "../../../../../Block223States.ump"
   private boolean hitPaddle(){
    BouncePoint bp = calculateBouncePointPaddle();
    setBounce(bp);
    return getBounce() != null;
  }

  // line 39 "../../../../../Block223States.ump"
   private boolean isOutOfBoundsAndLastLife(){
    // boolean outOfBounds = false;
    // if (lives == 1){
    //   outOfBounds = isOutOfBounds();
    // }
    return isOutOfBounds() && lives == 1;
  }

  // line 47 "../../../../../Block223States.ump"
   private boolean isOutOfBounds(){
    // boolean outOfBounds = false;
    double y = getCurrentBallY();
    double dy = getBallDirectionY();
    // Game game = getGame();
    // Ball ball = game.getBall();
    return y + Ball.BALL_DIAMETER/2 + dy > Game.PLAY_AREA_SIDE;
  }

  // line 56 "../../../../../Block223States.ump"
   private boolean hitLastBlockAndLastLevel(){
    Game game = this.getGame();
    if (currentLevel == game.numberOfLevels() && numberOfBlocks() == 1) {
      BouncePoint bp = calculateBouncePointBlock(blocks.get(0));
      setBounce(bp);
      return getBounce() != null;
    }
    return false;
  }

  // line 66 "../../../../../Block223States.ump"
   private boolean hitLastBlock(){
    // int nrBlocks = this.numberOfBlocks();
    // this.setBounce(null);
    if (numberOfBlocks() == 0) {
      return true;
    }
    if(numberOfBlocks() == 1) {
      BouncePoint bp = calculateBouncePointBlock(blocks.get(0));
      setBounce(bp);
      return getBounce() != null;
    }
    return false;
  }

  // line 80 "../../../../../Block223States.ump"
   private boolean hitBlock(){
    // int nrBlocks = this.numberOfBlocks();
    for(int i = 0; i < blocks.size(); i++){
      BouncePoint bp = calculateBouncePointBlock(blocks.get(i));
      // BouncePoint bounce = this.getBounce();
      // boolean closer = isCloser(bp, bounce);
      // if(closer){
      //   this.setBounce(bp);
      // }
      if (bp != null) {
        setBounce(bp);
        return true;
      }
    }
    return false;
  }

  // line 97 "../../../../../Block223States.ump"
   private boolean hitWall(){
    BouncePoint bp = calculateBouncePointWall();
    setBounce(bp);
    return getBounce() != null;
  }


  /**
   * Actions
   */
  // line 105 "../../../../../Block223States.ump"
   private void doSetup(){
    resetCurrentBallX();
    resetCurrentBallY();
    resetBallDirectionX();
    resetBallDirectionY();
    resetCurrentPaddleX();
    Game g = getGame();
    Level l = g.getLevel(currentLevel - 1);

    for (BlockAssignment ba : l.getBlockAssignments()) {
      new PlayedBlockAssignment(
          Game.WALL_PADDING + (Block.SIZE + Game.COLUMNS_PADDING) * (ba.getGridHorizontalPosition() - 1),
          Game.WALL_PADDING + (Block.SIZE + Game.ROW_PADDING) * (ba.getGridVerticalPosition() - 1), ba.getBlock(),
          this);
    }
    outerloop: while (numberOfBlocks() < game.getNrBlocksPerLevel()) {
      int randX = (int) (Math.random() * 15) + 1;
      int randY = (int) (Math.random() * 9) + 1;

      int coordX = Game.WALL_PADDING + (Block.SIZE + Game.COLUMNS_PADDING) * (randX - 1);
      int coordY = Game.WALL_PADDING + (Block.SIZE + Game.ROW_PADDING) * (randY - 1);

      for (PlayedBlockAssignment pba : blocks) {
        if (pba.getX() == coordX && pba.getY() == coordY) {
          continue outerloop;
        }
      }
      new PlayedBlockAssignment(coordX, coordY, g.getRandomBlock(), this);
    }
  }

  // line 136 "../../../../../Block223States.ump"
   private void doHitPaddleOrWall(){
    bounceBall();
  }

  // line 140 "../../../../../Block223States.ump"
   private void doOutOfBounds(){
    setLives(lives - 1);
    resetCurrentBallX();
    resetCurrentBallY();
    resetBallDirectionX();
    resetBallDirectionY();
    resetCurrentPaddleX();
  }

  // line 149 "../../../../../Block223States.ump"
   private void doHitBlock(){
    if (getBounce() == null) {
      return;
    }
    int score = getScore();
    BouncePoint bounce = getBounce();
    PlayedBlockAssignment pblock = bounce.getHitBlock();
    Block block = pblock.getBlock();
    int bscore = block.getPoints();
    setScore(bscore + score);
    if (block.getBlastRadius() > 0) {
      // for (pba : )
      // System.out.println("boom");
      int br = pblock.getBlock().getBlastRadius();
      List<PlayedBlockAssignment> explodeBlocks = new ArrayList<>();
      for (PlayedBlockAssignment pba : getBlocks()) {
        if (pba.getX() <= pblock.getX() + br * (Block.SIZE + Game.COLUMNS_PADDING)
            && pba.getX() >= pblock.getX() - br * (Block.SIZE + Game.COLUMNS_PADDING)
            && pba.getY() <= pblock.getY() + br * (Block.SIZE + Game.ROW_PADDING)
            && pba.getY() >= pblock.getY() - br * (Block.SIZE + Game.ROW_PADDING)) {
          explodeBlocks.add(pba);
        }
      }
      for (PlayedBlockAssignment pba : explodeBlocks) {
        setScore(getScore() + pba.getBlock().getPoints());
        pba.delete();
      }
    }
    pblock.delete();
    bounceBall();
  }

  // line 181 "../../../../../Block223States.ump"
   private void doHitBlockNextLevel(){
    doHitBlock();
    int level = getCurrentLevel();
    setCurrentLevel(level+1);
    double maxPL = (double) getGame().getPaddle().getMaxPaddleLength();
    double minPL = (double) getGame().getPaddle().getMinPaddleLength(); 
    double numLevels = (double) getGame().numberOfLevels();
    setCurrentPaddleLength(maxPL - (maxPL - minPL) / (numLevels-1) * (getCurrentLevel() - 1));
    setWaitTime(INITIAL_WAIT_TIME * Math.pow(getGame().getBall().getBallSpeedIncreaseFactor(), (getCurrentLevel() - 1)));
  }

  // line 192 "../../../../../Block223States.ump"
   private void doHitNothingAndNotOutOfBounds(){
    double x = getCurrentBallX();
    double y = getCurrentBallY();
    double dx = getBallDirectionX();
    double dy = getBallDirectionY();
    setCurrentBallX(x + dx);
    setCurrentBallY(y + dy);
  }

  // line 201 "../../../../../Block223States.ump"
   private void bounceBall(){
    // update ball position and direction
    // System.out.println("Bouncing ball");
    double cbx = getCurrentBallX();
    double cby = getCurrentBallY();
    double dx = getBallDirectionX();
    double dy = getBallDirectionY();

    BouncePoint bp = getBounce();

    double bpx = bp.getX();
    double bpy = bp.getY();

    double maxdist = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
    double disttobounce = Math.sqrt(Math.pow(cbx - bpx, 2) + Math.pow(cby - bpy, 2));
    double rem = maxdist - disttobounce;
    if (Math.abs(rem) < 0.000001 && bp.getHitBlock() == null) {
      setCurrentBallX(bpx);
      setCurrentBallY(bpy);

      return;
    }

    if (bp.getDirection() == BounceDirection.FLIP_X) {
      setBallDirectionX(getBallDirectionX() * -1.0);
      if (getBallDirectionY() < 0) {
        setBallDirectionY(getBallDirectionY() - 0.1 * Math.abs(getBallDirectionX()));
      } else if (getBallDirectionY() > 0) {
        setBallDirectionY(getBallDirectionY() + 0.1 * Math.abs(getBallDirectionX()));
      } else {
        setBallDirectionY(0.1 * Math.abs(getBallDirectionX()));
      }
      // cbx = bpx + outingX/dx * getBallDirectionX();
      // cby = bpy + outingX/dx * getBallDirectionY();
    } else if (bp.getDirection() == BounceDirection.FLIP_Y) {
      setBallDirectionY(getBallDirectionY() * -1.0);
      if (getBallDirectionX() < 0) {
        setBallDirectionX(getBallDirectionX() - 0.1 * Math.abs(getBallDirectionY()));
      } else if (getBallDirectionX() > 0) {
        setBallDirectionX(getBallDirectionX() + 0.1 * Math.abs(getBallDirectionY()));
      } else {
        setBallDirectionX(0.1 * Math.abs(getBallDirectionY()));
      }

    } else if (bp.getDirection() == BounceDirection.FLIP_BOTH) {
      setBallDirectionX(getBallDirectionX() * -1.0);
      setBallDirectionY(getBallDirectionY() * -1.0);
    }

    cbx = bpx + getBallDirectionX() * rem / maxdist;
    cby = bpy + getBallDirectionY() * rem / maxdist;
    // cbx = cbx + dx;
    // cby = cby + dy;

    System.out.println("Bounce Point: " + bpx + " , " + bpy);
    System.out.println();
    System.out.println("New Ball Position(after bounce): " + cbx + ", " + cby);
    System.out.println("New ball direction: " + getBallDirectionX() + " , " + getBallDirectionY());
    setCurrentBallX(cbx);
    setCurrentBallY(cby);
  }

  // line 264 "../../../../../Block223States.ump"
   private void doGameOver(){
    Block223 block223 = getBlock223();
    Player p = getPlayer();

    if (p != null){
      Game game = getGame();
      HallOfFameEntry hof = new HallOfFameEntry(score, playername, p, game, block223);
      game.setMostRecentEntry(hof);
      game.addHallOfFameEntry(hof);
    }

    this.delete();
  }

  // line 278 "../../../../../Block223States.ump"
   private BouncePoint calculateBouncePointBlock(PlayedBlockAssignment pb){
    double diam = Ball.BALL_DIAMETER;
    double radius = Ball.BALL_DIAMETER / 2;

    double pbl = pb.getX();
    double pbt = pb.getY();
    double pbr = pb.getX() + Block.SIZE;
    double pbb = pb.getY() + Block.SIZE;

    // double width = pb.getX() + Block.SIZE;
    // double height = pb.getY() + Block.SIZE;

    Line2D top = new Line2D.Double(pbl, pbt - radius, pbr, pbt - radius);
    Line2D left = new Line2D.Double(pbl - radius, pbt, pbl - radius, pbb);
    Line2D bottom = new Line2D.Double(pbl, pbb + radius, pbr, pbb + radius);
    Line2D right = new Line2D.Double(pbr + radius, pbt, pbr + radius, pbb);

    Arc2D tl = new Arc2D.Double(pbl - radius, pbt - radius, diam, diam, 90, 90, Arc2D.OPEN);
    Arc2D tr = new Arc2D.Double(pbr - radius, pbt - radius, diam, diam, 0, 90, Arc2D.OPEN);
    Arc2D bl = new Arc2D.Double(pbl - radius, pbb - radius, diam, diam, 180, 90, Arc2D.OPEN);
    Arc2D br = new Arc2D.Double(pbr - radius, pbb - radius, diam, diam, 270, 90, Arc2D.OPEN);

    Line2D ballpath = new Line2D.Double(currentBallX, currentBallY, currentBallX + ballDirectionX,
        currentBallY + ballDirectionY);

    // Check line intersects each section
    Point2D topint = calculateIntersection(ballpath, top);
    Point2D botint = calculateIntersection(ballpath, bottom);
    Point2D leftint = calculateIntersection(ballpath, left);
    Point2D rightint = calculateIntersection(ballpath, right);
    if (topint != null) {
      System.out.println("topint");
      BouncePoint bp = new BouncePoint(topint.getX(), topint.getY(), BounceDirection.FLIP_Y);
      bp.setHitBlock(pb);
      return bp;
    } else if (botint != null) {
      System.out.println("botint");
      BouncePoint bp = new BouncePoint(botint.getX(), botint.getY(), BounceDirection.FLIP_Y);
      bp.setHitBlock(pb);
      return bp;
    } else if (leftint != null) {
      System.out.println("leftint");
      BouncePoint bp = new BouncePoint(leftint.getX(), leftint.getY(), BounceDirection.FLIP_X);
      bp.setHitBlock(pb);
      return bp;
    } else if (rightint != null) {
      System.out.println("rightint");
      BouncePoint bp = new BouncePoint(rightint.getX(), rightint.getY(), BounceDirection.FLIP_X);
      bp.setHitBlock(pb);
      return bp;
    }

    // collide on corners
    Point2D tlintersects = calculateIntersection(ballpath, tl);
    Point2D trintersects = calculateIntersection(ballpath, tr);
    Point2D brintersects = calculateIntersection(ballpath, br);
    Point2D blintersects = calculateIntersection(ballpath, bl);

    if (tlintersects != null) {
      System.out.println("tlint");
      BouncePoint bp = new BouncePoint(tlintersects.getX(), tlintersects.getY(), BounceDirection.FLIP_BOTH);
      if (currentBallX < tlintersects.getX()) {
        bp.setDirection(BounceDirection.FLIP_X);
      } else {
        bp.setDirection(BounceDirection.FLIP_Y);
      }
      bp.setHitBlock(pb);
      return bp;
    } else if (trintersects != null) {
      System.out.println("trint");
      BouncePoint bp = new BouncePoint(trintersects.getX(), trintersects.getY(), BounceDirection.FLIP_BOTH);
      if (currentBallX > trintersects.getX()) {
        bp.setDirection(BounceDirection.FLIP_X);
      } else {
        bp.setDirection(BounceDirection.FLIP_Y);
      }
      bp.setHitBlock(pb);
      return bp;
    } else if (brintersects != null) {
      System.out.println("brint");
      BouncePoint bp = new BouncePoint(brintersects.getX(), brintersects.getY(), BounceDirection.FLIP_BOTH);
      if (currentBallX > brintersects.getX()) {
        bp.setDirection(BounceDirection.FLIP_X);
      } else {
        bp.setDirection(BounceDirection.FLIP_Y);
      }
      bp.setHitBlock(pb);
      return bp;
    } else if (blintersects != null) {
      System.out.println("blint");
      BouncePoint bp = new BouncePoint(blintersects.getX(), blintersects.getY(), BounceDirection.FLIP_BOTH);
      if (currentBallX < blintersects.getX()) {
        bp.setDirection(BounceDirection.FLIP_X);
      } else {
        bp.setDirection(BounceDirection.FLIP_Y);
      }
      bp.setHitBlock(pb);
      return bp;
    }

    return null;
  }

  // line 381 "../../../../../Block223States.ump"
   private BouncePoint calculateBouncePointWall(){
    double cbx = getCurrentBallX();
    double cby = getCurrentBallY();
    double dx = getBallDirectionX();
    double dy = getBallDirectionY();
    double newBallX = cbx + dx;
    double newBallY = cby + dy;
    double radius = Ball.BALL_DIAMETER / 2;

    Line2D ballPath = new Line2D.Double(cbx, cby, newBallX, newBallY);

    Line2D left = new Line2D.Double(radius, radius, radius, Game.PLAY_AREA_SIDE - radius);
    Line2D right = new Line2D.Double(Game.PLAY_AREA_SIDE - radius, radius, Game.PLAY_AREA_SIDE - radius,
        Game.PLAY_AREA_SIDE - radius);
    Line2D top = new Line2D.Double(radius, radius, Game.PLAY_AREA_SIDE - radius, radius);

    List<BouncePoint> bpts = new ArrayList<>();

    Point2D intleft = calculateIntersection(ballPath, left);
    Point2D intright = calculateIntersection(ballPath, right);
    Point2D inttop = calculateIntersection(ballPath, top);

    if (intleft != null) {
      System.out.println("Hit left");
      bpts.add(new BouncePoint(intleft.getX(), intleft.getY(), BounceDirection.FLIP_X));
    }
    if (intright != null) {
      System.out.println("Hit right");
      bpts.add(new BouncePoint(intright.getX(), intright.getY(), BounceDirection.FLIP_X));
    }
    if (inttop != null) {
      System.out.println("Hit top");
      bpts.add(new BouncePoint(inttop.getX(), inttop.getY(), BounceDirection.FLIP_Y));
    }

    if (bpts.size() == 0) {
      return null;
    }
    if (bpts.size() == 1) {
      return bpts.get(0);
    }

    return new BouncePoint(bpts.get(0).getX(), bpts.get(0).getY(), BounceDirection.FLIP_BOTH);
  }

  // line 426 "../../../../../Block223States.ump"
   private BouncePoint calculateBouncePointPaddle(){
    double cpx = getCurrentPaddleX();
    double cpy = getCurrentPaddleY();
    double cpl = getCurrentPaddleLength();
    double cbx = getCurrentBallX();
    double cby = getCurrentBallY();
    double dx = getBallDirectionX();
    double dy = getBallDirectionY();
    double newBallX = cbx + dx;
    double newBallY = cby + dy;
    double radius = Ball.BALL_DIAMETER / 2;

    Line2D ballPath = new Line2D.Double(cbx, cby, newBallX, newBallY);

    Line2D left = new Line2D.Double(cpx - radius, cpy, cpx - radius, cpy + Paddle.PADDLE_WIDTH);
    Line2D top = new Line2D.Double(cpx, cpy - radius, cpx + cpl, cpy - radius);
    Line2D right = new Line2D.Double(cpx + cpl + radius, cpy, cpx + cpl + radius, cpy + Paddle.PADDLE_WIDTH);

    Arc2D tl = new Arc2D.Double(cpx - radius, cpy - radius, radius * 2, radius * 2, 90, 90, Arc2D.OPEN);
    Arc2D tr = new Arc2D.Double(cpx + cpl - radius, cpy - radius, radius * 2, radius * 2, 0, 90, Arc2D.OPEN);

    Point2D leftint = calculateIntersection(ballPath, left);
    Point2D topint = calculateIntersection(ballPath, top);
    Point2D rightint = calculateIntersection(ballPath, right);

    Point2D tlint = calculateIntersection(ballPath, tl);
    Point2D trint = calculateIntersection(ballPath, tr);

    if (leftint != null) {
      System.out.println("Paddle left");
      return new BouncePoint(leftint.getX(), leftint.getY(), BounceDirection.FLIP_X);
    }
    if (topint != null) {
      System.out.println("Paddle top");
      return new BouncePoint(topint.getX(), topint.getY(), BounceDirection.FLIP_Y);
    }
    if (rightint != null) {
      System.out.println("Paddle right");
      return new BouncePoint(rightint.getX(), rightint.getY(), BounceDirection.FLIP_X);
    }

    if (tlint != null) {
      System.out.println("Paddle top left");
      if (cbx == tlint.getX() && getBounce() != null) {
        return new BouncePoint(tlint.getX(), tlint.getY(), getBounce().getDirection());
      }

      if (cbx < tlint.getX()) {
        return new BouncePoint(tlint.getX(), tlint.getY(), BounceDirection.FLIP_X);
      } else {
        return new BouncePoint(tlint.getX(), tlint.getY(), BounceDirection.FLIP_Y);
      }
    }

    if (trint != null) {
      System.out.println("Paddle top right");
      if (cbx == trint.getX() && getBounce() != null) {
        return new BouncePoint(trint.getX(), trint.getY(), getBounce().getDirection());
      }

      if (cbx > trint.getX()) {
        return new BouncePoint(trint.getX(), trint.getY(), BounceDirection.FLIP_X);
      } else {
        return new BouncePoint(trint.getX(), trint.getY(), BounceDirection.FLIP_Y);
      }
    }
    return null;
  }

  // line 495 "../../../../../Block223States.ump"
   public Point2D intersectionTester(Line2D line1, Line2D line2){
    return calculateIntersection(line1, line2);
  }

  // line 499 "../../../../../Block223States.ump"
   public Point2D intersectionTester(Line2D line, Arc2D arc){
    return calculateIntersection(line, arc);
  }

  // line 503 "../../../../../Block223States.ump"
   private Point2D calculateIntersection(Line2D line1, Line2D line2){
    // Point2D result = null;
    
    if (line1.intersectsLine(line2)) {

      double l1x1 = line1.getX1();
      double l1x2 = line1.getX2();
      double l1y1 = line1.getY1();
      double l1y2 = line1.getY2();

      double l2x1 = line2.getX1();
      double l2x2 = line2.getX2();
      double l2y1 = line2.getY1();
      double l2y2 = line2.getY2();

      double m1 = (l1y2 - l1y1) / (l1x2 - l1x1);
      double b1 = l1y1 - m1 * l1x1;
      double m2 = (l2y2 - l2y1) / (l2x2 - l2x1);
      double b2 = l2y1 - m2 * l2x1;

      if (Double.isInfinite(m1)) {
        return new Point2D.Double(l1x1, m2 * l1x1 + b2);
      }
      if (Double.isInfinite(m2)) {
        return new Point2D.Double(l2x1, m1 * l2x1 + b1);
      }

      double intx = (b2 - b1) / (m1 - m2);
      double inty = m1 * intx + b1;

      return new Point2D.Double(intx, inty);
    }
    return null;
  }


  /**
   * private Point2D calculateIntersection(Line2D line, Arc2D arc){
   * // Point result = new Point();
   * 
   * double xc = arc.getCenterX();
   * double yc = arc.getCenterY();
   * double rad = arc.getWidth() / 2;
   * 
   * // System.out.println(String.format("xc: %f, yc: %f, rad: %f", xc, yc, rad));
   * 
   * double x1 = line.getX1();
   * double x2 = line.getX2();
   * double y1 = line.getY1();
   * double y2 = line.getY2();
   * double a = y1 - y2;
   * double b = x2 - x1;
   * double c = x1 * y2 - x2 * y1;
   * 
   * // System.out.println(String.format("x1: %f, y1: %f, x2: %f, y2: %f\na: %f, b: %f, c: %f", x1, y1, x2, y2, a, b, c));
   * 
   * double rhs = (0 - c - a * xc - b * yc) / (rad * Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2)));
   * double p = Math.atan(-b / a);
   * 
   * double t1 = p + Math.acos(rhs);
   * double t2 = p - Math.acos(rhs);
   * 
   * if (t1 < 0) {
   * t1 = t1 + 2 * Math.PI;
   * }
   * if (t2 < 0) {
   * t2 = t2 + 2 * Math.PI;
   * }
   * 
   * double degt1 = Math.toDegrees(t1);
   * double degt2 = Math.toDegrees(t2);
   * // System.out.println(String.format("degt1: %f, degt2: %f", degt1, degt2));
   * 
   * if (between(degt1, arc.getAngleStart(), arc.getAngleStart() + arc.getAngleExtent())) {
   * 
   * double resX = rad * Math.cos(t1) + xc;
   * double resY = -rad * Math.sin(t1) + yc;
   * 
   * if (between(resX, line.getX1(), line.getX2()) && between(resY, line.getY1(), line.getY2())) {
   * // System.out.println("Found intersection at " + degt1 + " degrees.");
   * // result.setLocation(resX, resY);
   * // return result;
   * return new Point2D.Double(resX, resY);
   * }
   * }
   * 
   * if (between(degt2, arc.getAngleStart(), arc.getAngleStart() + arc.getAngleExtent())) {
   * double resX = rad * Math.cos(t2) + xc;
   * double resY = -rad * Math.sin(t2) + yc;
   * 
   * if (between(resX, line.getX1(), line.getX2()) && between(resY, line.getY1(), line.getY2())) {
   * // System.out.println("Found intersection at " + degt2 + " degrees.");
   * // result.setLocation(resX, resY);
   * // return result;
   * return new Point2D.Double(resX, resY);
   * }
   * }
   * 
   * return null;
   * }
   */
  // line 603 "../../../../../Block223States.ump"
   private Point2D calculateIntersection(Line2D line, Arc2D arc){
    double p = arc.getCenterX();
    double q = arc.getCenterY();
    double r = arc.getWidth() / 2;

    // System.out.println(String.format("xc: %f, yc: %f, rad: %f", xc, yc, rad));

    double x1 = line.getX1();
    double x2 = line.getX2();
    double y1 = line.getY1();
    double y2 = line.getY2();

    double m = (y1 - y2) / (x1 - x2);
    double c = y1 - m * x1;

    double A = m * m + 1;
    double B = 2 * (m * c - m * q - p);
    double C = q * q - r * r + p * p - 2 * c * q + c * c;

    double potentialX = 0;
    double potentialY = 0;
    double guard = 0;

    if (B * B - 4 * A * C == 0) {
      potentialX = -B / (2 * A);
      potentialY = m * potentialX + c;
      guard = 1;
    }
    if (B * B - 4 * A * C > 0) {
      guard = 1;
      double intersectX1 = (-B + Math.sqrt(B * B - 4 * A * C)) / (2 * A);
      double intersectY1 = m * intersectX1 + c;
      double intersectX2 = (-B - Math.sqrt(B * B - 4 * A * C)) / (2 * A);
      double intersectY2 = m * intersectX2 + c;
      double distance1 = Math.sqrt(Math.pow(x1 - intersectX1, 2) + Math.pow(y1 - intersectY1, 2));
      double distance2 = Math.sqrt(Math.pow(x1 - intersectX2, 2) + Math.pow(y1 - intersectY2, 2));
      if (distance1 < distance2) {
        potentialX = intersectX1;
        potentialY = intersectY1;
      } else {
        potentialX = intersectX2;
        potentialY = intersectY2;
      }
    }
    if (guard != 0 && between(potentialX, line.getX1(), line.getX2())
        && between(potentialY, line.getY1(), line.getY2())) {
      if (arc.getAngleStart() == 0) {
        if (between(potentialX, p, p + r) && between(potentialY, q, q - r)) {
          return new Point2D.Double(potentialX, potentialY);
        }
      }
      if (arc.getAngleStart() == 90) {
        if (between(potentialX, p, p - r) && between(potentialY, q, q - r)) {
          return new Point2D.Double(potentialX, potentialY);
        }
      }
      if (arc.getAngleStart() == 180) {
        if (between(potentialX, p, p - r) && between(potentialY, q, q + r)) {
          return new Point2D.Double(potentialX, potentialY);
        }
      }
      if (arc.getAngleStart() == 270) {
        if (between(potentialX, p, p + r) && between(potentialY, q, q + r)) {
          return new Point2D.Double(potentialX, potentialY);
        }
      }
    }
    return null;
  }

  // line 673 "../../../../../Block223States.ump"
   private boolean between(double v, double x, double y){
    if (x > y) {
      return v >= y && v <= x;
    } else {
      return v >= x && v <= y;
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "score" + ":" + getScore()+ "," +
            "lives" + ":" + getLives()+ "," +
            "currentLevel" + ":" + getCurrentLevel()+ "," +
            "waitTime" + ":" + getWaitTime()+ "," +
            "playername" + ":" + getPlayername()+ "," +
            "ballDirectionX" + ":" + getBallDirectionX()+ "," +
            "ballDirectionY" + ":" + getBallDirectionY()+ "," +
            "currentBallX" + ":" + getCurrentBallX()+ "," +
            "currentBallY" + ":" + getCurrentBallY()+ "," +
            "currentPaddleLength" + ":" + getCurrentPaddleLength()+ "," +
            "currentPaddleX" + ":" + getCurrentPaddleX()+ "," +
            "currentPaddleY" + ":" + getCurrentPaddleY()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "player = "+(getPlayer()!=null?Integer.toHexString(System.identityHashCode(getPlayer())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "bounce = "+(getBounce()!=null?Integer.toHexString(System.identityHashCode(getBounce())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "block223 = "+(getBlock223()!=null?Integer.toHexString(System.identityHashCode(getBlock223())):"null");
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 124 "../../../../../Block223Persistence.ump"
  private static final long serialVersionUID = 8597675110221231714L ;

  
}