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
    // MyLabel[][] lbls = new MyLabel[8][8];

    public OthelloInterface() {
        initComponents();
        setLocationRelativeTo(null);

        //create grille 
        GridLayout grdlyt = new GridLayout(8, 8);

        //initialization game
        game = new Game(this.panel);
        this.panel.setLayout(grdlyt);

        //Score icon
        lblWhite.setIcon(new ImageIcon(getClass().getResource("/othellogame/whitedice.png")));
        lblBlack.setIcon(new ImageIcon(getClass().getResource("/othellogame/blackdice.png")));
        
        // show score for players
        lblScoreBlack.setText(" " + game.getPlayer1().getScore());
        lblScoreWhite.setText(" " + game.getPlayer2().getScore());

        //Update matrix
        first();
    }

    public void first() {
        trace(1);
        game.getLabel(3, 4).setBackground(new Color(40, 100, 28));
        game.getLabel(3, 4).setEnabled(true);
        game.getLabel(4, 3).setBackground(new Color(40, 100, 28));
        game.getLabel(4, 3).setEnabled(true);
        game.getLabel(3, 3).setBackground(new Color(40, 100, 28));
        game.getLabel(3, 3).setEnabled(true);
        game.getLabel(4, 4).setBackground(new Color(40, 100, 28));
        game.getLabel(4, 4).setEnabled(true);
    }

    public void play(Point pos) {
       
        changePlayer();

        //get selected label
        MyLabel label = (MyLabel) this.panel.getComponentAt(pos);

        //verify label if is empty
        if (game.verifyPosition(label)) {

            // player 1
            if (tour) {
                //add white icon
                label.setIcon(new ImageIcon(getClass().getResource("/othellogame/whitedice.png")));
                label.setContent(1);
                //add label to matrix with content 1 == white 
                changeStat(label);

                System.out.println("-----------done----------");

                //add Jeton to player array 
                //game.getPlayer1().addJetons(label);
                checkBetween(label);
                game.displayJetons();
                //Disable All labels
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (game.getLabel(i, j).getContent() == 0) {
                            game.getLabel(i, j).setEnabled(false);
                        }
                    }
                }

                //display possibilities to next player
                trace(1);

                //change player
                tour = false;
                label.setBackground(new Color(40, 100, 28));
            } //player 2 
            else {
                //add black icon
                label.setIcon(new ImageIcon(getClass().getResource("/othellogame/blackdice.png")));
                label.setContent(2);
                //add label to matrix with content 2 == black
                changeStat(label);

                System.out.println("-----------done----------");

                //add Jeton to player array
                //game.getPlayer2().addJetons(label);
                checkBetween(label);
                game.displayJetons();

                //Disable All labels
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (game.getLabel(i, j).getContent() == 0) {
                            game.getLabel(i, j).setEnabled(false);
                        }
                    }
                }

                //display possibilities to next player
                trace(2);

                //change player
                tour = true;
                label.setBackground(new Color(40, 100, 28));
            }
        } else {
            // used position
            System.out.println("------Not available-------");

            // display possibilities
            if (tour) {
                trace(2);
            } else {
                trace(1);
            }
        }
        //Update score
        lblScoreBlack.setText(" " + game.getPlayer2().getScore());
        //Update score
        lblScoreWhite.setText(" " + game.getPlayer1().getScore());

    }

    private void trace(int playerContent) {
//        boolean win= true;
        try {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    //get used postion with content 
                    if (game.getLabel(i, j).getContent() == playerContent) {
                        if (verifyColumn(j, game.getLabel(i, j))) 
                        {
                            //Column
                            if (game.verifyPosition(game.getLabel(i + 1, j))) {
                                game.getLabel(i + 1, j).setBackground(Color.red);
                                game.getLabel(i + 1, j).setEnabled(true);
//                                win = false;
                            }
                            if (game.verifyPosition(game.getLabel(i - 1, j))) {
                                game.getLabel(i - 1, j).setBackground(Color.red);
                                game.getLabel(i - 1, j).setEnabled(true);
                            }
                        }
                            //Ligne
                            if(verifyLine(i, game.getLabel(i, j)))
                            {
                                if (game.verifyPosition(game.getLabel(i, j + 1))) {
                                    game.getLabel(i, j + 1).setBackground(Color.red);
                                    game.getLabel(i, j + 1).setEnabled(true);
                                }
                                if (game.verifyPosition(game.getLabel(i, j - 1))) {
                                    game.getLabel(i, j - 1).setBackground(Color.red);
                                    game.getLabel(i, j - 1).setEnabled(true);
                                }
                            }

//                            Diagonal
                            if(verifyDiagonal(j,game.getLabel(i, j)))
                            {
                                if (game.verifyPosition(game.getLabel(i + 1, j + 1)) && game.getLabel(i - 1, j - 1).getContent() == switchContent(playerContent) ) {
                                    game.getLabel(i + 1, j + 1).setBackground(Color.red);
                                    game.getLabel(i + 1, j + 1).setEnabled(true);
                                }
                                if (game.verifyPosition(game.getLabel(i - 1, j - 1)) && game.getLabel(i + 1, j + 1).getContent() == switchContent(playerContent) ) {
                                    game.getLabel(i - 1, j - 1).setBackground(Color.red);
                                    game.getLabel(i - 1, j - 1).setEnabled(true);
                                }
                                if (game.verifyPosition(game.getLabel(i - 1, j + 1)) && game.getLabel(i + 1, j - 1).getContent() == switchContent(playerContent)) {
                                    game.getLabel(i - 1, j + 1).setBackground(Color.red);
                                    game.getLabel(i - 1, j + 1).setEnabled(true);
                                }
                                if (game.verifyPosition(game.getLabel(i + 1, j - 1)) && game.getLabel(i - 1, j + 1).getContent() == switchContent(playerContent)) {
                                    game.getLabel(i + 1, j - 1).setBackground(Color.red);
                                    game.getLabel(i + 1, j - 1).setEnabled(true);
                                }
                                
                            }
                    }
                }
            }
        } catch (Exception e) {

        }

    }

    //line
    public boolean verifyLine(int line, MyLabel lbl) {
        for (int column = 0; column < 8; column++) {
            if (game.getLabel(line, column).getContent() == switchContent(lbl.getContent()) && game.getLabel(line, column)  != lbl) {
                System.out.println("switch = "+lbl.getContent());
                return true;
            }
        }
        return false;
    }

    //column 
    public boolean verifyColumn(int column, MyLabel lbl) {
        for (int line = 0; line < 8; line++) {
            if (game.getLabel(line, column).getContent() == switchContent(lbl.getContent()) && game.getLabel(line, column)  != lbl) {
                return true;
            }
        }
        return false;
    }
    //diagonale

    public boolean verifyDiagonal(int j,MyLabel lbl) {
        //Calcul distance entre diag et position de label
        int dist = 0;

        //To localize the diagonal.column for each row
        int diagonal = 0;
        
        //To calculate distance based on normal diagonal
        for (int line = 0; line < 8; line++) {
            for (int column = 0; column < 8; column++) {
//                if (line == column) {
//                    diagonal = column;
//                }
                if (game.getLabel(line, column) == lbl && lbl.getColumn() > line) {
                    dist = lbl.getColumn() - line;
                }
                if (game.getLabel(line, column) == lbl && lbl.getColumn() < line) {
                    dist = line - lbl.getColumn();
                }
            }
        }
        
        //To verify the normal label's diagonal
        for (int line = 0; line < 8; line++) {
            for (int column = 0; column < 8; column++) {
                if (column > line && (column + dist < 8)) {
                    if (game.getLabel(line, column + dist).getContent() == switchContent(lbl.getContent()) ) {
//                    if (game.getLabel(line, column + dist).getContent() == lbl.getContent() ) {
                        return true;
                    }
                }
                if (column <= line && (column - dist >= 0)) {
                    if (game.getLabel(line, column - dist).getContent() == switchContent(lbl.getContent()) ) {
//                    if (game.getLabel(line, column - dist).getContent() == lbl.getContent() ) {
                        return true;
                    }
                }
            }
        }
        
        
        //To verify the opposite label's diagonal
        for (int line = 0; line < 8; line++) {
            for (int column = 7; column >=0; column--) {
                if (column > line && (column - dist > 0)) {
//                    if (game.getLabel(line, column + dist).getContent() == switchContent(lbl.getContent()) ) {
                    if (game.getLabel(line, column - dist).getContent() == lbl.getContent() ) {
                        return true;
                    }
                }
                if (column <= line && (column + dist < 8)) {
//                    if (game.getLabel(line, column - dist).getContent() == switchContent(lbl.getContent()) ) {
                    if (game.getLabel(line, column + dist).getContent() == lbl.getContent() ) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public void changePlayer() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                game.getLabel(i, j).setBackground(new Color(40, 100, 28));
            }
        }
    }

    public void changeStat(MyLabel pos) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                //get Label from matrix and change state from 0 == empty to 1 == white or 2 == black
                if (game.getLabel(i, j) == pos) {
                    System.out.println("change stat");
                    game.getLabel(i, j).setContent(pos.getContent());
                    game.getLabel(i, j).setBackground(new Color(40, 100, 28));
                    if (pos.getContent() == 1) {
                        game.getPlayer1().addJetons(game.getLabel(i, j));
                    }

                    if (pos.getContent() == 2) {
                        game.getPlayer2().addJetons(game.getLabel(i, j));
                    }
                    //game.setLbels(lbls);
                }
            }
        }

    }

    public void checkBetween(MyLabel lbl) {
        int line = 0;
        int column = 0;
        int nextLine = 0;
        int nextColumn = 0;

        //get my label indexes
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (game.getLabel(i, j) == lbl) {
                    line = i;
                    column = j;
                    System.out.println(" i "+i+" j "+j);
                }
            }
        }

        //reverse lines to other player
        for (int j = 0; j < 8; j++) {
            if (game.getLabel(line, j).getContent() == lbl.getContent() && game.getLabel(line, j) != lbl) {
                System.out.println("***********************");
                if (column > j) {
                    for (int b = j + 1; b < column; b++) {
                        game.getLabel(line, b).setContent(lbl.getContent());
                        game.getPlayer(switchContent(lbl.getContent())).removeJeton(game.getLabel(line, b));
                        game.getPlayer(lbl.getContent()).addJetons(game.getLabel(line, b));
                        drawJeton(game.getLabel(line, b), lbl.getContent());
                    }
                }
                if (column <= j) {
                    for (int b = column + 1; b < j; b++) {
                        game.getLabel(line, b).setContent(lbl.getContent());
                        game.getPlayer(switchContent(lbl.getContent())).removeJeton(game.getLabel(line, b));
                        game.getPlayer(lbl.getContent()).addJetons(game.getLabel(line, b));
                        drawJeton(game.getLabel(line, b), lbl.getContent());
                    }
                }

            }
        }
        //reverse columns to other player
        for (int i = 0; i < 8; i++) {
            if (game.getLabel(i, column).getContent() == lbl.getContent() && game.getLabel(i, column) != lbl) {
                System.out.println("***********************");
                if (line > i) {
                    for (int b = i + 1; b < line; b++) {
                        game.getLabel(b, column).setContent(lbl.getContent());
                        game.getPlayer(switchContent(lbl.getContent())).removeJeton(game.getLabel(b, column));
                        game.getPlayer(lbl.getContent()).addJetons(game.getLabel(b, column));
                        drawJeton(game.getLabel(b, column), lbl.getContent());
                    }
                }
                if (line <= i) {
                    for (int b = line + 1; b < i; b++) {
                        game.getLabel(b, column).setContent(lbl.getContent());
                        game.getPlayer(switchContent(lbl.getContent())).removeJeton(game.getLabel(b, column));
                        game.getPlayer(lbl.getContent()).addJetons(game.getLabel(b, column));
                        drawJeton(game.getLabel(b, column), lbl.getContent());
                    }
                }

            }
        }
