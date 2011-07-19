
/**
 * Write a description of class MinesweeperLogic here.
 * 
 * @author (patrick@patrickjones.ca) 
 * @version (final project)
 */

import java.util.*;

public class MinesweeperLogic
{
    private int gameWidth = 10;
    private int gameHeight = 10;
    private int numBombs = 10;
    boolean[][] bombState;
    boolean[][] openState;
    boolean[][] mineFlag;
    
 // Starts a new game with a randomly generated minefield that has width columns and height rows
 public void startNewGame(int width, int height, int numBombs)
 {
    gameWidth = width;
    gameHeight = height;
    bombState = new boolean[gameHeight][gameWidth];
    openState = new boolean[gameHeight][gameWidth];
    mineFlag = new boolean[gameHeight][gameWidth];
    for(int i = 0; i < 10; i++){
        for(int j = 0; j < 10; j++){
            bombState[i][j] = false;
            openState[i][j] = false;
            mineFlag[i][j] = false;
        }
    }
    Random rnd = new Random();
    while(numBombs > 0) {
        int rHL = rnd.nextInt(gameHeight);
        int rWL = rnd.nextInt(gameWidth);
        if((rnd.nextInt(10) + 1) > 9 && !bombState[rHL][rWL]) {
            bombState[rHL][rWL] = true;
            numBombs --;
        }
    }
  }
  
  public void restartGame()
  {
    bombState = new boolean[gameHeight][gameWidth];
    openState = new boolean[gameHeight][gameWidth];
    mineFlag = new boolean[gameHeight][gameWidth];
    for(int i = 0; i < 10; i++){
        for(int j = 0; j < 10; j++){
            bombState[i][j] = false;
            openState[i][j] = false;
            mineFlag[i][j] = false;
        }
    }
    Random rnd = new Random();
    while(numBombs > 0) {
        int rHL = rnd.nextInt(gameHeight);
        int rWL = rnd.nextInt(gameWidth);
        if((rnd.nextInt(10) + 1) > 9 && !bombState[rHL][rWL]) {
            bombState[rHL][rWL] = true;
            numBombs --;
        }
    }
  }
 
 // Return the current width and the height of the gamefield, respectively.
 public int getWidth()
 {
    return gameWidth;
    }
 
 public int getHeight()
 {
    return gameHeight;
    }
    
 public int getNumBombs()
 {
    return numBombs;   
 }
 
 // Opens the cell at the coordinates (x,y), and if the value of the cell is zero, recursively opens all 
 // the neighbouring cells. If the cell contains a mine, the method returns false, otherwise it returns true.
 public boolean openCell(int x, int y)
 {
    boolean returnValue = false;
    if(x < 0) return returnValue;
    if(x >= gameWidth) return returnValue;
    if(y < 0) return returnValue;
    if(y >= gameHeight) return returnValue;
    if(openState[x][y]) {
      return returnValue;
    }
    if(isMarked(x, y)) {
      return returnValue;
    }
    if(getValue(x, y) != -1) {
        openState[x][y] = true;
    }
    if(getNumericalValue(x, y) == 0) {
        openCell(x - 1, y- 1);
        openCell(x, y - 1);
        openCell(x + 1, y - 1);
        openCell(x - 1, y);
        openCell(x + 1, y);
        openCell(x - 1, y + 1);
        openCell(x, y + 1);
        openCell(x + 1, y + 1);
    }
    return returnValue;
    }
 
 // Marks the cell at the coordinates (x,y) to be a presumed mine. If the cell really contains a mine, 
 // the method returns true, otherwise it returns false.
 public boolean markCell(int x, int y)
 {
    boolean returnValue = false;
    mineFlag[x][y] = true;
    int gv = getValue(x, y);
    if(gv == -1) returnValue = true;
    return returnValue;
    }
 
 // Returns true if the cell at the coordinates (x,y) is open, and returns false otherwise.
 public boolean isOpen(int x, int y)
 {
    boolean returnValue = false;
    if(openState[x][y]) returnValue = true;
    return returnValue;
    }
 
 // Returns true if the cell at the coordinates (x,y) is marked as a mine, and returns false otherwise.
 public boolean isMarked(int x, int y)
 {
    boolean returnValue = false;
    if(mineFlag[x][y]) returnValue = true;
    return returnValue;
    }
 
 // If the cell at the coordinates (x,y) is not a mine, returns its numerical value, i.e., the number of 
 // mines in its immediate neighbourhood. If the cell is a mine, returns -1.
 public int getValue(int x, int y)
 {
    int returnValue;
    if(bombState[x][y]) returnValue = -1;
    else returnValue = getNumericalValue(x, y);
    return returnValue;
    }
 
 private double chanceOfBomb(int height, int width, int bombs)
 {
    return (height * width) / bombs;
  }
 
  private int getNumericalValue(int x, int y) 
  {
      int returnValue = 0;
      returnValue += topRowCheck(x, y);
      returnValue += sameRowCheck(x, y);
      returnValue += bottomRowcheck(x, y);
      return returnValue;
  }
  
  private int topRowCheck(int x, int y){
        if(y <= 0) return 0;
        int returnValue = 0;
        int dy = y - 1;
        if(x > 0){
            if(bombState[x - 1][dy]) returnValue += 1;
        }
        if(bombState[x][dy]) {returnValue += 1;}
        if(x < (gameWidth - 1)) {
            if(bombState[x + 1][dy]) returnValue += 1;
        }
        return returnValue;
  }
  
  private int sameRowCheck(int x, int y)
  {
    int returnValue = 0;
    int dxminus = x - 1;
    int dxplus = x + 1;
    if(dxminus >= 0){
        if(bombState[dxminus][y]) returnValue += 1;
    }
    if(dxplus <= gameWidth - 1){
        if(bombState[dxplus][y]) returnValue +=1;
    }
    return returnValue;
  }
  
  private int bottomRowcheck(int x, int y)
  {
    int returnValue = 0;
    int dy = y + 1;
    if(dy >= gameHeight) return 0;
    if(x > 0){
        if(bombState[x - 1][dy]) returnValue += 1;
    }
    if(bombState[x][dy]) returnValue += 1;
    if(x < gameWidth - 1){
        if(bombState[x + 1][dy]) returnValue += 1;
    }
    return returnValue;
  }
  
}
