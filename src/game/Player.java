/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Hp EliteBook
 */
// this class define player by their used tokens
public class Player {
    ArrayList<MyLabel> jetons;
  
    public Player()
    {
        jetons= new ArrayList();
    }
  
    //add used token to player
    public void addJetons(MyLabel pos)
    {
        jetons.add(pos);
    }
    
    //get score of player
    public int getScore()
    {
        return jetons.size();
    }
    
    public ArrayList<MyLabel> getJetons()
    {
        return this.jetons;
    }
    
    //remove jetons won by adv
    public void removeJeton(MyLabel lbl){
       
        Iterator itr = jetons.iterator(); 
        while (itr.hasNext()) 
        { 
            MyLabel l = (MyLabel)itr.next(); 
            
            // getMyLabel postion inside matrix and remove it from arraylist
            if(lbl.getLine() == l.getLine() && lbl.getColumn() == lbl.getColumn())
            {
                // verify score
                System.out.println("score before"+jetons.size());
                jetons.remove(l);
                System.out.println("score after"+jetons.size());
                break;
            }
        } 
    
    } 
    public void updateJetons(ArrayList<MyLabel> newJetons)
    {
        this.jetons = new ArrayList<MyLabel>();
        this.jetons = newJetons;
    }
}
