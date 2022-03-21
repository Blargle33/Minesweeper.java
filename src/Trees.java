import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Trees extends JFrame implements ActionListener {
    private final JTextField numText;
    private final JTextField angleText;
    private int check = 0;
    int R, G, B = 255;

    public Trees() {
        super("Trees");
        JLabel numLabel = new JLabel("How Many Iterations Do You Want?");
        JLabel angleLabel = new JLabel("What Angle Would You Like?");
        JPanel panel = new JPanel(new GridLayout(2, 2));
        JPanel bg = new JPanel(new BorderLayout());

        this.setBounds(100, 100, 500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        numText = new JTextField(10);
        numText.addActionListener(this);
        numText.setText("");

        angleText = new JTextField(10);
        angleText.addActionListener(this);
        angleText.setText("");

        panel.add(numLabel);
        panel.add(numText);
        panel.add(angleLabel);
        panel.add(angleText);

        bg.add(panel, BorderLayout.NORTH);
        bg.setBackground(Color.black);

        this.add(bg);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Trees();
    }

    public void branch(Graphics g, int x, int y, double length, int angle, int angleChange, int num){
        int x2, y2;

        x2 = (int) (x - length * (Math.sin(angle*(Math.PI/180))));
        y2 = (int) (y - length * (Math.cos(angle*(Math.PI/180))));
        if (num != 0){
            R = (int)(Math.random()*255);
            G = (int)(Math.random()*255);
            B = (int)(Math.random()*255);
            Color colour = new Color(R,G,B);
            g.setColor(colour);
            g.drawLine(x2,y2,x,y);
            branch(g, x2, y2, length*.8, angle+angleChange, angleChange, num-1);
            branch(g, x2, y2, length*.8, angle-angleChange, angleChange, num-1);
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        int n;
        int a;
        try {
            n = Integer.parseInt(numText.getText());
        }
        catch(NumberFormatException e){
            n = 0;

        }
        try{
            a = Integer.parseInt(angleText.getText());
        }
        catch (NumberFormatException e){
            a = 0;
        }
        System.out.println(n);
        if (a < 0) {
            branch(g, getWidth() / 2, getHeight(), getHeight() / 5d, 0, a, n);
        }
        else if (a > 0 && check > 0){
            JOptionPane.showMessageDialog(null, "Please Input An Angle Greater Than 0");
        }
        check++;
    }

    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}