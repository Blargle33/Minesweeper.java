import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Memory extends JFrame implements ActionListener {
    int size;
    int indx;
    int guessed = 0;
    int clickCheck = 0;
    boolean reset = false;
    Object lastClick;
    Object currentClick;
    Card lastCard;
    Card currentCard;

    public Memory(){
        super("Memory");

        try{
            size = Integer.parseInt(JOptionPane.showInputDialog("What size grid would you like?"));
        }
        catch (NumberFormatException e){
            size = 3;
        }
        if (size > 10 || size < 1){
            JOptionPane.showMessageDialog(null,"You Can't Do That");
            size = 4;
        }
        JPanel panel = new JPanel(new GridLayout(size, size));
        Card[] card = new Card[size * size];

        for (int x = 0; x < size; x++){
            for (int y = 0; y < size; y++){
                int cardPos = x * size + y;
                card[cardPos] = new Card();
                card[cardPos].addActionListener(this);
                panel.add(card[cardPos]);
                card[cardPos].setEnabled(false);
            }
        }

        for (int i = 1 ; i <= size * size / 2 ; i++)
        {
            for (int j = 0 ; j < 2 ; j++)
            {
                do
                {
                    indx = (int) (Math.random () * size * size);
                }
                while (card [indx].getValue () > 0);
                card [indx].setValue (i);
                card[indx].setEnabled(true);
            }
        }

        this.setContentPane(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(150,150,size*50,size*50);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (reset){
            lastCard.setText("");
            currentCard.setText("");

            lastCard.setBackground(null);
            currentCard.setBackground(null);

            currentClick = null;

            reset = false;
        }
        lastClick = currentClick;
        currentClick = e.getSource();

        currentCard = (Card) currentClick;
        lastCard = (Card) lastClick;

        currentCard.setText(String.valueOf(currentCard.getValue()));

        if (lastClick != currentClick){
            clickCheck += 1;
            if (clickCheck == 2){
                if (currentCard.getValue() == lastCard.getValue()){
                    currentCard.setBackground(Color.GREEN);
                    lastCard.setBackground(Color.GREEN);

                    currentCard.setEnabled(false);
                    lastCard.setEnabled(false);

                    guessed += 1;

                    reset = false;
                }
                else{
                    currentCard.setBackground(Color.RED);
                    lastCard.setBackground(Color.RED);

                    reset = true;
                }
                clickCheck = 0;
            }
        }
        if (guessed == size*size/2){
            System.exit (0);
        }
    }
    public static void main(String[] args){
        new Memory();
    }
}
