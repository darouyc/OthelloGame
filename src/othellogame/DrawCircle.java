/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package othellogame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JPanel;

/**
 *
 * @author Hp EliteBook
 */
public class DrawCircle extends JPanel{
    Point position;
    
    public DrawCircle(Point position)
    {
        this.position = position;
    }
     @Override
    public void paintComponent(Graphics g) { //override paintComponent for custom painting
        super.paintComponent(g); //call super
        g.setColor(Color.white);  //set painting color
        int x=(int) this.position.getX();
        int y= (int) this.position.getY();
        g.fillOval(x, y, 50, 50);
    }
}
