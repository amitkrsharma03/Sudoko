package Sudoko;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokoGUI extends JFrame {
    private JTextField[][] board;
    private static final int GRID_SIZE = 9; // Sudoku 9x9
    private static int secondsElapsed = 0;
    private static JLabel timeLabel;
    private static Timer timer;
    private static JButton startButton;
    private static JButton submitButton;
    private JPanel timerPanel;
    private int[][] initialBoard = {
            { 0, 7, 9, 8, 0, 2, 0, 6, 3 },
            { 6, 0, 0, 9, 0, 0, 0, 1, 0 },
            { 8, 0, 3, 0, 7, 0, 0, 0, 2 },
            { 0, 9, 0, 0, 0, 0, 3, 7, 1 },
            { 0, 6, 8, 7, 0, 0, 0, 9, 0 },
            { 0, 3, 1, 0, 2, 0, 5, 8, 0 },
            { 2, 8, 6, 5, 0, 0, 1, 3, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 9, 0, 4, 3, 0, 0, 8, 2, 7 }
    };

    public SudokoGUI() {
        super("Sudoku");

        // Set full-screen mode
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);

        board = new JTextField[GRID_SIZE][GRID_SIZE];

        addGuiComponents();
    }

    private void addGuiComponents() {
        int screenWidth = getToolkit().getScreenSize().width;
        int screenHeight = getToolkit().getScreenSize().height;

        // Title Bar (Reduced Height)
        JLabel barLabel = new JLabel("Sudoku", SwingConstants.CENTER);
        barLabel.setOpaque(true);
        barLabel.setBackground(Common.Bar_Color);
        barLabel.setForeground(Color.BLACK);
        barLabel.setFont(new Font("Arial", Font.BOLD, 28));
        barLabel.setHorizontalAlignment(SwingConstants.CENTER);
        barLabel.setBounds(0, 0, screenWidth, 40); // Decreased height

        // Board Panel (Shifted Upwards)
        int boardSize = (int) (screenHeight * 0.70); // 70% of screen height
        int boardY = 50; // Adjusting the position to leave space at the bottom
        JPanel boardPanel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE));
        boardPanel.setBounds((screenWidth - boardSize) / 2, boardY, boardSize, boardSize);
        boardPanel.setBorder(BorderFactory.createLineBorder(Common.Grid_Color, 5));
        boardPanel.setBackground(Common.Board_Color);

        // 3x3 Box Colors
        Color[][] boxColors = {
                { Common.Box1_Color, Common.Box2_Color, Common.Box3_Color },
                { Common.Box4_Color, Common.Box5_Color, Common.Box6_Color },
                { Common.Box7_Color, Common.Box8_Color, Common.Box9_Color }
        };

        // Populate the board with buttons
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                board[i][j] = new JTextField();
                board[i][j].setFont(new Font("Dialog", Font.BOLD, 24));
                board[i][j].setFocusable(false);
                board[i][j].setEnabled(false); // Disable all initially (No values set)
                board[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                board[i][j].setBackground(boxColors[i / 3][j / 3]); // Set background color for 3x3 grids

                boardPanel.add(board[i][j]);

                // Grid Borders
                int top = (i % 3 == 0) ? 3 : 1;
                int left = (j % 3 == 0) ? 3 : 1;
                int bottom = (i == 8) ? 3 : 1;
                int right = (j == 8) ? 3 : 1;

                board[i][j].setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Common.Grid_Color));
            }
        }

        // Start Button (Centered)
        startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 24));
        int buttonWidth = 200;
        int buttonHeight = 30;
        startButton.setBounds((screenWidth - buttonWidth) / 2, boardY + boardSize + 20, buttonWidth, buttonHeight);
        startButton.addActionListener(e -> {
            startButton.setVisible(false); // Hide button on click
            startGame();
        });

        // Submit Button (Below Start Button)
        submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.BOLD, 18));
        submitButton.setBounds((screenWidth - buttonWidth) / 2, boardY + boardSize + 60, buttonWidth, buttonHeight);
        submitButton.setVisible(false); // Initially hidden
        submitButton.addActionListener(e -> submitGame());

        // Timer Panel (Bottom Left)
        timerPanel = new JPanel();
        timerPanel.setBounds(20, screenHeight - 70, 200, 50);
        timerPanel.setBackground(Color.DARK_GRAY);
        timerPanel.setLayout(new FlowLayout());
        timerPanel.setVisible(false); // Initially hidden

        timeLabel = new JLabel("00:00");
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setFont(new Font("Verdana", Font.BOLD, 18));

        timerPanel.add(timeLabel);

        // Add components to frame
        getContentPane().add(barLabel);
        getContentPane().add(boardPanel);
        getContentPane().add(startButton);
        getContentPane().add(submitButton);
        getContentPane().add(timerPanel);
    }

    private void startGame() {
        timerPanel.setVisible(true);
        submitButton.setVisible(true);
        secondsElapsed = 0;
        timer = new Timer(1000, e -> {
            secondsElapsed++;
            int minutes = secondsElapsed / 60;
            int seconds = secondsElapsed % 60;
            timeLabel.setText(String.format("%02d:%02d", minutes, seconds));
        });
        timer.start();

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (initialBoard[i][j] != 0) {
                    board[i][j].setText(String.valueOf(initialBoard[i][j]));
                    board[i][j].setEditable(false);
                    board[i][j].setEnabled(false); // Keep pre-filled cells disabled

                } else {
                    board[i][j].setText("");
                    board[i][j].setEditable(true);
                    board[i][j].setEnabled(true); // Ensure empty cells are enabled for input
                    board[i][j].setFocusable(true);
                    ((AbstractDocument) board[i][j].getDocument()).setDocumentFilter(new DocumentFilter() {
                        @Override
                        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                                throws BadLocationException {
                            if (isValidInput(string)) {
                                super.insertString(fb, offset, string, attr);
                            }
                        }

                        @Override
                        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                                throws BadLocationException {
                            if (isValidInput(text)) {
                                super.replace(fb, offset, length, text, attrs);
                            }
                        }

                        @Override
                        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
                            super.remove(fb, offset, length);
                        }

                        private boolean isValidInput(String text) {
                            if (text.isEmpty()) {
                                return true; // Allow empty input
                            }
                            try {
                                int value = Integer.parseInt(text);
                                return value >= 1 && value <= 9;
                            } catch (NumberFormatException e) {
                                return false;
                            }
                        }
                    });
                }
            }
        }
    }

    private void submitGame() {
        timer.stop();
        if (checkSudokuSolution()) {
            JOptionPane.showMessageDialog(this, "You Win!");
        } else {
            JOptionPane.showMessageDialog(this, "You Failed!");
        }
        resetGame();
    }

    private boolean checkSudokuSolution() {
        int[][] board = new int[GRID_SIZE][GRID_SIZE];
        // Populate the board with current values from your GUI components
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                String text = this.board[i][j].getText();
                if (text.isEmpty() || !text.matches("\\d+")) {
                    return false;
                }
                board[i][j] = text.isEmpty() ? 0 : Integer.parseInt(text);
            }
        }
        return isSolutionValid(board);
    }

    private boolean isSolutionValid(int[][] board) {
        boolean[][] rows = new boolean[9][10];
        boolean[][] cols = new boolean[9][10];
        boolean[][] grids = new boolean[9][10];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int num = board[i][j];
                if (num != 0) {
                    if (rows[i][num] || cols[j][num] || grids[(i / 3) * 3 + (j / 3)][num]) {
                        return false;
                    }
                    rows[i][num] = true;
                    cols[j][num] = true;
                    grids[(i / 3) * 3 + (j / 3)][num] = true;
                }
            }
        }
        return true;
    }

    static void solveSudoku(int[][] mat) {
        boolean[][] rows = new boolean[9][10];
        boolean[][] cols = new boolean[9][10];
        boolean[][] grids = new boolean[9][10];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int num = mat[i][j];
                if (num != 0) {
                    rows[i][num] = true;
                    cols[j][num] = true;
                    grids[(i / 3) * 3 + (j / 3)][num] = true;
                }
            }
        }
        solve(mat, rows, cols, grids);
    }

    static boolean isValid(int row, int col, int num, boolean[][] rows, boolean[][] cols, boolean[][] grids) {
        return !rows[row][num] && !cols[col][num] && !grids[(row / 3) * 3 + (col / 3)][num];
    }

    static boolean solve(int[][] mat, boolean[][] rows, boolean[][] cols, boolean[][] grids) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (mat[row][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (isValid(row, col, num, rows, cols, grids)) {
                            mat[row][col] = num;
                            rows[row][num] = true;
                            cols[col][num] = true;
                            grids[(row / 3) * 3 + (col / 3)][num] = true;

                            if (solve(mat, rows, cols, grids)) {
                                return true;
                            }

                            mat[row][col] = 0;
                            rows[row][num] = false;
                            cols[col][num] = false;
                            grids[(row / 3) * 3 + (col / 3)][num] = false;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private void resetGame() {
        timer.stop();
        secondsElapsed = 0;
        timeLabel.setText("00:00");
        startButton.setVisible(true);
        timerPanel.setVisible(false);
        submitButton.setVisible(false);
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                board[i][j].setText("");
                board[i][j].setEnabled(false);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SudokoGUI().setVisible(true));
    }
}
