import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

import com.formdev.flatlaf.intellijthemes.*;

public class App {
    private void createGUI() {
        frame = new JFrame("Calculator");
        frame.setSize(new Dimension(500, 500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FlatArcIJTheme.setup();
        //
        // INITIALIZE COMPONENTS
        //
        pnl_MainPanel = new JPanel();
        pnl_GamePanel = new JPanel();
        pnl_Box00 = new JPanel();
        pnl_Box10 = new JPanel();
        pnl_Box20 = new JPanel();
        pnl_Box01 = new JPanel();
        pnl_Box11 = new JPanel();
        pnl_Box21 = new JPanel();
        pnl_Box02 = new JPanel();
        pnl_Box12 = new JPanel();
        ;
        pnl_Box22 = new JPanel();
        pnl_ControlPanel = new JPanel();
        lbl_PlayerTurn = new JLabel();
        btn_ResetButton = new JButton();
        //
        // COMPONENT PROPERTIES
        //
        pnl_MainPanel.setLayout(new GridBagLayout());
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.weightx = 0.5;

        pnl_GamePanel.setLayout(new GridBagLayout());
        c.gridy = 0;
        c.weighty = 0.8;
        pnl_MainPanel.add(pnl_GamePanel, c);

        JPanel[] GameBoxes = { pnl_Box00, pnl_Box10, pnl_Box20, pnl_Box01, pnl_Box11, pnl_Box21, pnl_Box02, pnl_Box12,
                pnl_Box22 };
        int count = 0;
        for (JPanel pnl_GameBox : GameBoxes) {
            pnl_GameBox.setName(count - (3 * (int) Math.floor(count / 3.0)) + "" + (int) Math.floor(count / 3.0));
            pnl_GameBox.setBackground(Color.GRAY);
            pnl_GameBox.setBorder(BorderFactory.createLineBorder(Color.black));
            c.fill = GridBagConstraints.BOTH;
            c.gridx = count - (3 * (int) Math.floor(count / 3.0));
            c.gridy = (int) Math.floor(count / 3.0);
            c.weighty = 0.5;
            pnl_GamePanel.add(pnl_GameBox, c);
            count++;
        }

        pnl_ControlPanel.setLayout(s);
        c.gridx = 0;
        c.gridy = 1;
        c.weighty = 0.2;
        pnl_MainPanel.add(pnl_ControlPanel, c);

        lbl_PlayerTurn.setText("Player 1's Turn!");
        pnl_ControlPanel.add(lbl_PlayerTurn);
        s.putConstraint(SpringLayout.NORTH, lbl_PlayerTurn, 5, SpringLayout.NORTH, pnl_ControlPanel);
        s.putConstraint(SpringLayout.WEST, lbl_PlayerTurn, 5, SpringLayout.WEST, pnl_ControlPanel);

        btn_ResetButton.setText("Reset Game");
        pnl_ControlPanel.add(btn_ResetButton);
        s.putConstraint(SpringLayout.NORTH, btn_ResetButton, 5, SpringLayout.SOUTH, lbl_PlayerTurn);
        s.putConstraint(SpringLayout.WEST, btn_ResetButton, 0, SpringLayout.WEST, lbl_PlayerTurn);
        //
        // SHOW APPLICATION
        //
        frame.getContentPane().add(pnl_MainPanel);
        frame.setVisible(true);
    }

    private void createEvents() {
        //
        // GAME BOXES
        //
        JPanel[] GameBoxes = { pnl_Box00, pnl_Box10, pnl_Box20, pnl_Box01, pnl_Box11, pnl_Box21, pnl_Box02, pnl_Box12,
                pnl_Box22 };
        for (JPanel pnl_GameBox : GameBoxes) {
            pnl_GameBox.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(gameOver) { return; }
                    int x = Integer.parseInt(pnl_GameBox.getName().substring(0, 1));
                    int y = Integer.parseInt(pnl_GameBox.getName().substring(1));
                    if (gameBoard[x][y] != 0) {
                        return;
                    }
                    if (playerOneTurn) {
                        gameBoard[x][y] = 1;
                        pnl_GameBox.setBackground(Color.RED);
                        lbl_PlayerTurn.setText("Player 2's Turn!");
                    } else {
                        gameBoard[x][y] = 2;
                        pnl_GameBox.setBackground(Color.BLUE);
                        lbl_PlayerTurn.setText("Player 1's Turn!");
                    }
                    playerOneTurn = !playerOneTurn;
                    checkForWin();
                }

                @Override
                public void mousePressed(MouseEvent e) {}

                @Override
                public void mouseReleased(MouseEvent e) {}

                @Override
                public void mouseEntered(MouseEvent e) {}

                @Override
                public void mouseExited(MouseEvent e) {}
            });
        }
        //
        // RESET BUTTON
        //
        btn_ResetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Reset Game State
                Arrays.stream(gameBoard).forEach(i -> Arrays.fill(i, 0));

