import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try (
            // Socket kết nối tới server tại localhost cổng 9999
            Socket socket = new Socket("localhost", 9999);
            // Luồng đọc dữ liệu từ bàn phím
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            // Luồng đọc dữ liệu từ server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // Luồng ghi dữ liệu tới server
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            System.out.println("Da ket noi voi server. Nhap ky tu:");
            String userInput;
            while (true) {
                System.out.print("In: ");
                // Đọc dữ liệu từ bàn phím
                userInput = keyboard.readLine();
                // Gửi dữ liệu tới server
                out.println(userInput);
                // Nhận phản hồi từ server
                String response = in.readLine();
                System.out.println("Server: " + response);
                // Nếu nhập "quit" thì thoát chương trình
                if (userInput.equalsIgnoreCase("quit")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
