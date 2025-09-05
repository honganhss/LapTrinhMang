import javax.swing.*;  // Thư viện giao diện Swing
import java.awt.*;      // Thư viện AWT cho layout và color
import java.awt.event.*; // Thư viện xử lý events (click, key press)
import java.io.*;       // Thư viện I/O cho đọc/ghi dữ liệu
import java.net.*;      // Thư viện network cho Socket connection

/**
 * CalculatorClient - Ứng dụng máy tính TCP Client với giao diện đồ họa
 * Kết nối đến server để thực hiện các phép tính cơ bản (+, -, *, /)
 * Sử dụng Swing GUI và Socket TCP để giao tiếp với server
 */
public class CalculatorClient extends JFrame {
    // Các thành phần giao diện người dùng
    private JTextField txtIP, txtPort, txtNum1, txtNum2, txtResult;  // Text fields cho IP, port, số và kết quả
    private JComboBox<String> cmbOperator;  // ComboBox cho phép toán (+, -, *, /)
    private JButton btnCalculate;           // Nút tính toán
    
    // Các thành phần kết nối mạng
    private Socket socket;                  // Socket kết nối đến server
    private PrintWriter out;                // Stream gửi dữ liệu đến server
    private BufferedReader in;              // Stream nhận dữ liệu từ server
    private boolean isConnected = false;    // Trạng thái kết nối với server
    
    /**
     * Constructor - Khởi tạo giao diện và thiết lập event handlers
     */
    public CalculatorClient() {
        initializeUI();
        setupEventHandlers();
    }
    
    /**
     * Khởi tạo giao diện người dùng (UI)
     * Tạo và sắp xếp các thành phần GUI như labels, textfields, buttons
     */
    private void initializeUI() {
        // Thiết lập cửa sổ chính
        setTitle("MÁY TÍNH TCP");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Căn giữa màn hình
        setResizable(false);          // Không cho phép resize
        
        // Tạo panel chính với background màu xám nhạt
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(220, 220, 220));
        mainPanel.setLayout(null);  // Sử dụng absolute positioning
        
        // Tiêu đề ứng dụng
        JLabel lblTitle = new JLabel("MÁY TÍNH TCP");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(Color.MAGENTA);
        lblTitle.setBounds(170, 20, 200, 30);
        mainPanel.add(lblTitle);
        
        // Máy chủ (IP)
        JLabel lblIP = new JLabel("Máy chủ (IP)");
        lblIP.setBounds(30, 70, 100, 25);
        mainPanel.add(lblIP);
        
        txtIP = new JTextField("localhost");
        txtIP.setBounds(130, 70, 120, 25);
        mainPanel.add(txtIP);
        
        // Cổng
        JLabel lblPort = new JLabel("Cổng");
        lblPort.setBounds(270, 70, 50, 25);
        mainPanel.add(lblPort);
        
        txtPort = new JTextField("3210");
        txtPort.setBounds(320, 70, 80, 25);
        mainPanel.add(txtPort);
        
        // Số thứ nhất
        JLabel lblNum1 = new JLabel("Số thứ nhất");
        lblNum1.setBounds(30, 120, 100, 25);
        mainPanel.add(lblNum1);
        
        txtNum1 = new JTextField("1001");
        txtNum1.setBounds(30, 145, 80, 30);
        txtNum1.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(txtNum1);
        
        // Phép toán
        String[] operators = {"+", "-", "*", "/"};
        cmbOperator = new JComboBox<>(operators);
        cmbOperator.setBounds(130, 145, 50, 30);
        cmbOperator.setSelectedIndex(1); // Mặc định là "-"
        mainPanel.add(cmbOperator);
        
        // Số thứ hai
        JLabel lblNum2 = new JLabel("Số thứ hai");
        lblNum2.setBounds(200, 120, 100, 25);
        mainPanel.add(lblNum2);
        
        txtNum2 = new JTextField("5");
        txtNum2.setBounds(200, 145, 80, 30);
        txtNum2.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(txtNum2);
        
        // Dấu bằng
        JLabel lblEqual = new JLabel("=");
        lblEqual.setBounds(300, 150, 20, 20);
        lblEqual.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(lblEqual);
        
        // Kết quả
        JLabel lblResult = new JLabel("Kết quả");
        lblResult.setBounds(330, 120, 100, 25);
        mainPanel.add(lblResult);
        
        txtResult = new JTextField("996.0");
        txtResult.setBounds(330, 145, 120, 30);
        txtResult.setFont(new Font("Arial", Font.PLAIN, 14));
        txtResult.setEditable(false);
        txtResult.setBackground(Color.WHITE);
        mainPanel.add(txtResult);
        
        // Nút tính toán
        btnCalculate = new JButton("Tính toán");
        btnCalculate.setBounds(200, 200, 100, 35);
        btnCalculate.setBackground(new Color(50, 200, 50));
        btnCalculate.setForeground(Color.WHITE);
        btnCalculate.setFont(new Font("Arial", Font.BOLD, 14));
        btnCalculate.setFocusPainted(false);
        mainPanel.add(btnCalculate);
        
