import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Curve extends JFrame implements ActionListener {
    private final JTextField text;

    public Curve() {
        super("Koch Curve");
        this.setBounds(100, 100, 500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        text = new JTextField(10);
        text.addActionListener(this);
        text.setText("");
        JPanel panel = new JPanel();
        panel.setBackground(Color.white);
        panel.add(text);
        this.add(panel);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Curve();
    }

    public void koch (Graphics g, int x1, int y1, int x5, int y5, int n)
    {
        int x2, y2, x3, y3, x4, y4;
        double d, a, h;
        g.setColor (Color.blue);
        if (n == 0 || (x5 - x1) == 0) {
            g.drawLine (x1, getHeight() - y1, x5, getHeight() - y5); //Draw the line
            return;
        }
        d = Math.sqrt ((x5-x1) * (x5-x1) + (y5-y1) * (y5-y1)) / 3; // 1/3 of the length
        a = Math.atan2 ((y5-y1), (x5-x1)); // angle of the line
        h = 2 * d * Math.cos (30 * Math.PI / 180); // distance to peak

        x2 = x1 + (int) (d * Math.cos (a));
        y2 = y1 + (int) (d * Math.sin (a));
        x3 = x1 + (int) (h * Math.cos ((a + 30 * Math.PI / 180))); // coordinates of peak
        y3 = y1 + (int) (h * Math.sin ((a + 30 * Math.PI / 180)));
        x4 = x1 + (int) (2 * d * Math.cos (a));
        y4 = y1 + (int) (2 * d * Math.sin (a));
        koch (g, x1, y1, x2, y2, n - 1); // Recursive calls to replace line with new pattern
        koch (g, x2, y2, x3, y3, n - 1);
        koch (g, x3, y3, x4, y4, n - 1);
        koch (g, x4, y4, x5, y5, n - 1);
    }

    public void paint(Graphics g) {
        super.paint(g);
        int n;
        try {
            n = Integer.parseInt(text.getText());
        }
        catch(NumberFormatException e){
            n = 0;
        }
        koch(g, 400, 120, 100, 120, n);
        koch(g, 100, 120, 250, 380, n);
        koch(g, 250, 380, 400, 120, n);
    }

    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}