                JPanel[] GameBoxes = { pnl_Box00, pnl_Box10, pnl_Box20, pnl_Box01, pnl_Box11, pnl_Box21, pnl_Box02, pnl_Box12,
                    pnl_Box22 };
                for (JPanel pnl_GameBox : GameBoxes) {
                    pnl_GameBox.setBackground(Color.GRAY);
                }

                //Set Player Turn
                playerOneTurn = true;
                lbl_PlayerTurn.setText("Player 1's Turn!");

                //Activate Game
                gameOver = false;
            }
        });
    }

    private void checkForWin() {
        // Horizontal
        for (int c = 0; c < 3; c++) {
            if (gameBoard[0][c] == gameBoard[1][c] && gameBoard[0][c] == gameBoard[2][c] && gameBoard[0][c] != 0) {
                lbl_PlayerTurn.setText("Player " + gameBoard[0][c].toString() + " Wins!");
                gameOver = true;
            }
        }

        // Vertical
        for (int r = 0; r < 3; r++) {
            if (gameBoard[r][0] == gameBoard[r][1] && gameBoard[r][0] == gameBoard[r][2] && gameBoard[r][0] != 0) {
                lbl_PlayerTurn.setText("Player " + gameBoard[r][0].toString() + " Wins!");
                gameOver = true;
            }
        }

        // Diagonal
        if (gameBoard[0][0] == gameBoard[1][1] && gameBoard[0][0] == gameBoard[2][2] && gameBoard[0][0] != 0) {
            lbl_PlayerTurn.setText("Player " + gameBoard[0][0].toString() + " Wins!");
            gameOver = true;
        }
        if (gameBoard[2][0] == gameBoard[1][1] && gameBoard[2][0] == gameBoard[0][2] && gameBoard[2][0] != 0) {
            lbl_PlayerTurn.setText("Player " + gameBoard[2][0].toString() + " Wins!");
            gameOver = true;
        }
    }

    public static void main(String[] args) throws Exception {
        App app = new App();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                app.createGUI();
                app.createEvents();
            }
        });
    }

    private static GridBagConstraints c = new GridBagConstraints();
    private static SpringLayout s = new SpringLayout();

    private JFrame frame;
    private JPanel pnl_MainPanel;
    private JPanel pnl_GamePanel;
    private JPanel pnl_Box00;
    private JPanel pnl_Box10;
    private JPanel pnl_Box20;
    private JPanel pnl_Box01;
    private JPanel pnl_Box11;
    private JPanel pnl_Box21;
    private JPanel pnl_Box02;
    private JPanel pnl_Box12;
    private JPanel pnl_Box22;
    private JPanel pnl_ControlPanel;
    private JLabel lbl_PlayerTurn;
    private JButton btn_ResetButton;

    private Integer[][] gameBoard = { { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 } };
    private Boolean playerOneTurn = true;
    private Boolean gameOver = false;
}
