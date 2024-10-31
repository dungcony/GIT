import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorChooserDemo extends JFrame {
    private JPanel panel;

    public ColorChooserDemo() {
        setTitle("Hộp chọn màu");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setBackground(Color.WHITE); // Màu nền ban đầu

        JButton button = new JButton("Chọn màu"); //tạo 1 nút với văn bản chọn màu
        button.addActionListener(new ActionListener() {// gắn hành động vào nút, khi nhấn nút thì sự kiện button sẽ được thực thi
            @Override
            public void actionPerformed(ActionEvent e) {
                Color selectedColor = JColorChooser.showDialog(null, "Chọn màu", panel.getBackground());
                if (selectedColor != null) { // nghĩa là người dùng đã chọn màu 
                    panel.setBackground(selectedColor);
                }
            }
        });

        panel.add(button);// thêm nút vào panel
        add(panel); // thêm panel vào JFrame để hiển thị

        setVisible(true); //đặt JFrame thành có thể nhìn thấy để người dùng nhìn thấy giao diện
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ColorChooserDemo());
    }
}
