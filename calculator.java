import java.awt.*;
import java.awt.event.*;
import java.util.StringJoiner;

import javax.swing.*;

public class calculator extends JFrame{

    private draw d;

    public calculator(){
        setTitle("calculator");
        setSize(300,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        d = new draw();
        add(d);

        setVisible(true);

    }

    public class draw extends JPanel{

        private JTextField display;
        private double firstOperand = 0; // lưu số nhập vào cho đến khi nhập operator
        private double secondOperand = -1;
        private String operator = "";
        private boolean check = true;

        public draw(){
            setLayout(new BorderLayout());

            display = new JTextField(); // khởi tạo trường văn bản lưu kết quả
            display.setEditable(false); // không cho chỉnh sửa
            display.setFont(new Font("Arial", Font.BOLD, 50)); // Tăng kích thước font

            add(display, BorderLayout.NORTH); // đặt lên trên đầu

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(5, 5)); //tạo JP chứa các nút gồm 4 cột 4 dòng

            String[] buttons = {
                "1", "2", "3", 
                "4", "5", "6",
                "7", "8", "9",
                "0", "C", "CE",
                "+", "-", "*",
                "/","^","sqrt","="
            };

            for(String i : buttons){
                JButton button = new JButton(i);
                button.addActionListener(new ButtonClickListener());
                buttonPanel.add(button); // thêm mỗi nút vào danh sách nút
            }

            add(buttonPanel, BorderLayout.CENTER);
            
        }

        private class ButtonClickListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {

                String command = e.getActionCommand();//lấy lệnh của nút được bấm

                if(command.charAt(0) >= '0' && command.charAt(0) <= '9'){
                    display.setText(display.getText() + command);
                    if(check == false){
                        display.setText(command); // Xóa màn hình
                        firstOperand = 0; // Đặt lại kết quả
                        secondOperand = -1;
                        operator = ""; // Đặt lại toán tử
                    }
                    check = true;
                } else if(command.equals("C")){
                    String tmp = display.getText();
                    if(!tmp.isEmpty()){
                        display.setText(tmp.substring(0,tmp.length() - 1));
                    }

                } else if(command.equals("sqrt")){
                    firstOperand = Double.parseDouble(display.getText());
                    operator = command;
                    firstOperand = Math.sqrt(firstOperand);
                    display.setText(String.valueOf(firstOperand));
                } else if (command.equals("=")) {
                    check = false;
                    if (secondOperand == -1) secondOperand = Double.parseDouble(display.getText());
                    
                    switch (operator) {
                        case "+":
                            firstOperand = firstOperand + secondOperand;
                            break;
                        case "-":
                            firstOperand = firstOperand - secondOperand;
                            break;
                        case "*":
                            firstOperand = firstOperand * secondOperand;
                            break;
                        case "/":
                            if (secondOperand != 0) {
                                firstOperand = firstOperand / secondOperand;
                            } else {
                                display.setText("Error");
                                return;
                            }
                            break;
                        case "^":
                            firstOperand = Math.pow(firstOperand, secondOperand);
                            break;
                        case "sqrt":
                            firstOperand = Math.sqrt(firstOperand);
                            break;
                        case "":
                            firstOperand = Double.parseDouble(display.getText());
                    }
                    display.setText(String.valueOf(firstOperand));
                } else if (command.equals("CE")){
                    display.setText(""); // Xóa màn hình
                    firstOperand = 0; // Đặt lại kết quả
                    secondOperand = -1;
                    operator = ""; // Đặt lại toán tử
                } else { 
                    firstOperand = Double.parseDouble(display.getText());
                    operator = command;
                    display.setText("");
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new calculator());
    }

} 