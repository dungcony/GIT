import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class draw extends JFrame{

    private JPanel panel = new JPanel();
    private JPanel controPanel; //chứa các điều khiển
    private DrawPanel drawPanel;//là class con của JPan nơi các hình sẽ được vẽ 
    private JComboBox<String> shapeCombo; // là 1 combo box cho phép chọn loại hình vẽ 
    private JComboBox<String> fillCombo; //cho combo cho chọn rỗng hay đặc
    private JButton colorButton; //nút để chọn màu
    private Color selectedColor = Color.BLACK; //biến lưu màu đã chọn mặc định là đen
    private JTextField widthField, heightField;//nhập kích thước 

    public draw(){ // thiết lập giao diên và các controller khi chạy 
        setTitle("Draw");
        setSize(800,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        controPanel = new JPanel();
        controPanel.setLayout(new FlowLayout()); // sắp xếp các thành phần từ trái qua phải     
        controPanel.setBackground(Color.BLACK); // đặt màu nền cho controPanel

        //tạo combo Box chọn hình
        shapeCombo = new JComboBox<>(new String[]{"vuong","tron","chu nhat","da giac"});
        controPanel.add(new JLabel("Chọn hình"));
        controPanel.add(shapeCombo);

        //tạo combo Box chọn đặc/rỗng
        fillCombo = new JComboBox<>(new String[]{"yes","no"});
        controPanel.add(new JLabel("rỗng hay không"));
        controPanel.add(fillCombo);

        widthField = new JTextField("200", 5); // Ô nhập chiều rộng
        controPanel.add(widthField);
        
        heightField = new JTextField("200", 5); // Ô nhập chiều cao
        controPanel.add(heightField);

        //tạo nút chọn màu
        colorButton = new JButton("chon mau");
        colorButton.addActionListener(e ->{
            Color color = JColorChooser.showDialog(null, "chon mau", selectedColor);

            if(color != null){
                selectedColor = color;
            }
        });
        controPanel.add(colorButton);


        //thiết lập nút vẽ
        JButton drawButton = new JButton("ve hinh");
        drawButton.addActionListener(new DrawAction()); //đăng ký sự kiện khi nhấn nút sẽ thực hiện lớp drawaction
        controPanel.add(drawButton);

        //thiết lập hình
        drawPanel = new DrawPanel();
        add(controPanel,BorderLayout.NORTH);//thêm control vào phía trên cùng
        add(drawPanel,BorderLayout.CENTER);//thêm draw vào trung tâm 


        setVisible(true);
    }

    private class DrawAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            String shape = (String) shapeCombo.getSelectedItem();//lấy loại hình đã chọn 
            String fill  = (String) fillCombo.getSelectedItem();

            drawPanel.setShape(shape, fill, selectedColor); //cập nhật loại hình và kiểm tra màu sắc
            drawPanel.repaint();
        }
    }

    private class DrawPanel extends JPanel{
        private String shape = "hinh vuong";
        private String fill  = "rong";
        private Color color  = Color.BLACK;
        private int width = 200;  // Kích thước mặc định
        private int height = 200; // Kích thước mặc định

        public void setShape(String shape ,String fill,Color color){
            this.shape = shape;
            this.fill = fill;
            this.color = color;
        }

        @Override
        protected void paintComponent(Graphics g){ // gọi mỗi khi vùng được vẽ lại
            super.paintComponent(g); // gọi phương thức lớp cha để làm sạch vùng vẽ
            Graphics2D g2d = (Graphics2D) g;// chuyển đối tượng thành 2d
            g2d.setColor(color);

            int x = 100;
            int y = 100;
            
            switch (shape) {
                case "vuong":
                    if (fill.equals("no")) {
                        g2d.fillRect(x, y, width, width); // Sử dụng chiều rộng để vẽ hình vuông
                    } else {
                        g2d.drawRect(x, y, width, width); // Vẽ hình vuông rỗng
                    }
                    break;

                case "chu nhat":
                    if (fill.equals("no")) {
                        g2d.fillRect(x, y, width, height); // Vẽ hình chữ nhật đặc
                    } else {
                        g2d.drawRect(x, y, width, height); // Vẽ hình chữ nhật rỗng
                    }
                    break;

                case "tron":
                    if (fill.equals("no")) {
                        g2d.fillOval(x, y, width, width); // Vẽ hình tròn đặc
                    } else {
                        g2d.drawOval(x, y, width, width); // Vẽ hình tròn rỗng
                    }
                    break;
                
                case "da giac":
                drawPolygon(g2d, x + 200, y + 100, 5, width); // Vẽ đa giác
                break;
                }
        }

        private void drawPolygon(Graphics2D g2d, int x, int y, int sides, int size) {
            int[] xPoints = new int[sides];
            int[] yPoints = new int[sides];

            for (int i = 0; i < sides; i++) {
                double angle = 2 * Math.PI * i / sides; // Tính góc cho các điểm
                xPoints[i] = (int) (x + size * Math.cos(angle)); // Tính tọa độ x
                yPoints[i] = (int) (y + size * Math.sin(angle)); // Tính tọa độ y
            }

            if (fill.equals("no")) {
                g2d.fillPolygon(xPoints, yPoints, sides); // Vẽ đa giác đặc
            } else {
                g2d.drawPolygon(xPoints, yPoints, sides); // Vẽ đa giác rỗng
            }
        }


    }   



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new draw());
    }

}
