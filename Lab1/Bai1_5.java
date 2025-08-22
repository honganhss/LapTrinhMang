import java.io.*;

public class Bai1_5 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new FileWriter("file_bai1_5.txt"));
        System.out.println("Nhap noi dung file (ket thuc bang chu 'stop'): ");
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.equalsIgnoreCase("stop")) break;
            writer.write(line);
            writer.newLine();
        }
        writer.close();
        System.out.println("Da ghi file xong!");
    }
}
