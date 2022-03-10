import javax.swing.*;

public class Cell extends JButton{
    int value = -1;
    int row;
    int column;
    int cycleValue = 0;
    String[] text = new String[]{"", "B", "?"};

    public String cycle(){
        cycleValue += 1;
        if (cycleValue == 3){
            cycleValue = 0;
        }
        return text[cycleValue];
    }

    public Cell(int r, int c) {
        row = r;
        column = c;
    }

    public int getColumn(){return column;}
    public int getRow(){ return row; }
    public void setValue(int x){
        value = x;
    }
    public int getValue(){
        return value;
    }
}
