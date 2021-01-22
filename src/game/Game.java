/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

/**
 *
 * @author Hp EliteBook
 */
 
   // this class save all transaction made in game 
    public class Game {
        ArrayList<MyLabel> jetons; //containt all tokens used by both players 
        Player player1;
        Player player2;
        MyLabel [][] lbls; // matrix of MyLabels
    
    public Game(JPanel panel)
    {
       this.lbls = new MyLabel[8][8]; 
       jetons = new ArrayList();
       this.player1 = new Player();
       this.player2 = new Player(); 
       
       for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                
                // create new MyLabel
                MyLabel lbl = new MyLabel(new JLabel(" "), i, j);
                // set disgn configurations 
                lbl.setPreferredSize(new java.awt.Dimension(50, 50));
                lbl.setBackground(new Color(40, 100, 28));
                lbl.setBorder(new BevelBorder(BevelBorder.LOWERED));
 //               lbl.setText("i" + i + "j" + j );
                // set label not clickable
                lbl.setEnabled(false);
                
                //add MyLabel to panel
                panel.add(lbl);

               
                // initial stat  2 black and 2 white token
                if (i == 3 && j == 4 || i == 4 && j == 3) {
                    lbl.setIcon(new ImageIcon(getClass().getResource("/othellogame/blackdice.png")));
                    lbl.setContent(2);
                    player1.addJetons(lbl);
                }
                if (i == 3 && j == 3 || i == 4 && j == 4) {
                    
                    lbl.setIcon(new ImageIcon(getClass().getResource("/othellogame/whitedice.png")));
                    lbl.setContent(1);
                    player2.addJetons(lbl);
                }
                //save label inside matrix
                lbls[i][j] = lbl;
            }
       }
                
    }

    public Game() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    //add tokens used by the both of players
    public void concatArrays()
    {
        jetons.clear();
        jetons.addAll(player1.getJetons());
        jetons.addAll(player2.getJetons());
    }
    
    //verify if lbl is empty
    public boolean verifyPosition(MyLabel lbl)
    {
        concatArrays();
        // 1=> used by player1 && 2=> used by player2
        if(lbl.getContent() == 1 || lbl.getContent() == 2)
            return false;
        return true;
    }
    
    public Player getPlayer1()
    {
        return player1;
    }
    
    public Player getPlayer2()
    {
        return player2;
    }
    
    public ArrayList<MyLabel> getJetons()
    {
        return this.jetons;
    }
    
    // define player by content 1=> player1 && 2=> player2
    public Player getPlayer(int content)
    {
        if(content == 1)
        {
            return player1;
        }
        if(content == 2)
        {
            return player2;
        }
        return null;
    }
    
    // get matrix
    public MyLabel [][] getLabels()
    {
        return lbls;
    }
    
    //get MyLabel by postion in matix
    public MyLabel getLabel(int i , int j)
    {
        return lbls[i][j];
        
    }
    
    // display tokens
    public void displayJetons()
    {
        //current tokens of player1
        System.out.println(" player1 ");
        for(MyLabel lbl: getPlayer1().jetons)
        {
          System.out.print(" content "+lbl.getContent()); 
        }
        
        //current tokens of player2
        System.out.println(" player2 ");
        for(MyLabel lbl: getPlayer2().jetons)
        {
           System.out.print(" content "+lbl.getContent());
        }
    }
    
    //Update score for players
    public void updateScore()
    {
        //create new arrayList 
         ArrayList<MyLabel> jetons1= new ArrayList<MyLabel>();
         ArrayList<MyLabel> jetons2= new ArrayList<MyLabel>();
         
        for(int line = 0;line<lbls.length;line++)
            for(int column = 0;column<lbls.length; column++)
            {
                //get labels where content == 1 from matrix
                if(lbls[line][column].getContent()== 1)
                {
                    jetons1.add(lbls[line][column]);
                }
                //get labels where content == 2 from matrix
                if(lbls[line][column].getContent()== 2)
                {
                    jetons2.add(lbls[line][column]);
                }
            }
        //Update jetons from current matrix for players
        player1.updateJetons(jetons1);
        player2.updateJetons(jetons2);
        
    }
}
