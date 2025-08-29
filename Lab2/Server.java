import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(9999)) {
            System.out.println("Server started on port 9999");
            // Vòng lặp liên tục nhận kết nối từ client
            while (true) {
                // Chấp nhận kết nối từ client
                Socket clientSocket = serverSocket.accept();
                // Tạo một luồng mới để xử lý client (đa luồng)
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket socket;

    // Nhận socket từ client
    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
            // Tạo luồng đọc dữ liệu từ client
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // Tạo luồng ghi dữ liệu tới client
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String inputLine;
            // Vòng lặp nhận và xử lý dữ liệu từ client
            while ((inputLine = in.readLine()) != null) {
                // Nếu client gửi "quit" thì trả lời "Goodbye!" và kết thúc
                if (inputLine.equalsIgnoreCase("quit")) {
                    out.println("Bye!");
                    break;
                }
                // Đảo ngược chuỗi nhận được và gửi lại cho client
                String reversed = new StringBuilder(inputLine).reverse().toString();
                out.println(reversed);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Đóng socket khi kết thúc
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
