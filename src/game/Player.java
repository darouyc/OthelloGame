/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JLabel;

/**
 *
 * @author Hp EliteBook
 */
public class Player {
    ArrayList<JLabel> jetons;
    
    public Player()
    {
        jetons= new ArrayList();
    }
    
    public boolean verifyPosition(JLabel lbl)
    {
        if(jetons.contains(lbl))
            return false;
        return true;
    }
    public void addJetons(JLabel pos)
    {
        jetons.add(pos);
    }
}
