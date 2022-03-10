import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class Minesweeper extends JFrame implements MouseListener, ActionListener {
    private boolean game = true;
    private Score highScorePanel;
    private int size;
    private int score;
    private int bombs;
    private int closedCells;
    private int seconds;
    private int  flags;
    public Cell[][] grid;
    private Timer timer;
    JButton reset = new JButton(":)");
    JLabel bombLabel = new JLabel("", SwingConstants.CENTER);
    JLabel timerLabel = new JLabel("0", SwingConstants.CENTER);

    public Minesweeper(){
        try{
            size = Integer.parseInt(JOptionPane.showInputDialog("What size grid would you like?"));
        }
        catch (NumberFormatException e){
            size = 6;
        }
        if (size > 15 || size < 5){
            JOptionPane.showMessageDialog(null,"You Can't Do That");
            size = 6;
        }
        closedCells = size*size;

        JPanel panel = new JPanel(new GridLayout(1,3));
        reset.addActionListener(this);
        panel.add(timerLabel);
        reset.setSize(new Dimension(size/3, size/3));
        panel.add(reset);
        panel.add(bombLabel);

        JPanel bg = new JPanel(new BorderLayout());

        bg.add(panel, BorderLayout.NORTH);

        panel = new JPanel(new GridLayout(size, size));

        grid = new Cell [size][size];

        for (int r = 0; r < size; r++){
            for (int c = 0; c < size; c++){
                grid [r][c] = new Cell (r, c);
                grid [r][c].addMouseListener( this);
                panel.add(grid [r][c]);
            }
        }
        bg.add(panel, BorderLayout.CENTER);
        newGame();
        this.setContentPane(bg);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(150,150,size*50,size*50);
        this.setVisible(true);
    }

    public void newGame(){
        game = true;
        seconds = 0;
        score = 0;
        closedCells = size*size;
        bombs = 0;
        timer = new Timer(1000,this);
        timer.start();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j].setValue(-1);
                grid[i][j].setEnabled(true);
                grid[i][j].setText("");
            }
        }
        do{
            Random random = new Random();
            int x = random.nextInt(size);
            int y = random.nextInt(size);
            if (grid[x][y].getValue() < 0) {
                grid[x][y].setValue(1);
                bombs++;
            }
        }
        while (bombs <= size * size/6);
        bombLabel.setText("0");
        flags = bombs;
    }

    public int count(int row, int column){
        int counter = 0;
        for (int i = row - 1; i <= row + 1; i++){
            for (int j = column - 1; j <= column + 1; j++){
                try {
                    if (i >= 0 && i < size && j >= 0 && j < size) {
                        if (grid[i][j].getValue() > 0) {
                            counter++;
                            }
                        }
                    }
                catch(ArrayIndexOutOfBoundsException ignore){}
            }
        }
        return counter;
    }


    public void reveal(int row, int column){
        closedCells--;
        score++;
        grid[row][column].setEnabled(false);

        if (count(row,column) == 0) {
            for (int i = row - 1; i <= row + 1; i++) {
                for (int j = column - 1; j <= column + 1; j++) {
                    try {
                        if (grid[i][j].getValue() != 1 && grid[i][j].isEnabled() && (i != row || column != j)) {
                            reveal(i, j);
                        }
                    }
                    catch (ArrayIndexOutOfBoundsException ignore){}
                }
            }
        }
        if (count(row,column) != 0) {
            grid[row][column].setText(String.valueOf(count(row, column)));
        }
    }

    public void mousePressed(MouseEvent e) {
        System.out.println(bombs + " " + closedCells);
        Cell c = (Cell) (e.getSource() );
        if (e.getButton()==1)
        {
            if (c.isEnabled()){
                //System.out.println(closedCells);
                int status = c.getValue();
                int row = c.getRow();
                int column = c.getColumn();
                if (status > 0){
                    game = false;
                    String[] options = {"Play Again!", "No Thanks :("};
                    JOptionPane.showMessageDialog(null, "You Lost :(", "", JOptionPane.INFORMATION_MESSAGE);

                    if (highScorePanel == null)
                    {
                        highScorePanel = new Score(score,null);
                    } else
                    {
                        highScorePanel.addScore(score,null);
                    }

                    int x = JOptionPane.showOptionDialog(null,"Would You Like To Play Again?", "You Lost", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                    if (x == 0){
                        newGame();
                    }
                    else if (x == 1){
                        System.exit(0);
                    }
                }
                else if (status < 0) {
                    reveal(row, column);
                }
            }
        }
        else
        {
            if (c.isEnabled()) {
                if (c.cycleValue == 0){
                    flags--;
                }
                else if (c.cycleValue == 1){
                    flags++;
                }
                c.setText(c.cycle());
                bombLabel.setText(String.valueOf(flags));
            }
        }

        if (bombs == closedCells){
            JOptionPane.showMessageDialog(null, "You Won!", "", JOptionPane.INFORMATION_MESSAGE);
            String[] options = {"Play Again!", "No Thanks :("};
            int x = JOptionPane.showOptionDialog(null,"Would You Like To Play Again?", "You Won", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            if (x == 0){
                newGame();
            }
            else if (x == 1){
                System.exit(0);
            }
        }
    }

    public static void main(String[] args){
        new Minesweeper();
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer && game) {
            seconds++;
            timerLabel.setText(String.valueOf(seconds));

        } else if (e.getSource() == reset) {
            newGame();
        }
    }
}
