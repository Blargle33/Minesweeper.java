import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class Score extends JFrame {
    JLabel scoreLabel = new JLabel();
    JPanel panel = new JPanel();
    JTable scoreTable;
    String[][] tableData = {{"0", ""},{"0", ""},{"0", ""},{"0", ""},{"0", ""},{"0", ""},{"0", ""},{"0", ""},{"0", ""},{"0", ""}};


    public Score(){
        String[] columnNames = {"Score", "Name"};
        scoreTable = new JTable(tableData, columnNames);
        DefaultTableCellRenderer centerRenderer=new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        scoreTable.setDefaultRenderer(Object.class, centerRenderer);
        scoreTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        panel.add(scoreLabel);
        panel.add(scoreTable);
        this.setContentPane(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(400,200,200 ,200);
        this.setVisible(true);
    }

    public Score(int score, String name)
    {
        this();
        addScore(score,name);
    }

    public void addScore(int newScore, String name){
        for (int i=0; i<10; i++)
        {
            if (newScore > Integer.parseInt(tableData[i][0]))
            {
                if (name == null) {
                    name = JOptionPane.showInputDialog("What is your name?");
                }
                for (int j = 9; j > i; j--) {
                    tableData[j][0] = tableData[j-1][0];
                    tableData[j][1] = tableData[j-1][1];
                }
                tableData[i][0]=Integer.toString(newScore);
                tableData[i][1]=name;
                repaint();
                break;
            }
        }
    }
}

