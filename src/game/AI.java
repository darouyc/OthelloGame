/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import othellogame.OthelloInterface;

/**
 *
 * @author Hp EliteBook
 */
public class AI {
    ArrayList<MyLabel> possibility;
    
    public AI(OthelloInterface othello)
    {
        possibility = othello.trace(2);
    }
    
    public MyLabel playAI()
    {
        int randomNum = ThreadLocalRandom.current().nextInt(0, possibility.size());
        MyLabel lbl =possibility.get(randomNum);
        
       return lbl;
    }
}
