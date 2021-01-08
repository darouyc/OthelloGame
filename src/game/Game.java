/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

/**
 *
 * @author Hp EliteBook
 */
    public class Game {
        ArrayList<MyLabel> jetons;
        Player player1;
        Player player2;
        MyLabel [][] lbls;
    
    public Game(JPanel panel)
    {
       this.lbls = new MyLabel[8][8];
       jetons = new ArrayList();
       this.player1 = new Player();
       this.player2 = new Player(); 
       
       for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                MyLabel lbl = new MyLabel(new JLabel(" "), i, j);
                lbl.setPreferredSize(new java.awt.Dimension(50, 50));
                lbl.setBackground(new Color(40, 100, 28));
                lbl.setBorder(new BevelBorder(BevelBorder.LOWERED));
                lbl.setText("i" + i + "j" + j );
                lbl.setEnabled(false);
                panel.add(lbl);

               
                // initial stat  
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
                lbls[i][j] = lbl;
            }
       }
                
    }
    
    public void concatArrays()
    {
        jetons.clear();
        jetons.addAll(player1.getJetons());
        jetons.addAll(player2.getJetons());
    }
    
    public boolean verifyPosition(MyLabel lbl)
    {
        concatArrays();
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
//    public void setLbels( MyLabel[][] lbls)
//    {
//        this.lbls = lbls;
//        //lbls[1][1].setBackground(Color.red);
//    }
    public ArrayList<MyLabel> getJetons()
    {
        return this.jetons;
    }
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
    public MyLabel [][] getLabels()
    {
        return lbls;
    }
    public MyLabel getLabel(int i , int j)
    {
        return lbls[i][j];
        
    }
//    public void setLabel(int i, int j)
//    {
//        lbls[i][j]
//    
    public void displayJetons()
    {
        System.out.println(" player1 ");
        for(MyLabel lbl: getPlayer1().jetons)
        {
//            for(int i=0;i<8;i++ )
//                for(int j=0;j<8;j++ )
//                {
                   // if(lbl.getLocation() == lbls[i][j].getLocation())
                        System.out.print(" content "+lbl.getContent());
 //               }
            
        }
        
        System.out.println(" player2 ");
        
        for(MyLabel lbl: getPlayer2().jetons)
        {
//            for(int i=0;i<8;i++ )
//                for(int j=0;j<8;j++ )
//                {
                   // if(lbl.getLocation() == lbls[i][j].getLocation())
                        System.out.print(" content "+lbl.getContent());
  //              }
            
        }
    }
}
