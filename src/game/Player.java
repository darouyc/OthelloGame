/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Hp EliteBook
 */
public class Player {
    ArrayList<MyLabel> jetons;
  
    public Player()
    {
        jetons= new ArrayList();
    }
  
 
    public void addJetons(MyLabel pos)
    {
        jetons.add(pos);
    }
    public int getScore()
    {
        return jetons.size();
    }
    
    public ArrayList<MyLabel> getJetons()
    {
        return this.jetons;
    }
    public void removeJeton(MyLabel lbl){
        jetons.remove(lbl);
    }
}
