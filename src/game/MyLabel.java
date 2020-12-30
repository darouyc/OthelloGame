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
    
    
   int content = 0;
   
   public MyLabel()
   {
    super();
            
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
}
