import javax.swing.*;

public class Card extends JButton{
    int value = -1;
    public void setValue(int x){
        value = x;
    }
    public int getValue(){
        return value;
    }
}