        add(mainPanel);
    }
    
    /**
     * Thiết lập các event handlers cho giao diện
     * Xử lý sự kiện click button và phím Enter
     */
    private void setupEventHandlers() {
        // ActionListener cho button - xử lý khi click nút "Tính toán"
        // Tạo anonymous class thứ 1 -> CalculatorClient$1.class
        btnCalculate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performCalculation();
            }
        });
        
        // KeyListener cho txtNum1 - xử lý phím Enter trên ô số thứ nhất
        // Tạo anonymous class thứ 2 -> CalculatorClient$2.class
        txtNum1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performCalculation();
                }
            }
        });
        
        // KeyListener cho txtNum2 - xử lý phím Enter trên ô số thứ hai  
        // Tạo anonymous class thứ 3 -> CalculatorClient$3.class
        txtNum2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performCalculation();
                }
            }
        });
    }
    
    /**
     * Kết nối đến server TCP
     * Thiết lập Socket connection và I/O streams
     */
    private void connectToServer() {
        // Kiểm tra nếu đã kết nối rồi thì không cần kết nối lại
        if (isConnected) {
            return;
        }
        
        try {
            // Lấy thông tin IP và port từ giao diện
            String ip = txtIP.getText().trim();
            int port = Integer.parseInt(txtPort.getText().trim());
            
            // Tạo socket kết nối đến server
            socket = new Socket(ip, port);
            // Tạo output stream để gửi dữ liệu đến server
            out = new PrintWriter(socket.getOutputStream(), true);
            // Tạo input stream để nhận dữ liệu từ server
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            isConnected = true;
            System.out.println("Đã kết nối tới server: " + ip + ":" + port);
            
        } catch (Exception e) {
            // Hiển thị thông báo lỗi nếu không kết nối được
            JOptionPane.showMessageDialog(this, 
                "Không thể kết nối tới server: " + e.getMessage(),
                "Lỗi kết nối", 
                JOptionPane.ERROR_MESSAGE);
            isConnected = false;
        }
    }
    
    /**
     * Thực hiện phép tính bằng cách gửi yêu cầu tới server
     * Kết nối tới server nếu chưa kết nối, gửi phép tính và nhận kết quả
     */
    private void performCalculation() {
        try {
            // Kết nối tới server nếu chưa kết nối
            if (!isConnected) {
                connectToServer();
                if (!isConnected) {
                    return;
                }
            }
            
            // Lấy dữ liệu từ giao diện
            String num1 = txtNum1.getText().trim();
            String operator = (String) cmbOperator.getSelectedItem();
            String num2 = txtNum2.getText().trim();
            
            // Kiểm tra dữ liệu đầu vào
            if (num1.isEmpty() || num2.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Vui lòng nhập đầy đủ hai số!",
                    "Lỗi", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Gửi yêu cầu tới server
            String request = num1 + " " + operator + " " + num2;
            out.println(request);
            System.out.println("Gửi đến server: " + request);
            
            // Nhận kết quả từ server
            String result = in.readLine();
            System.out.println("Nhận từ server: " + result);
            
            // Xử lý kết quả
            if (result != null) {
                if (result.startsWith("ERROR:")) {
                    // Hiển thị thông báo lỗi từ server
                    JOptionPane.showMessageDialog(this, 
                        result,
                        "Lỗi tính toán", 
                        JOptionPane.ERROR_MESSAGE);
                    txtResult.setText("");
                } else {
                    // Hiển thị kết quả tính toán
                    txtResult.setText(result);
                }
            }
            
        } catch (Exception e) {
            // Xử lý lỗi kết nối hoặc giao tiếp
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi gửi yêu cầu: " + e.getMessage(),
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            
            // Đặt lại trạng thái kết nối khi có lỗi
            isConnected = false;
            try {
                if (socket != null) socket.close();
                if (out != null) out.close();
                if (in != null) in.close();
            } catch (IOException ex) {
                System.err.println("Lỗi đóng kết nối: " + ex.getMessage());
            }
        }
    }
    
    /**
     * Override dispose method để đóng kết nối khi đóng ứng dụng
     * Giải phóng tài nguyên network (socket, streams) trước khi thoát
     */
    @Override
    public void dispose() {
        try {
            // Đóng tất cả các kết nối network
            if (socket != null) socket.close();
            if (out != null) out.close();
            if (in != null) in.close();
        } catch (IOException e) {
            System.err.println("Lỗi đóng kết nối: " + e.getMessage());
        }
        super.dispose();
    }
    
    /**
     * Main method - điểm khởi động của ứng dụng
     * Sử dụng SwingUtilities.invokeLater để tạo GUI trên Event Dispatch Thread
     */
    public static void main(String[] args) {
        // Tạo anonymous class thứ 4 -> CalculatorClient$4.class
        // Sử dụng invokeLater để đảm bảo GUI được tạo trên EDT (Event Dispatch Thread)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Khởi tạo và hiển thị cửa sổ ứng dụng
                new CalculatorClient().setVisible(true);
            }
        });
    }
}
