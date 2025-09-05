import java.io.*;        // Thu vien I/O cho BufferedReader, PrintWriter, IOException
import java.net.*;       // Thu vien network cho ServerSocket, Socket
import java.util.StringTokenizer; // Thu vien phan tich chuoi input

/**
 * CalculatorServer - May tinh TCP Server da luong
 * Nhan ket noi tu nhieu client dong thoi va xu ly phep tinh co ban
 * Su dung multi-threading de xu ly moi client rieng biet
 */
public class CalculatorServer {
    private static final int PORT = 8080;  // Cong ket noi mac dinh
    
    /**
     * Ham main - khoi tao server va lang nghe ket noi
     */
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server da khoi dong tren cong " + PORT);
            System.out.println("Dang cho ket noi tu client...");
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client da ket noi tu: " + clientSocket.getInetAddress());
                
                // Tao thread moi de xu ly client rieng biet
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            System.err.println("Loi khoi dong server: " + e.getMessage());
        }
    }
}

/**
 * ClientHandler - Xu ly moi client trong thread rieng biet
 * Implements Runnable de chay tren thread rieng
 * Nhan request tu client, xu ly phep tinh va tra ve ket qua
 */
class ClientHandler implements Runnable {
    private Socket clientSocket;        // Socket ket noi voi client
    private BufferedReader in;          // Stream doc du lieu tu client
    private PrintWriter out;            // Stream gui du lieu den client
    
    /**
     * Constructor - Khoi tao ClientHandler voi socket cua client
     * @param socket Socket connection tu client
     */
    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }
    
    /**
     * Ham run - xu ly client trong thread rieng biet
     * Nhan request, xu ly phep tinh va gui ket qua ve client
     */
    @Override
    public void run() {
        try {
            // Khoi tao input va output streams
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            
            String inputLine;
            // Doc du lieu tu client cho den khi client ngat ket noi
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Nhan tu client: " + inputLine);
                
                // Xu ly phep tinh
                String result = processCalculation(inputLine);
                out.println(result);
                
                System.out.println("Gui ket qua: " + result);
            }
        } catch (IOException e) {
            System.err.println("Loi xu ly client: " + e.getMessage());
        } finally {
            // Dong ket noi va giai phong tai nguyen
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (clientSocket != null) clientSocket.close();
                System.out.println("Da dong ket noi voi client");
            } catch (IOException e) {
                System.err.println("Loi dong ket noi: " + e.getMessage());
            }
        }
    }
    
    /**
     * Xu ly phep tinh tu chuoi input cua client
     * Phan tich chuoi "so1 pheptoan so2" va tra ve ket qua
     * @param input Chuoi phep tinh tu client
     * @return Ket qua phep tinh hoac thong bao loi
     */
    private String processCalculation(String input) {
        try {
            // Phan tich chuoi input: "so1 phep_toan so2"
            StringTokenizer tokenizer = new StringTokenizer(input, " ");
            
            if (tokenizer.countTokens() != 3) {
                return "LOI: Dinh dang khong dung. Su dung: so1 phep_toan so2";
            }
            
            double num1 = Double.parseDouble(tokenizer.nextToken());
            String operator = tokenizer.nextToken();
            double num2 = Double.parseDouble(tokenizer.nextToken());
            
            double result;
            
            // Xu ly tung phep toan
            switch (operator) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "/":
                    if (num2 == 0) {
                        return "LOI: Khong the chia cho 0";
                    }
                    result = num1 / num2;
                    break;
                default:
                    return "LOI: Phep toan khong ho tro. Su dung: +, -, *, /";
            }
            
            // Tra ve ket qua voi dinh dang phu hop
            if (result == (long) result) {
                return String.valueOf((long) result);
            } else {
                return String.valueOf(result);
            }
            
        } catch (NumberFormatException e) {
            return "LOI: So khong hop le";
        } catch (Exception e) {
            return "LOI: Loi xu ly: " + e.getMessage();
        }
    }
}
