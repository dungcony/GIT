import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.ArrayList;

public class ImageGallery extends JFrame {
    private JPanel imagePanel; //JPanel chứa và hiển thị ảnh
    private ArrayList<BufferedImage> images; //danh sách lưu trữ ảnh được chọn kiểu Buffered
    private int width = 100;
    private int height = 100;

    public ImageGallery() {
        setTitle("Image Gallery");  
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        images = new ArrayList<>();// danh sách ảnh
        imagePanel = new JPanel();//jpanel chứa ảnh
        imagePanel.setLayout(new GridLayout(0,3));
        JScrollPane scrollPane = new JScrollPane(imagePanel); //Đặt imagePanel vào trong một JScrollPane để có thể cuộn khi số lượng ảnh lớn hơn không gian hiển thị.
        add(scrollPane, BorderLayout.CENTER);

        JButton loadButton = new JButton("Chọn Ảnh"); // nút chọn ảnh
        loadButton.addActionListener(new LoadImageAction()); //chọn ảnh 
        add(loadButton, BorderLayout.SOUTH);

        JButton size = new JButton("size");
        size.addActionListener(new Size());
        add(size, BorderLayout.NORTH);

        setVisible(true);
    }

    private class LoadImageAction implements ActionListener { // xử lý sự kiện chọn ảnh
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();// tạo 1 hộp thoại chon file 
            fileChooser.setMultiSelectionEnabled(true);// cho phép chọn nhiều file
            int returnValue = fileChooser.showOpenDialog(null);// hiển thị hộp thaoji và lưu giá trị trả về
            if (returnValue == JFileChooser.APPROVE_OPTION) { //kiểm tra có chọn không
                File[] selectedFiles = fileChooser.getSelectedFiles(); //các file đã chọn
                for (File file : selectedFiles) {
                    try {
                        BufferedImage img = ImageIO.read(file);//xem ảnh
                        images.add(img);// thêm vào hộp ảnh
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                displayImages();// phương thức hiển thị ảnh
            }
        }
    }

    private class Size implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String w = JOptionPane.showInputDialog("nhap chieu rong");
            String h = JOptionPane.showInputDialog("nhap chieu cao");

            try{
                width = Integer.parseInt(w);
                height = Integer.parseInt(h);
                displayImages();//cập nhật lại ảnh 
            } catch(NumberFormatException ex){

            }
        }
    }

    private void displayImages() {
        imagePanel.removeAll(); // xóa toàn bộ ảnh trước khi thêm mới
        for (BufferedImage img : images) { // lặp qua từng ảnh trong danh sách
            // Thay đổi kích thước ảnh
            Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH); // điều chỉnh kích thước (200x200)
            JLabel label = new JLabel(new ImageIcon(scaledImg)); // tạo JLabel với ảnh đã thay đổi kích thước
            imagePanel.add(label); // thêm JLabel vào panel
        }
        imagePanel.revalidate(); // cập nhật lại bố cục của panel
        imagePanel.repaint(); // vẽ lại panel
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ImageGallery());
    }
}
