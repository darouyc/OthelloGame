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
    ArrayList<JLabel> jetons;
    JPanel grille;
    public Player()
    {
        jetons= new ArrayList();
    }
    public void setGrille(JPanel pn)
    {
        this.grille = pn;
    }
 
    public void addJetons(JLabel pos)
    {
        jetons.add(pos);
    }
    public int getScore()
    {
        return jetons.size();
    }
    
    public ArrayList<JLabel> getPossibilities(ArrayList<JLabel> vs)
    {
        ArrayList<JLabel> results = null;
            for(JLabel lbl :vs)
            {
                
              System.out.println(this.grille.getComponent(lbl.getComponentCount()));
            }
        return results;
    }
    
    public ArrayList<JLabel> getJetons()
    {
        return this.jetons;
    }
}
