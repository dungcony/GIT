import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ClassExerciseGUI extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton readButton, sortButton;

    public ClassExerciseGUI() {
        setTitle("Class Exercise GUI");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Tạo bảng và mô hình bảng
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Name");
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Tạo nút nhấn để đọc từ file
        readButton = new JButton("Read from File");
        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readFromFile("data.txt"); // Đường dẫn đến file dữ liệu
            }
        });

        // Tạo nút nhấn để sắp xếp
        sortButton = new JButton("Sort by Name");
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortTable();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(readButton);
        buttonPanel.add(sortButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void readFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            tableModel.setRowCount(0); // Xóa dữ liệu cũ
            while ((line = br.readLine()) != null) {
                String[] data = line.split(","); // Giả sử dữ liệu trong file được phân cách bằng dấu phẩy
                tableModel.addRow(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error reading file: " + e.getMessage());
        }
    }

    private void sortTable() {
        ArrayList<String[]> data = new ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String[] row = new String[tableModel.getColumnCount()];
            for (int j = 0; j < tableModel.getColumnCount(); j++) {
                row[j] = (String) tableModel.getValueAt(i, j);
            }
            data.add(row);
        }

        Collections.sort(data, new Comparator<String[]>() {
            @Override
            public int compare(String[] o1, String[] o2) {
                return o1[1].compareTo(o2[1]); // Sắp xếp theo cột Name
            }
        });

        tableModel.setRowCount(0); // Xóa dữ liệu cũ
        for (String[] row : data) {
            tableModel.addRow(row);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ClassExerciseGUI gui = new ClassExerciseGUI();
            gui.setVisible(true);
        });
    }
}
