/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.ArrayList;
import javax.swing.JLabel;

/**
 *
 * @author Hp EliteBook
 */
    public class Game {
        ArrayList<JLabel> jetons;
        Player player1;
        Player player2;
    
    public Game()
    {
       jetons = new ArrayList();
       this.player1 = new Player();
       this.player2 = new Player();
    }
    
    public void concatArrays()
    {
        jetons.addAll(player1.getJetons());
        jetons.addAll(player2.getJetons());
    }
    
    public boolean verifyPosition(JLabel lbl)
    {
        if(jetons.contains(lbl))
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
}
