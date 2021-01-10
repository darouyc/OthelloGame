/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package othellogame;

import Authentication.Authentication;
import game.Game;
import game.MyLabel;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import javax.swing.ImageIcon;

/**
 *
 * @author Abdellah
 */
public class OthelloInterface extends javax.swing.JFrame {

    /**
     * Creates new form OthelloInterface
     */
    Game game;
    
    // change round for players
    boolean tour = false;
    
    public OthelloInterface() {
         
        //display interface
        initComponents();
        setLocationRelativeTo(null);
        initial();
    }

    //Initilize the panel
    public void initial() {
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
        
        //get possibilities to first player
        trace(1);
        
        //change background for 4 first tokens
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

                //change state get won tokens and change it 
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
            } 
            
            //player 2 
            else {
                //add black icon
                label.setIcon(new ImageIcon(getClass().getResource("/othellogame/blackdice.png")));
                label.setContent(2);
                //add label to matrix with content 2 == black
                changeStat(label);

                System.out.println("-----------done----------");
                
                //change state get won tokens and change it 
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
    
    // get possibilities to player
    private void trace(int playerContent) {
        
        try {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    //get used position with content 
                    if (game.getLabel(i, j).getContent() == playerContent) {
                        if (verifyColumn(j, game.getLabel(i, j))) {
                            //Column
                            
                            //high
                            if (game.verifyPosition(game.getLabel(i + 1, j))) {
                                game.getLabel(i + 1, j).setBackground(Color.red);
                                game.getLabel(i + 1, j).setEnabled(true);

                            }
                            //bottom
                            if (game.verifyPosition(game.getLabel(i - 1, j))) {
                                game.getLabel(i - 1, j).setBackground(Color.red);
                                game.getLabel(i - 1, j).setEnabled(true);
                            }
                        }
                        //Line
                        //Right
                        if (verifyLine(i, game.getLabel(i, j))) {
                            if (game.verifyPosition(game.getLabel(i, j + 1))) {
                                game.getLabel(i, j + 1).setBackground(Color.red);
                                game.getLabel(i, j + 1).setEnabled(true);
                            }
                            //Left
                            if (game.verifyPosition(game.getLabel(i, j - 1))) {
                                game.getLabel(i, j - 1).setBackground(Color.red);
                                game.getLabel(i, j - 1).setEnabled(true);
                            }
                        }

                        //Diagonal
                        if (verifyDiagonal(j, game.getLabel(i, j))) {
                            // right && high 
                            if (game.verifyPosition(game.getLabel(i + 1, j + 1)) && game.getLabel(i - 1, j - 1).getContent() == switchContent(playerContent)) {
                                game.getLabel(i + 1, j + 1).setBackground(Color.red);
                                game.getLabel(i + 1, j + 1).setEnabled(true);
                            }
                            //Left && buttom
                            if (game.verifyPosition(game.getLabel(i - 1, j - 1)) && game.getLabel(i + 1, j + 1).getContent() == switchContent(playerContent)) {
                                game.getLabel(i - 1, j - 1).setBackground(Color.red);
                                game.getLabel(i - 1, j - 1).setEnabled(true);
                            }
                            //Left && high
                            if (game.verifyPosition(game.getLabel(i - 1, j + 1)) && game.getLabel(i + 1, j - 1).getContent() == switchContent(playerContent)) {
                                game.getLabel(i - 1, j + 1).setBackground(Color.red);
                                game.getLabel(i - 1, j + 1).setEnabled(true);
                            }
                            //right && bottom
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

    // verify if there is 2 tokens with different color in same line
    public boolean verifyLine(int line, MyLabel lbl) {
        for (int column = 0; column < 8; column++) {
            if (game.getLabel(line, column).getContent() == switchContent(lbl.getContent()) && game.getLabel(line, column) != lbl) {
                return true;
            }
        }
        return false;
    }

    // verify if there is 2 tokens with different color in same column
    public boolean verifyColumn(int column, MyLabel lbl) {
        for (int line = 0; line < 8; line++) {
            if (game.getLabel(line, column).getContent() == switchContent(lbl.getContent()) && game.getLabel(line, column) != lbl) {
                return true;
            }
        }
        return false;
    }
   
    // verify if there is 2 tokens with different color in same diagonal
    public boolean verifyDiagonal(int j, MyLabel lbl) {
        //Calcul distance entre diag et position de label
        int dist = 0;

        //To localize the diagonal.column for each row
        int diagonal = 0;

        //To calculate distance based on normal diagonal
        for (int line = 0; line < 8; line++) {
            for (int column = 0; column < 8; column++) {

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
                    if (game.getLabel(line, column + dist).getContent() == switchContent(lbl.getContent())) {
                        return true;
                    }
                }
                if (column <= line && (column - dist >= 0)) {
                    if (game.getLabel(line, column - dist).getContent() == switchContent(lbl.getContent())) {
                        return true;
                    }
                }
            }
        }

        //To verify the opposite label's diagonal
        for (int line = 0; line < 8; line++) {
            for (int column = 7; column >= 0; column--) {
                if (column > line && (column - dist > 0)) {
//                    if (game.getLabel(line, column + dist).getContent() == switchContent(lbl.getContent()) ) {
                    if (game.getLabel(line, column - dist).getContent() == lbl.getContent()) {
                        return true;
                    }
                }
                if (column <= line && (column + dist < 8)) {
//                    if (game.getLabel(line, column - dist).getContent() == switchContent(lbl.getContent()) ) {
                    if (game.getLabel(line, column + dist).getContent() == lbl.getContent()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
    
    // switch players change background inside panel
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
                   
                    //add token to player
                    if (pos.getContent() == 1) {
                        game.getPlayer1().addJetons(game.getLabel(i, j));
                    }

                    if (pos.getContent() == 2) {
                        game.getPlayer2().addJetons(game.getLabel(i, j));
                    }
                }
            }
        }

    }
    
    
   //switch tokens  
    public void checkBetween(MyLabel lbl) {
        
        //initializ position by -1 -1
        int nextLine = -1;
        int nextColumn = -1;

        //reverse lines to other player
        for (int j = 0; j < 8; j++) {
            
            // get other token same color in line for the lbl (played by player) and different of lbl 
            if (game.getLabel(lbl.getLine(), j).getContent() == lbl.getContent() && game.getLabel(lbl.getLine(), j) != lbl) {
             
                // get top column 
                if (lbl.getColumn() > j) {
                    for (int b = j + 1; b < lbl.getColumn(); b++) {
                        game.getLabel(lbl.getLine(), b).setContent(lbl.getContent());
                        game.getPlayer(switchContent(lbl.getContent())).removeJeton(game.getLabel(lbl.getLine(), b));
                        game.getPlayer(lbl.getContent()).addJetons(game.getLabel(lbl.getLine(), b));
                        drawJeton(game.getLabel(lbl.getLine(), b), lbl.getContent());
                    }
                }
                if (lbl.getColumn() <= j) {
                    for (int b = lbl.getColumn() + 1; b < j; b++) {
                        game.getLabel(lbl.getLine(), b).setContent(lbl.getContent());
                        game.getPlayer(switchContent(lbl.getContent())).removeJeton(game.getLabel(lbl.getLine(), b));
                        game.getPlayer(lbl.getContent()).addJetons(game.getLabel(lbl.getLine(), b));
                        drawJeton(game.getLabel(lbl.getLine(), b), lbl.getContent());
                    }
                }

            }
        }
        //reverse columns to other player
        for (int i = 0; i < 8; i++) {
            
            // get other token same color in column for the lbl (played by player) and different of lbl 
            if (game.getLabel(i, lbl.getColumn()).getContent() == lbl.getContent() && game.getLabel(i, lbl.getColumn()) != lbl) {
                
                //get left token
                if (lbl.getLine() > i) {
                    for (int b = i + 1; b < lbl.getLine(); b++) {
                        game.getLabel(b, lbl.getColumn()).setContent(lbl.getContent());
                        game.getPlayer(switchContent(lbl.getContent())).removeJeton(game.getLabel(b, lbl.getColumn()));
                        game.getPlayer(lbl.getContent()).addJetons(game.getLabel(b, lbl.getColumn()));
                        drawJeton(game.getLabel(b, lbl.getColumn()), lbl.getContent());
                    }
                }
                if (lbl.getLine() <= i) {
                    for (int b = lbl.getLine() + 1; b < i; b++) {
                        game.getLabel(b, lbl.getColumn()).setContent(lbl.getContent());
                        game.getPlayer(switchContent(lbl.getContent())).removeJeton(game.getLabel(b, lbl.getColumn()));
                        game.getPlayer(lbl.getContent()).addJetons(game.getLabel(b, lbl.getColumn()));
                        drawJeton(game.getLabel(b, lbl.getColumn()), lbl.getContent());
                    }
                }

            }
        }

        //Reverse diagonale to other player
        //Calcul distance entre diag et position de label
        int dist = 0;

        //To localize the diagonal.column for each row
        int diagonal = 0;

        for (int lineDia = 0; lineDia < 8; lineDia++) {
            for (int columnDia = 0; columnDia < 8; columnDia++) {
                if (lineDia == columnDia) {
                    diagonal = columnDia;
                }
                if (game.getLabel(lineDia, columnDia) == lbl && columnDia > lineDia) {
                    dist = columnDia - diagonal;
                }
                if (game.getLabel(lineDia, columnDia) == lbl && columnDia < lineDia) {
                    dist = diagonal - columnDia;
                }
                System.out.println("Distance is:" + dist);
            }
        }

        //Get next position 
        for (int lineDia = 0; lineDia < 8 && nextLine == -1; lineDia++) {
            for (int columnDia = 0; columnDia < 8 && nextColumn == -1; columnDia++) {
                if (lbl.getColumn() > lbl.getLine() && (columnDia + dist < 8)) {
                    if (game.getLabel(lineDia, columnDia + dist).getContent() == lbl.getContent() && game.getLabel(lineDia, columnDia + dist) != lbl) {
                        nextColumn = columnDia + dist;
                        nextLine = lineDia;
                        System.out.println(" 1 === nextLine " + nextLine + " nextColumn " + nextColumn);
                        break;
                    }
                }
                if (lbl.getColumn() <= lbl.getLine() && (columnDia - dist >= 0)) {
                    if (game.getLabel(lineDia, columnDia - dist).getContent() == lbl.getContent() && game.getLabel(lineDia, columnDia - dist) != lbl) {

                        nextColumn = columnDia - dist;
                        nextLine = lineDia;
                        System.out.println(" 2 ==== nextLine " + nextLine + " nextColumn " + nextColumn);
                        break;

                    }
                }
                
            }
        }
//           for (int lineDia = 0; lineDia < 8 && nextLine == -1; lineDia++) {
//            for (int columnDia = 7; columnDia >= 0 && nextColumn == -1; columnDia++) {
//
//                if (column > line && (columnDia - dist > 0)) {
//                    if (game.getLabel(lineDia, columnDia - dist).getContent() == lbl.getContent() && game.getLabel(lineDia, columnDia - dist) != lbl) {
//                        nextColumn = columnDia - dist;
//                        nextLine = lineDia;
//                        System.out.println(" 3 ===== nextLine " + nextLine + " nextColumn " + nextColumn);
//                        break;
//                    }
//                }
//
//                if (column <= line && (columnDia + dist < 8)) {
//                    if (game.getLabel(lineDia, columnDia + dist).getContent() == lbl.getContent() && game.getLabel(lineDia, columnDia + dist) != lbl) {
//
//                        nextColumn = columnDia + dist;
//                        nextLine = lineDia;
//                        System.out.println(" 4 === nextLine " + nextLine + " nextColumn " + nextColumn);
//                        break;
//                    }
//                }
//
//            }
//        }
//
        if (nextColumn < lbl.getColumn() && nextLine < lbl.getLine()) {
            System.out.println(" 1************88column " + lbl.getColumn() + " line " + lbl.getLine());
            for (int i = nextLine + 1, j = nextColumn + 1; i < lbl.getLine() && j < lbl.getColumn(); i++, j++) {
                {

                    if (i < j && (j + dist < 8)) {
                        game.getLabel(i, j + dist).setContent(lbl.getContent());
                        System.out.println(" 1==== i " + i + " j+dist " + (j + dist));
                        game.getPlayer(switchContent(lbl.getContent())).removeJeton(game.getLabel(i, j + dist));
                        game.getPlayer(lbl.getContent()).addJetons(game.getLabel(i, j + dist));
                        drawJeton(game.getLabel(i, j + dist), lbl.getContent());

                    }
                    if (i >= j && (j - dist >= 0)) {
                        game.getLabel(i, j - dist).setContent(lbl.getContent());
                        System.out.println(" 1 ==== i " + i + " j- dist " + (j - dist));
                        game.getPlayer(switchContent(lbl.getContent())).removeJeton(game.getLabel(i, j - dist));
                        game.getPlayer(lbl.getContent()).addJetons(game.getLabel(i, j - dist));
                        drawJeton(game.getLabel(i, j - dist), lbl.getContent());

                    }
                }
            }
        }
        if (nextColumn > lbl.getColumn() && nextLine < lbl.getLine()) {
            System.out.println(" 2 **************8line " + lbl.getLine() + " column " + lbl.getColumn());
            for (int i = nextLine + 1, j = lbl.getColumn() + 1; j < nextColumn && i < lbl.getLine(); i++, j++) {

                {
                    System.out.println("**********************///////////////////////////////// ");
                    if (i < j && (j + dist < 8)) {
                        game.getLabel(i, j + dist).setContent(lbl.getContent());
                        System.out.println(" 2====i " + i + " (j+dist) " + (j + dist));
                        game.getPlayer(switchContent(lbl.getContent())).removeJeton(game.getLabel(i, j + dist));
                        game.getPlayer(lbl.getContent()).addJetons(game.getLabel(i, j + dist));
                        drawJeton(game.getLabel(i, j + dist), lbl.getContent());

                    }
                    if (i >= j && (j - dist >= 0)) {
                        game.getLabel(i, j - dist).setContent(lbl.getContent());
                        System.out.println(" 2====i " + i + " j+dist " + (j + dist));
                        game.getPlayer(switchContent(lbl.getContent())).removeJeton(game.getLabel(i, j - dist));
                        game.getPlayer(lbl.getContent()).addJetons(game.getLabel(i, j - dist));
                        drawJeton(game.getLabel(i, j - dist), lbl.getContent());

                    }
                }
            }
        }
        if (nextColumn < lbl.getColumn() && nextLine >= lbl.getLine()) {
            System.out.println(" 3***************line " + lbl.getLine() + " column " + lbl.getColumn());
            for (int i = lbl.getLine() + 1, j = nextColumn + 1; i < nextLine && j < lbl.getColumn(); i++, j++) {
                {
                    System.out.println("**********************///////////////////////////////// ");
                    if (i < j && (j + dist < 8)) {
                        game.getLabel(i, j + dist).setContent(lbl.getContent());
                        System.out.println("3============= i " + i + " j+dist " + (j + dist));
                        game.getPlayer(switchContent(lbl.getContent())).removeJeton(game.getLabel(i, j + dist));
                        game.getPlayer(lbl.getContent()).addJetons(game.getLabel(i, j + dist));
                        drawJeton(game.getLabel(i, j + dist), lbl.getContent());
                    }
                    if (i >= j && (j - dist >= 0)) {
                        game.getLabel(i, j - dist).setContent(lbl.getContent());
                        System.out.println(" 3========i " + i + " j- dist " + (j - dist));
                        game.getPlayer(switchContent(lbl.getContent())).removeJeton(game.getLabel(i, j - dist));
                        game.getPlayer(lbl.getContent()).addJetons(game.getLabel(i, j - dist));
                        drawJeton(game.getLabel(i, j - dist), lbl.getContent());
                    }
                }
            }
        }
        if (nextColumn >= lbl.getColumn() && nextLine >= lbl.getLine()) {
            System.out.println(" 4***************line " + lbl.getColumn() + " column " + lbl.getColumn());
            for (int i = lbl.getLine() + 1, j = lbl.getColumn() + 1; i < nextLine && j < nextColumn; i++, j++) {
                {
                    System.out.println("**********************///////////////////////////////// ");
                    if (i < j && (j + dist < 8)) {
                        game.getLabel(i, j + dist).setContent(lbl.getContent());
                        System.out.println(" 4==========i " + i + " j+dist " + (j + dist));
                        game.getPlayer(switchContent(lbl.getContent())).removeJeton(game.getLabel(i, j + dist));
                        game.getPlayer(lbl.getContent()).addJetons(game.getLabel(i, j + dist));
                        drawJeton(game.getLabel(i, j + dist), lbl.getContent());
                    }
                    if (i >= j && (j - dist >= 0)) {
                        game.getLabel(i, j - dist).setContent(lbl.getContent());
                        System.out.println(" 4 ========i " + i + " j-dist " + (j - dist));
                        game.getPlayer(switchContent(lbl.getContent())).removeJeton(game.getLabel(i, j - dist));
                        game.getPlayer(lbl.getContent()).addJetons(game.getLabel(i, j - dist));
                        drawJeton(game.getLabel(i, j - dist), lbl.getContent());
                    }
                }
            }
        }

    }
    
    // change value of content
    public int switchContent(int content) {
        if (content == 1) {
            return 2;
        } else if (content == 2) {
            return 1;
        }
        return 0;
    }
    
    // draw token 
    public void drawJeton(MyLabel lbl, int content) {
        if (content == 2) {
            lbl.setIcon(new ImageIcon(getClass().getResource("/othellogame/blackdice.png")));
        } else if (content == 1) {
            lbl.setIcon(new ImageIcon(getClass().getResource("/othellogame/whitedice.png")));
        }
    }
    
    // display username inside interface
    public void setUser(String username) {
        user.setText(username);
    }
    
    //disconnect user
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
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        newGame = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        exit = new javax.swing.JMenuItem();

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
            .addGap(0, 0, Short.MAX_VALUE)
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblScoreWhite)
                    .addComponent(lblScoreBlack))
                .addGap(84, 84, 84))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(74, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblBlack, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblWhite, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(lblWhite)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblScoreWhite)
                .addGap(155, 155, 155)
                .addComponent(lblScoreBlack)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblBlack)
                .addContainerGap(56, Short.MAX_VALUE))
        );

        disconnect.setBackground(new java.awt.Color(255, 255, 255));
        disconnect.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        disconnect.setForeground(new java.awt.Color(0, 0, 0));
        disconnect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/othellogame/Disconnect.png"))); // NOI18N
        disconnect.setText("Disconnect");
        disconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disconnectActionPerformed(evt);
            }
        });

        user.setBackground(new java.awt.Color(0, 0, 0));
        user.setFont(new java.awt.Font("Gill Sans MT", 1, 24)); // NOI18N
        user.setForeground(new java.awt.Color(60, 153, 128));
        user.setText("Guest");

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Game");

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

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(227, 227, 227)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(user, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(111, 111, 111))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(disconnect, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(72, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(user)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 24, Short.MAX_VALUE)
                .addComponent(disconnect, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(77, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void panelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelMouseClicked
        // TODO add your handling code here:
        Point pos = new Point();
        
        //get clicked position
        pos.setLocation(evt.getPoint());
        
        //get component by position
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
        //clean all parameters to start game
        panel.removeAll();
        // initialz round
        tour = false;
        initial();
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
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
