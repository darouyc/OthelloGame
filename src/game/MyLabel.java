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
public class MyLabel extends JLabel{
    
   JLabel lbl ;
   int content = 0;
   int line = 0;
   int column = 0;
   
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
