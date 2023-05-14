import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class MovingTextFrame extends JFrame implements Runnable {
    private String message = "Moving Text";
    private int x = 300, y = 100;
    private int dx = 1, dy = 1;
    private Random rand = new Random();
    private Font[] fonts = new Font[] {
        new Font("Serif", Font.BOLD, 20),
        new Font("SansSerif", Font.BOLD, 20),
        new Font("Monospaced", Font.BOLD, 20),
        new Font("Dialog", Font.BOLD, 20),
        new Font("DialogInput", Font.BOLD, 20),
        new Font("Serif", Font.ITALIC, 20)
    };

    public MovingTextFrame() {
        setTitle("Moving Text Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1000, 300));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void run() {
        while (true) {
            if (x < 0 || x + getFontMetrics(getFont()).stringWidth(message) > getWidth()) {
                dx *= -1;
                changeFont();
            }
            if (y < 50 || y + getFontMetrics(getFont()).getHeight() > getHeight()) {
                dy *= -1;
                changeFont();
            }
            x += dx;
            y += dy;
            repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void changeFont() {
        int index = rand.nextInt(fonts.length);
        setFont(fonts[index]);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            if (rand.nextBoolean()) {
                c = Character.toUpperCase(c);
            } else {
                c = Character.toLowerCase(c);
            }
            sb.append(c);
        }
        message = sb.toString();
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.drawString(message, x, y);
    }

    public static void main(String[] args) {
        MovingTextFrame frame = new MovingTextFrame();
        new Thread(frame).start();
    }
}