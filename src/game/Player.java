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
       
        Iterator itr = jetons.iterator(); 
        while (itr.hasNext()) 
        { 
            MyLabel l = (MyLabel)itr.next(); 
            
            if(lbl.getLine() == l.getLine() && lbl.getColumn() == lbl.getColumn())
            {
                System.out.println("score before"+jetons.size());
                jetons.remove(l);
                System.out.println("score after"+jetons.size());
                break;
            }
        } 
  
       
    }
//    public void displayJetons()
//    {
//        for(MyLabel lbl: jetons)
//        {
//            System.out.print(" i "+i+" j "+j+"");
//        }
//    }
}
