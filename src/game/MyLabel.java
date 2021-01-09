/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import javax.swing.JLabel;

/**
 *
 * @author Hp EliteBook
 */
 
// this class define label by content and its positions in matrix
public class MyLabel extends JLabel{
    
   JLabel lbl ; 
   int content = 0; // 0 => unused token 1=> used by player1 2=> used by player2
   int line = 0; // line in matrix
   int column = 0; //column in matrix
   
   public MyLabel(JLabel lbl, int line, int column)
   {
    super();
    this.lbl =  lbl;
    this.line = line;
    this.column = column;
   }
   
    public MyLabel(String str)
   {
    super(str);
            
   }
   public int getContent()
   {
       return content;
   }
   
   public void setContent(int content)
   {
       this.content = content;
   }
   
   public int getLine()
   {
       return line;
   }
   
   public int getColumn()
   {
       return column;
   }
}
