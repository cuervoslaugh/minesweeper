/**
 Created by Patrick Jones (patrick@patrickjones.ca)
 
*/
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.*;

public class MineSweeper extends JPanel
  {
      MinesweeperLogic game;
      boolean showBombs = false;
      boolean stillAlive = true;
      
  public MineSweeper()
  {
     game = new MinesweeperLogic();
     game.startNewGame(10, 10, 10);
     
     int x = game.getWidth() * 25;
     int y = game.getHeight() * 25;
     this.setPreferredSize(new Dimension(x, y));
     this.setBorder(BorderFactory.createEtchedBorder());
     this.setBackground(Color.BLACK);
     this.addMouseListener(new MyMouseListener());
     
  }
  private class MyMouseListener implements MouseListener { 
    int bombs = game.getNumBombs();
    public void mousePressed(MouseEvent me) {};
    public void mouseReleased(MouseEvent me) {};
    public void mouseClicked(MouseEvent me) {
        if(stillAlive) {
            int x = me.getX() / 25;
            int y = me.getY() / 25;
            if(!game.bombState[x][y] && !game.isOpen(x,y) && me.getButton() == 1){
                game.openCell(x,y);
                repaint();
            }
            if(game.bombState[x][y] && me.getButton() == 1 && !game.isMarked(x, y)) {
                stillAlive = false;
                showBombs = true;
                repaint();
                int score = 0;
                int errFlagged = 0;
                for(int i = 0; i < game.getWidth(); i++){
                    for(int j = 0; j < game.getHeight(); j++){
                        if(game.bombState[i][j] && game.isMarked(i, j)) score += 1;
                    }
                }
                JOptionPane.showMessageDialog(null, "You found " + score + " out of " + bombs + " mines."); 
                String answer = JOptionPane.showInputDialog(null, "Play Again (y/n) ?");
                if(answer.equals("y")) {
                    game.restartGame();
                    stillAlive = true;
                    showBombs = false;
                    repaint();
                }
            }
            if(me.getButton() == 3 && !game.isOpen(x, y)){
                if(!game.isMarked(x,y)){
                  game.mineFlag[x][y] = true;}
                else {
                    game.mineFlag[x][y] = false;}
                repaint();
            }
        }
        
        
    };
    public void mouseEntered(MouseEvent me) {};
    public void mouseExited(MouseEvent me) {};
  }
  
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g;  
    int xBorder = (game.getWidth()) * 25;
    int yBorder = (game.getHeight()) * 25;
    g2.setColor(Color.BLUE);
    for(int i = 0; i < xBorder; i += 25){
        for(int j = 1; j < yBorder; j += 25){
      int x = i / 25;
      int y = j / 25;
          if(game.openState[x][y]){
        g2.setColor(Color.WHITE);
        };
      if(showBombs && game.bombState[x][y]){
        g2.setColor(Color.RED);
        }
      if(game.mineFlag[x][y]){
        g2.setColor(Color.GREEN);
        }
          g2.fill(new Rectangle2D.Double(i, j, 24, 24));
      if(game.isOpen(x, y) && game.getValue(x, y) > 0 && !game.isMarked(x, y)){
        g2.setColor(Color.RED);
        String val = Integer.toString(game.getValue(x, y));
        Font font = new Font("Comic Sans", Font.PLAIN, 9);
        g2.setFont(font);
        int slX = (x * 25) + 2;
        int slY = (y * 25) + 12;
        g2.drawString(val, slX, slY);
        g2.setColor(Color.BLUE);
        }
          g2.setColor(Color.BLUE);
        }
  }
  }
  public void reset(){

    }
  
  public static void main(String[] args)
  {
    final MineSweeper ms = new MineSweeper();
    JFrame jf = new JFrame("CCPS 109 Final Project");
    JMenuBar mb = new JMenuBar();
    JMenu restartM = new JMenu("Restart");
    JMenuItem restart = new JMenuItem("Restart");
    restartM.add(restart);
    restart.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent ae) {
            JOptionPane.showMessageDialog(null, "you clicked me!");
        }
    });
//    jf.add(restartM);   
    jf.setJMenuBar(mb);
    jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    jf.setLayout(new FlowLayout());
    jf.add(ms);
    jf.pack();
    jf.setVisible(true);
  }
}