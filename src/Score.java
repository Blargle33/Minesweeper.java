import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Score extends JDialog {
    JLabel scoreLabel = new JLabel();
    JPanel panel = new JPanel();
    JTable scoreTable;
    String[][] tableData = {{"0", ""},{"0", ""},{"0", ""},{"0", ""},{"0", ""},{"0", ""},{"0", ""},{"0", ""},{"0", ""},{"0", ""}};


    public Score(int score, Frame owner){
        super(owner, "Scores", true);
        String[] columnNames = {"Score", "Name"};
        scoreTable = new JTable(tableData, columnNames);
        DefaultTableCellRenderer centerRenderer=new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        scoreTable.setDefaultRenderer(Object.class, centerRenderer);
        scoreTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        panel.add(scoreLabel);
        panel.add(scoreTable);
        this.setContentPane(panel);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setBounds(400,200,200 ,200);
        getScore();
        addScore(score, null);
        this.setVisible(true);
    }


    public void getScore(){
        try {
            BufferedReader in = new BufferedReader(new FileReader("scores.txt"));
            for (int i = 0; i < 10; i++) {
                tableData[i][0] = String.valueOf(in.readLine());
                tableData[i][1] = in.readLine();
            }
            in.close();
        } catch (IOException ignored) {}
    }

    public void saveScores(){
        try {
        PrintWriter scores = new PrintWriter("scores.txt");
        for (int i = 0; i < 10; i++) {
            scores.println(tableData[i][0]);
            scores.println(tableData[i][1]);
        }
        scores.close();
        } catch (IOException ignored) {}
    }

    /**
     * adds the score
     *
     * @param newScore the score to be added
     * @param name the name
     */
    public void addScore(int newScore, String name){
        for (int i=0; i<10; i++)
        {
            if (newScore > Integer.parseInt(tableData[i][0]))
            {
                if (name == null) {
                    name = JOptionPane.showInputDialog("What is your name?");
                }
                for (int j = 9; j > i; j--) {
                    tableData[j][0] = tableData[j-1][0]; //scores
                    tableData[j][1] = tableData[j-1][1]; //names
                }
                tableData[i][0]=Integer.toString(newScore);
                tableData[i][1]=name;
                saveScores();
                repaint();
                break;
            }
        }
    }
}