//             
//             //Reverse diagonale to other player
//             //Calcul distance entre diag et position de label
//                int dist = 0;
//
//                //To localize the diagonal.column for each row
//                int diagonal = 0;
//
//                for(int lineDia = 0; lineDia<8 ; lineDia++)
//                    for(int columnDia = 0; columnDia<8 ; columnDia++){
//                        if(lineDia == columnDia) diagonal = columnDia;
//                        if(game.getLabel(lineDia, columnDia) == lbl && columnDia>lineDia)
//                            dist = columnDia - diagonal;
//                        if(game.getLabel(lineDia, columnDia) == lbl && columnDia<lineDia)
//                            dist = diagonal - columnDia;
//                    }   
//
//                //Get next position
//                for(int lineDia = 0; lineDia<8 ; lineDia++)
//                    for(int columnDia = 0; columnDia<8 ; columnDia++){
//                        if(columnDia>lineDia && (columnDia+dist < 8) )
//                        {
//                            if(game.getLabel(lineDia, columnDia+dist).getContent() == lbl.getContent() && game.getLabel(lineDia, columnDia+dist)!= lbl)
//                            {
//                              
//                                nextColumn = columnDia;
//                                nextLine = lineDia;  
//                                System.out.println(" nextLine "+nextLine+" nextColumn "+ nextColumn);
//                            }
//                        }
//                        if(columnDia <= lineDia && (columnDia-dist >= 0))
//                        {
//                            if(game.getLabel(lineDia, columnDia - dist).getContent() == lbl.getContent() && game.getLabel(lineDia, columnDia - dist)!=lbl)
//                            {
//                               
//                                nextColumn = columnDia;
//                                nextLine = lineDia;  
//                                System.out.println(" nextLine "+nextLine+" nextColumn "+ nextColumn);
//                            }
//                        }
//                    }
//                if(nextColumn < column && nextLine< line)
//                {
//                    System.out.println(" nextLine "+nextLine+" line "+ line);
//                    for(int i = nextLine ; i<line ; i++)
//                    {
//                        for(int j = nextColumn + 1 ; j < column; j++)
//                        {
//                            if(i < j && (j + dist < 8))
//                            {
//                                game.getLabel(i,j+dist).setContent(lbl.getContent());
//                                System.out.println(" j+dist "+(j+dist)+" i "+ i);
//                                game.getPlayer(switchContent(lbl.getContent())).removeJeton(game.getLabel(i,j+dist));
//                                game.getPlayer(lbl.getContent()).addJetons(game.getLabel(i, j+dist));
//                                drawJeton(game.getLabel(i,j+dist), lbl.getContent());
//                            }
//                             if(i >= j && (j - dist >=0))
//                            {
//                                game.getLabel(i ,j- dist).setContent(lbl.getContent());
//                                System.out.println(" i "+i+" j- dist "+ (j- dist));
//                                game.getPlayer(switchContent(lbl.getContent())).removeJeton(game.getLabel(i,j-dist));
//                                game.getPlayer(lbl.getContent()).addJetons(game.getLabel(i, j-dist));
//                                drawJeton(game.getLabel(i,j-dist), lbl.getContent());
//                            }
//                        }
//                    }
//                }
//                if(nextColumn >= column && nextLine< line)
//                {
//                    System.out.println(" nextLine "+nextLine+" line "+ line);
//                     for(int i = nextLine ; i<line ; i++)
//                    {
//                        for(int j = column +1 ; j < nextColumn ; j++)
//                        {
//                              System.out.println("**********************///////////////////////////////// ");
//                            if(i < j && (j + dist < 8))
//                            {
//                                game.getLabel(i,j+dist).setContent(lbl.getContent());
//                                System.out.println(" i "+i+" (j+dist) "+ (j+dist));
//                                game.getPlayer(switchContent(lbl.getContent())).removeJeton(game.getLabel(i,j+dist));
//                                game.getPlayer(lbl.getContent()).addJetons(game.getLabel(i, j+dist));
//                                drawJeton(game.getLabel(i,j+dist), lbl.getContent());
//                            }
//                             if(i >= j && (j - dist >= 0))
//                            {
//                                game.getLabel(i ,j- dist).setContent(lbl.getContent());
//                                System.out.println(" i "+i+" j+dist "+ (j+dist));
//                                game.getPlayer(switchContent(lbl.getContent())).removeJeton(game.getLabel(i, j-dist));
//                                game.getPlayer(lbl.getContent()).addJetons(game.getLabel(i, j-dist));
//                                drawJeton(game.getLabel(i, j-dist), lbl.getContent());
//                            }
//                        }
//                    }
//                }
//                if(nextColumn < column && nextLine >= line)
//                {
//                    System.out.println(" nextLine "+nextLine+" line "+ line);
//                    for(int i = line ; i<nextLine ; i++)
//                    {
//                        for(int j = nextColumn + 1 ; j < column; j++)
//                        {
//                             System.out.println("**********************///////////////////////////////// ");
//                            if(i < j && (j + dist < 8))
//                            {
//                                game.getLabel(i,j+dist).setContent(lbl.getContent());
//                                System.out.println(" j+dist "+(j+dist)+" i "+ i);
//                                game.getPlayer(switchContent(lbl.getContent())).removeJeton(game.getLabel(i,j+dist));
//                                game.getPlayer(lbl.getContent()).addJetons(game.getLabel(i, j+dist));
//                                drawJeton(game.getLabel(i,j+dist), lbl.getContent());
//                            }
//                             if(i >= j && (j - dist >=0))
//                            {
//                                game.getLabel(i ,j- dist).setContent(lbl.getContent());
//                                System.out.println(" i "+i+" j- dist "+ (j- dist));
//                                game.getPlayer(switchContent(lbl.getContent())).removeJeton(game.getLabel(i,j-dist));
//                                game.getPlayer(lbl.getContent()).addJetons(game.getLabel(i, j-dist));
//                                drawJeton(game.getLabel(i,j-dist), lbl.getContent());
//                            }
//                        }
//                    }
//                }
//                 if(nextColumn >= column && nextLine >= line)
//                {
//                    System.out.println(" nextLine "+nextLine+" line "+ line);
//                     for(int i = line ; i<nextLine ; i++)
//                    {
//                        for(int j = column +1 ; j < nextColumn ; j++)
//                        {
//                              System.out.println("**********************///////////////////////////////// ");
//                            if(i < j && (j + dist < 8))
//                            {
//                                game.getLabel(i,j+dist).setContent(lbl.getContent());
//                                System.out.println(" i "+i+" (j+dist) "+ (j+dist));
//                                game.getPlayer(switchContent(lbl.getContent())).removeJeton(game.getLabel(i,j+dist));
//                                game.getPlayer(lbl.getContent()).addJetons(game.getLabel(i, j+dist));
//                                drawJeton(game.getLabel(i,j+dist), lbl.getContent());
//                            }
//                             if(i >= j && (j - dist >= 0))
//                            {
//                                game.getLabel(i ,j- dist).setContent(lbl.getContent());
//                                System.out.println(" i "+i+" j+dist "+ (j+dist));
//                                game.getPlayer(switchContent(lbl.getContent())).removeJeton(game.getLabel(i, j-dist));
//                                game.getPlayer(lbl.getContent()).addJetons(game.getLabel(i, j-dist));
//                                drawJeton(game.getLabel(i, j-dist), lbl.getContent());
//                            }
//                        }
//                    }
//                }
//                
        
    }

    public int switchContent(int content) {
        if (content == 1) {
            return 2;
        } else if(content == 2) {
            return 1;
        } return 0;
    }

    public void drawJeton(MyLabel lbl, int content) {
        if (content == 2) {
            lbl.setIcon(new ImageIcon(getClass().getResource("/othellogame/blackdice.png")));
        } else if (content == 1) {
            lbl.setIcon(new ImageIcon(getClass().getResource("/othellogame/whitedice.png")));
        }
    }

    public void setUser(String username) {
        user.setText(username);
    }

    public void setDisconnectButton(String s) {
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
        MyLabel ana = (MyLabel) panel.getComponentAt(pos);
        if (ana.isEnabled() == true) {
            play(pos);
        }

        //show matrix in console
        for (int i = 0; i < 8; i++) {
            System.out.println(" ");
            for (int j = 0; j < 8; j++) {
                System.out.print(" " + game.getLabel(i, j).getContent() + " ");
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
