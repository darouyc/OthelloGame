/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package othellogame;

import Authentication.Authentication;
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
                lbl.setText("i" + i + "j" + j );
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
                checkBetween(label,1 , game.getPlayer1(), game.getPlayer2());
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
                checkBetween(label,2, game.getPlayer2(), game.getPlayer1());
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
     public void checkBetween(JLabel lbl, int content, Player player1, Player player2){
         int line = 0;
         int column = 0;
         int nextLine = 0;
         int nextColumn = 0;
         
         //get my label indexes
         for(int i = 0; i<8; i++){
             for(int j = 0; j<8; j++){
                 if(lbls[i][j] == lbl){
                     line = i;
                     column = j;
                 }
             }
         }
         
            //reverse lines to other player
             for(int j = 0; j<8; j++){
                 if(lbls[line][j].getContent() == content && lbls[line][j] != lbl){
                     if( column > j){
                           for(int b = j+1; b < column; b++){
                               lbls[line][b].setContent(content);
                               player1.removeJeton(lbls[line][b]);
                               player2.addJetons(lbls[line][b]);
                               drawJeton(lbls[line][b], switchContent(content));
                           }
                    }
                     if( column < j ){
                           for(int b = column+1; b < j; b++){
                               lbls[line][b].setContent(content);
                               player1.removeJeton(lbls[line][b]);
                               player2.addJetons(lbls[line][b]);
                               drawJeton(lbls[line][b], switchContent(content));
                           }
                    }
                     
                 }
             }
             //reverse columns to other player
             for(int i = 0; i<8; i++){
                 if(lbls[i][column].getContent() == content && lbls[i][column] != lbl){
                     if( line > i){
                           for(int b = i+1; b < column; b++){
                               lbls[b][column].setContent(content);
                               player1.removeJeton(lbls[b][column]);
                               player2.addJetons(lbls[b][column]);
                               drawJeton(lbls[b][column], switchContent(content));
                           }
                    }
                     if( line < i ){
                           for(int b = line+1; b < i; b++){
                               lbls[b][column].setContent(content);
                               player1.removeJeton(lbls[b][column]);
                               player2.addJetons(lbls[b][column]);
                               drawJeton(lbls[b][column], switchContent(content));
                           }
                    }
                     
                 }
             }
             
             //Reverse diagonale to other player
             //Calcul distance entre diag et position de label
                int dist = 0;

                //To localize the diagonal.column for each row
                int diagonal = 0;

                for(int lineDia = 0; lineDia<8 ; lineDia++)
                    for(int columnDia = 0; columnDia<8 ; columnDia++){
                        if(lineDia == columnDia) diagonal = columnDia;
                        if(lbls[lineDia][columnDia] == lbl && columnDia>lineDia)
                            dist = columnDia - diagonal;
                        if(lbls[lineDia][columnDia] == lbl && columnDia<lineDia)
                            dist = diagonal - columnDia;
                    }   

                //Get next position
                for(int lineDia = 0; lineDia<8 ; lineDia++)
                    for(int columnDia = 0; columnDia<8 ; columnDia++){
                        if(lineDia == columnDia && columnDia>lineDia && (columnDia+dist < 8) )
                            if(lbls[lineDia][columnDia+dist].getContent() == content && lbls[lineDia][columnDia+dist]!=lbl){
                                nextColumn = columnDia;
                                nextLine = lineDia;
                            }
                        if(lineDia == columnDia && columnDia <= lineDia && (columnDia-dist >= 0))
                            if(lbls[lineDia][columnDia - dist].getContent() != 0 && lbls[lineDia][columnDia - dist]!=lbl){
                                nextColumn = columnDia;
                                nextLine = lineDia;
                            }
                    }
               // if(column < nextColumn)
                    
             
     }
     
     public int switchContent(int content){
         if(content == 1)
             return 2;
         return 1;
     }
     
     public void drawJeton(JLabel lbl, int content){
         if(content == 1)
             lbl.setIcon(new ImageIcon(getClass().getResource("/othellogame/blackdice.png")));
         else if(content == 2)
             lbl.setIcon(new ImageIcon(getClass().getResource("/othellogame/whitedice.png")));
     }
     
     public void setUser(String username)
    {
       user.setText(username);
    }
     
     public void setDisconnectButton(String s)
    {
       disconnect.setText(s);
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
        disconnect = new javax.swing.JButton();
        user = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        newGame = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        exit = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Othello");
        setForeground(java.awt.Color.white);

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

        lblScoreWhite.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lblScoreWhite.setText("0");

        lblBlack.setText("jLabel1");

        lblWhite.setText("jLabel1");

        lblScoreBlack.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lblScoreBlack.setText("0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblWhite, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblScoreWhite)
                    .addComponent(lblScoreBlack)
                    .addComponent(lblBlack, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(lblWhite)
                .addGap(33, 33, 33)
                .addComponent(lblScoreWhite)
                .addGap(77, 77, 77)
                .addComponent(lblScoreBlack)
                .addGap(29, 29, 29)
                .addComponent(lblBlack)
                .addContainerGap(61, Short.MAX_VALUE))
        );

        disconnect.setText("Disconnect");
        disconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disconnectActionPerformed(evt);
            }
        });

        user.setBackground(new java.awt.Color(0, 0, 0));
        user.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        user.setText("Guest");

        jMenu1.setText("File");

        newGame.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        newGame.setText("New Game");
        newGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newGameActionPerformed(evt);
            }
        });
        jMenu1.add(newGame);
        jMenu1.add(jSeparator1);

        exit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        exit.setText("Exit");
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });
        jMenu1.add(exit);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(59, 59, 59)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 63, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(user, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(disconnect)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(disconnect)
                    .addComponent(user))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)))
                .addContainerGap(101, Short.MAX_VALUE))
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

    private void disconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disconnectActionPerformed
        // TODO add your handling code here:
        dispose();
        Authentication auth = new Authentication();
        auth.setVisible(true);
    }//GEN-LAST:event_disconnectActionPerformed

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_exitActionPerformed

    private void newGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newGameActionPerformed
        // TODO add your handling code here:
        panel.repaint();
    }//GEN-LAST:event_newGameActionPerformed

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
    private javax.swing.JButton disconnect;
    private javax.swing.JMenuItem exit;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JLabel lblBlack;
    private javax.swing.JLabel lblScoreBlack;
    private javax.swing.JLabel lblScoreWhite;
    private javax.swing.JLabel lblWhite;
    private javax.swing.JMenuItem newGame;
    private javax.swing.JPanel panel;
    private javax.swing.JLabel user;
    // End of variables declaration//GEN-END:variables
}
