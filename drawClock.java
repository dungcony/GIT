import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class drawClock extends JFrame {
    private ClockPanel clockpanel;
    //private ClockString clockString;

    public drawClock() {

        setTitle("Clock");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        clockpanel = new ClockPanel();
        add(clockpanel);
        setVisible(true);
    }


    public class ClockPanel extends JPanel {
        private int x = 400;
        private int y = 250;    
        private int second = 0;
        private int minute = 0;
        private int hour = 0;

        public ClockPanel() {
            setBackground(Color.BLACK);

            Timer timer = new Timer(true);
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    second = (second + 1) % 60;
                    if (second == 0) {
                        minute = (minute + 1) % 60;
                        if (minute == 0) {
                            hour = (hour + 1) % 12;
                        }
                    }
                    repaint();
                }
            }, 0, 1000);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Vẽ mặt đồng hồ
            g.setColor(Color.PINK);
            g.fillOval(x - 150, y - 150, 300, 300);

            g.fillRect(250, 600, 50, 50);
            g.fillRect(250 + 75 + 50, 600, 50, 50);
            g.fillRect(250 + 75 + 50 + 50 + 75, 600, 50, 50);

            g.setColor(Color.RED);
            g.fillOval(300 + 37,610,10,10);
            g.fillOval(300 + 37,630,10,10);

            g.fillOval(425 + 37,610,10,10);
            g.fillOval(425 + 37,630,10,10);

            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.setColor(Color.CYAN);
            g.drawString(String.valueOf(hour),255,630);
            g.drawString(String.valueOf(minute),380,630);
            g.drawString(String.valueOf(second),505,630);

            g.setColor(Color.BLACK);
            int idx = 130;
            for(int i = 1;i<=12;i++){
                if(i != 3 && i != 6 && i != 9 && i != 12) continue;
                double R = Math.toRadians(30 * i);
                int X = x + (int) (Math.sin(R) * idx);
                int Y = y - (int) (Math.cos(R) * idx);
                g.drawString(String.valueOf(i), X-5, Y+5);
            }

            // Vẽ kim giây
            g.setColor(Color.RED);
            double angleSecond = Math.toRadians(6 * second);
            int secondX = x + (int) (140 * Math.sin(angleSecond));
            int secondY = y - (int) (140 * Math.cos(angleSecond));
            g.drawLine(x, y, secondX, secondY);

            // Vẽ kim phút
            g.setColor(Color.BLUE);
            double angleMinute = Math.toRadians(6 * minute);
            int minuteX = x + (int) (100 * Math.sin(angleMinute));
            int minuteY = y - (int) (100 * Math.cos(angleMinute));
            g.drawLine(x, y, minuteX, minuteY);

            // Vẽ kim giờ
            g.setColor(Color.GREEN);
            double angleHour = Math.toRadians(30 * hour + minute * 0.5); 
            int hourX = x + (int) (70 * Math.sin(angleHour));
            int hourY = y - (int) (70 * Math.cos(angleHour));
            g.drawLine(x, y, hourX, hourY);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new drawClock());
    }
}
