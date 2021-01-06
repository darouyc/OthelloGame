/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package othellogame;

import game.Game;
import game.MyLabel;
import game.Player;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.text.Position;

/**
 *
 * @author Abdellah
 */
public class OthelloInterface extends javax.swing.JFrame {

    /**
     * Creates new form OthelloInterface
     */
    Game game;

    boolean tour = false;
    MyLabel[][] lbls = new MyLabel[8][8];

    public OthelloInterface() {
        initComponents();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(OthelloInterface.EXIT_ON_CLOSE);
        
        //create grille 
        GridLayout grdlyt = new GridLayout(8, 8);
        
        //initialization game
        game = new Game(lbls);
        this.panel.setLayout(grdlyt);

        //Score icon
        lblWhite.setIcon(new ImageIcon(getClass().getResource("/othellogame/whitedice.png")));
        lblBlack.setIcon(new ImageIcon(getClass().getResource("/othellogame/blackdice.png")));

        // create labels
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                MyLabel lbl = new MyLabel(" ");
                lbl.setPreferredSize(new java.awt.Dimension(50, 50));
                lbl.setBackground(new Color(40, 100, 28));
                lbl.setBorder(new BevelBorder(BevelBorder.LOWERED));
//                lbl.setText("i" + i + "j" + j );
                lbl.setEnabled(false);
                this.panel.add(lbl);

                lbls[i][j] = lbl;
                // initial stat  
                if (i == 3 && j == 4 || i == 4 && j == 3) {
                    game.getPlayer1().addJetons(lbl);
                    lbl.setIcon(new ImageIcon(getClass().getResource("/othellogame/blackdice.png")));
                    lbl.setContent(2);
                    
                }
                if (i == 3 && j == 3 || i == 4 && j == 4) {
                    game.getPlayer2().addJetons(lbl);
                    lbl.setIcon(new ImageIcon(getClass().getResource("/othellogame/whitedice.png")));
                    lbl.setContent(1);
                }
                
                
            }
        }

        // show score for players
        lblScoreBlack.setText(" " + game.getPlayer1().getScore());
        lblScoreWhite.setText(" " + game.getPlayer2().getScore());

        //Update matrix
        game.setLbels(lbls);
        first();
    }
    public void first()
    {
        trace(1);
        lbls[3][4].setBackground(new Color(40, 100, 28));
        lbls[3][4].setEnabled(true);
        lbls[4][3].setBackground(new Color(40, 100, 28));
        lbls[4][3].setEnabled(true);
        lbls[3][3].setBackground(new Color(40, 100, 28));
        lbls[3][3].setEnabled(true);
        lbls[4][4].setBackground(new Color(40, 100, 28));
        lbls[4][4].setEnabled(true);
    }
    public void play(Point pos) {
        // Concat players arrays
        game.concatArrays();
        changePlayer();
        
        //get selected label
        MyLabel label = (MyLabel) this.panel.getComponentAt(pos);
        
        //verify label if is empty
        if (game.verifyPosition(label)) 
        {
            
            
            // player 1
            if (tour) 
            {
                //add white icon
                label.setIcon(new ImageIcon(getClass().getResource("/othellogame/whitedice.png")));
                
                //add label to matrix with content 1 == white 
                changeStat( label, 1);
                System.out.println("-----------done----------"); 
               
                //add Jeton to player array 
                game.getPlayer1().addJetons(label);
                                
                //Disable All labels
                for (int i = 0; i < 8; i++) 
                    for (int j = 0; j < 8; j++) 
                        if(lbls[i][j].getContent() == 0)
                            lbls[i][j].setEnabled(false);
            
                //display possibilities to next player
                trace(1);
                
                //Update score
                lblScoreWhite.setText(" " + game.getPlayer1().getScore());
                
                //change player
                tour = false;
                label.setBackground(new Color(40, 100, 28));
            }  
               //player 2 
            else {
                //add black icon
                label.setIcon(new ImageIcon(getClass().getResource("/othellogame/blackdice.png")));
                
                //add label to matrix with content 2 == black
                changeStat( label, 2);
                System.out.println("-----------done----------");
                
                //add Jeton to player array
                game.getPlayer2().addJetons(label);
                
                //Disable All labels
                for (int i = 0; i < 8; i++) 
                    for (int j = 0; j < 8; j++) 
                        if(lbls[i][j].getContent() == 0)
                            lbls[i][j].setEnabled(false);
                
                //display possibilities to next player
                trace(2);
                
                //Update score
                lblScoreBlack.setText(" " + game.getPlayer2().getScore());
                
                 //change player
                tour = true;
                label.setBackground(new Color(40, 100, 28));
            }
        } else{
                // used position
                System.out.println("------Not available-------");
                
                // display possibilities
                if (tour) {
                    trace(2);
                }
                else {
                    trace(1);
                }
               }
    

}
    
private void trace(int playerContent)
    {
//        boolean win= true;
        try {
            for(int i=0;i<8;i++)
            {
                for(int j=0; j<8 ; j++)
                {
                    //get used postion with content 
                    if(lbls[i][j].getContent() == playerContent )
                    {
                        if(verifyLine(i, lbls[i][j]) && verifyColumn(j, lbls[i][j]) && verifyDiagonal(lbls[i][j]) )
                        {   
                            //Column
                            if(game.verifyPosition(lbls[i+1][j])){
                                lbls[i+1][j].setBackground(Color.red);
                                lbls[i+1][j].setEnabled(true);
//                                win = false;
                            }
                            if(game.verifyPosition(lbls[i-1][j])){
                                lbls[i-1][j].setBackground(Color.red);
                                lbls[i-1][j].setEnabled(true);
                          }
    
                            //Ligne
                            if(game.verifyPosition(lbls[i][j+1])){
                                lbls[i][j+1].setBackground(Color.red);
                                lbls[i][j+1].setEnabled(true);
                            }
                            if(game.verifyPosition(lbls[i][j-1])){
                                lbls[i][j-1].setBackground(Color.red);
                                lbls[i][j-1].setEnabled(true);
                            }

                            //Diagonal
                            if(game.verifyPosition(lbls[i+1][j+1]) && lbls[i-1][j-1].getContent()!= playerContent && lbls[i-1][j-1].getContent() != 0  ){
                                lbls[i+1][j+1].setBackground(Color.red);
                                lbls[i+1][j+1].setEnabled(true);
                            }
                            if(game.verifyPosition(lbls[i-1][j-1]) && lbls[i+1][j+1].getContent() != playerContent && lbls[i+1][j+1].getContent() != 0 ){
                                lbls[i-1][j-1].setBackground(Color.red);
                                lbls[i-1][j-1].setEnabled(true);
                            }
                            if(game.verifyPosition(lbls[i-1][j+1]) && lbls[i+1][j-1].getContent()!= playerContent && lbls[i+1][j-1].getContent() != 0 ){
                                lbls[i-1][j+1].setBackground(Color.red);
                                lbls[i-1][j+1].setEnabled(true);
                            }
                            if(game.verifyPosition(lbls[i+1][j-1]) && lbls[i-1][j+1].getContent()!= playerContent && lbls[i-1][j+1].getContent() != 0 ){
                                lbls[i+1][j-1].setBackground(Color.red);
                                lbls[i+1][j-1].setEnabled(true);
                            }

                        }
                    }
                }
            }
        } catch (Exception e) {
            
        }
        
        
         
    }
    //line
    public boolean verifyLine(int line, MyLabel lbl)
    {
        for(int column = 0; column<8 ; column++)
        {
            if(lbls[line][column].getContent() != 0 && lbls[line][column]!=lbl)
            {
                return true;
            }
        }
        return false;
    }
    //column 
        public boolean verifyColumn(int column, MyLabel lbl)
    {
        for(int line = 0; line<8 ; line++)
            if(lbls[line][column].getContent() != 0 && lbls[line][column]!=lbl)
                return true;        
        return false;
    }
            //diagonale
        public boolean verifyDiagonal(MyLabel lbl)
    {
        //Calcul distance entre diag et position de label
        int dist = 0;
        
        //To localize the diagonal.column for each row
        int diagonal = 0;
        
        for(int line = 0; line<8 ; line++)
            for(int column = 0; column<8 ; column++){
                if(line == column) diagonal = column;
                if(lbls[line][column] == lbl && column>line)
                    dist = column - diagonal;
                if(lbls[line][column] == lbl && column<line)
                    dist = diagonal - column;
            }   
        
        
        for(int line = 0; line<8 ; line++)
            for(int column = 0; column<8 ; column++){
                if(line == column && column>line && (column+dist < 8) )
                    if(lbls[line][column+dist].getContent() != 0 && lbls[line][column+dist]!=lbl)
                        return true;
                if(line == column && column <= line && (column-dist >= 0))
                    if(lbls[line][column - dist].getContent() != 0 && lbls[line][column - dist]!=lbl)
                        return true;  
            }
        

        return false;
    }
     public void changePlayer()
     {
          for(int i=0;i<8;i++)
        {
            for(int j=0; j<8 ; j++)
            {
                lbls[i][j].setBackground(new Color(40, 100, 28));
            }
        }
     }
     
     public void changeStat(MyLabel pos, int content)
    {
       for(int i=0;i<8;i++)
        {
            for(int j=0; j<8 ; j++)
            {
                //get Label from matrix and change state from 0 == empty to 1 == white or 2 == black
                if(lbls[i][j]==pos)
                {
                    lbls[i][j].setContent(content);
                    lbls[i][j].setBackground(new Color(40, 100, 28));
                    game.setLbels(lbls);
                }
            }
        }
     
     }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lblScoreWhite = new javax.swing.JLabel();
        lblBlack = new javax.swing.JLabel();
        lblWhite = new javax.swing.JLabel();
        lblScoreBlack = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panel.setBackground(new java.awt.Color(40, 100, 28));
        panel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 463, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 411, Short.MAX_VALUE)
        );

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));

        lblScoreWhite.setText("0");

        lblBlack.setText("jLabel1");

        lblWhite.setText("jLabel1");

        lblScoreBlack.setText("0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(69, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblScoreBlack, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBlack, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblScoreWhite, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(64, 64, 64))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(69, Short.MAX_VALUE)
                    .addComponent(lblWhite, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(64, 64, 64)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(175, 175, 175)
                .addComponent(lblScoreWhite)
                .addGap(61, 61, 61)
                .addComponent(lblBlack)
                .addGap(55, 55, 55)
                .addComponent(lblScoreBlack)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(95, 95, 95)
                    .addComponent(lblWhite)
                    .addContainerGap(304, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(69, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(101, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(115, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void panelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelMouseClicked
        // TODO add your handling code here:
        Point pos = new Point();
        pos.setLocation(evt.getPoint());
        JLabel ana = (JLabel) panel.getComponentAt(pos);
        if(ana.isEnabled() == true)
            play(pos);
       
        //show matrix in console
         for(int i=0 ; i<8 ; i++)
         {
             System.out.println(" ");
             for(int j = 0 ; j<8 ; j++)
             {
                 System.out.print(" "+lbls[i][j].getContent()+" ");
             }
         }
    }//GEN-LAST:event_panelMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                

}
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(OthelloInterface.class
.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        

} catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OthelloInterface.class
.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        

} catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OthelloInterface.class
.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        

} catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OthelloInterface.class
.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OthelloInterface().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblBlack;
    private javax.swing.JLabel lblScoreBlack;
    private javax.swing.JLabel lblScoreWhite;
    private javax.swing.JLabel lblWhite;
    private javax.swing.JPanel panel;
    // End of variables declaration//GEN-END:variables
}
